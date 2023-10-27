# OJ2.0后端重构指南

OJ2.0项目基于springboot框架采用前后端分离设计，本文档将主要介绍OJ2.0后端的项目结构、设计思想、重构流程、测试方法，并给出了一些进一步完善的建议。

## 推荐开发工具

IntelliJ IDEA

Navicat

Copilot（可选）

## 准备工作

1. 获取代码：git clone https://github.com/JennyTurtles/OJ.git
2. 获取代码编辑权限：将GitHub账号发送给我，我来添加。
3. 获取文档编辑权限：https://app.apifox.com/invite/project?token=uf4zyZdfEKS7cyWtAj9Nw
4. 在resources文件夹下添加application.properties文件，该文件包含远程数据库配置信息，请勿上传到git上。

## 项目结构

项目按照模块进行开发，模块根据作用域大小分为：功能模块（exam模块、user模块等）和全局模块。

功能模块与功能模块之间尽可能少地相互引用，全局模块中存放工具类和常用实体类，可被功能模块引用。

![image-20231026114332020](/Users/gongrunze/Library/Application Support/typora-user-images/image-20231026114332020.png)

### 功能模块

功能模块采用SSM结构，每个模块内均包含controller，dao，model，service这四个文件夹。

![image-20231026120140058](/Users/gongrunze/Library/Application Support/typora-user-images/image-20231026120140058.png)

### 全局模块

全局模块包含aop（切面，能实现不修改原代码的情况下进行功能增强），config（全局配置，主要用于安全控制，用户认证），exception（全局异常处理），model（通用实体类，如响应体RespBean），util（工具类，如token解析工具）。

![image-20231026120313276](/Users/gongrunze/Library/Application Support/typora-user-images/image-20231026120313276.png)

### 配置文件

- application.properties：主要为数据库配置，目前项目混用mybatis和hibernate两个DAO框架，因此需要在配置文件中对他们进行分别配置（url，username，password，name）。
- spring-hibernate：hibernate的专用配置，不建议修改。

## 远程数据库

- url：***
- username：oj
- password：***
- name：gdoj
- 注：该数据库可在外网使用，由于使用了内网穿透，远程数据库在进行增删改查时速度慢是正常现象。

## 版本控制

使用git进行版本控制，仓库链接：https://github.com/JennyTurtles/OJ

## 接口文档

采用apifox进行文档管理。文档链接：https://app.apifox.com/project/3419447

### 文档撰写

#### 设计思想

一个功能（按钮）一个文件夹。

如：点击**忘记密码**按钮，弹出新网页，该网页中所有接口均属于**忘记密码**文件夹。

#### 捕获请求和响应（chrome为例）

打开目标网页->右键点击检查->点击网络->选择Fetch/XHR选项

点击目标按钮（或其他可以触发网络请求的操作）->找到以**.action**结尾的请求->获取请求的URL、请求参数和响应体。

如：userAction!findUserByStudentNoSchoolId.action转换为：user/findUserByStudentNoSchoolId

#### 撰写请求

请求参数需要以json形式放置于body内。

#### 撰写响应

复制获取到的响应体->点击**通过JSON等生成**->粘贴进去即可自动生成

对生成好的响应进行微调，将msg改为message，将obj改为data。将success删除，添加code。对成功示例也进行同样的修改。

响应需要满足**响应体规范约定**。

### 测试

测试之前需要下载apifox的插件，或者使用客户端版。

为了通过token验证，首先请运行：User模块/登陆/学生登陆（或老师登陆）/成功，复制响应中的tokn。

将其粘贴到待测试接口的Headers中的Authorization内。

测试通过后点击右上角保存为用例，然后修改文档状态为：测试中，等待前端进行测试。

## 用户认证与权限管理

OJ1.0中采用session进行用户认证，OJ2.0改为了JWT，并全面接入了*Spring Security*框架，不仅有利于后端的用户权限管理，并且由于无需在服务器保存用户信息，从而去除了Reddis。

*Spring Security*框架的封装程度较高，为了方便后人进行维护和添加新功能，在此介绍一下OJ2.0中用户认证与权限管理的流程。

### 用户信息

用户信息包含：用户ID，角色，学号（仅对于学生）等，这些信息在OJ1.0中均存放于session中，获取的过程如下：

```JAVA
SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
String role = sessionInfo.getRoleNames();
int id = sessionInfo.getUserId();
```

在OJ2.0中用户信息放置在token中，获取的过程如下：

```JAVA
// request来自controller函数的形参HttpServletRequest request
DecodeToken decodeToken = new DecodeToken(request); 
String role = decodeToken.getRole();
String userId = decodeToken.getUserId();
```

实例代码请见：AccountController

### 用户认证

用户登陆成功则向其发放token，后续的用户请求均需要携带token，以下将介绍用户认证并生成token的过程。

1. 用户向/login/student或/login/admin发送请求
2. 请求进入拦截器进行token校验，为了跳过这步，需要在**InterceptorConfig**中将登陆url设置为排除项，否则请求就会进入**JwtInterceptor**，就无法完成登陆了。
3. 请求经过**LoginController**，进入**LoginServiceImpl**。
4. （重点）**Spring Security**进行用户认证。
   1. 在**LoginServiceImpl**的**login**方法中，用户输入的用户名和密码会被封装为**Authentication对象**，然后输入**Spring Security**中的**AuthenticationManager**。
   2. **AuthenticationManager**寻找实现了**UserDetailsService接口**的bean，并调用其中的**loadUserByUsername**方法。
   3. **loadUserByUsername**方法获取**Authentication对象**中的用户名，根据用户名去数据库查询用户信息，用户信息需要被封装为实现了**UserDetails接口**的对象。
   4. **UserDetails**对象实现了**getPassword**方法，**AuthenticationManager**会调用**getPassword**方法获取前面从数据库中查询的用户真实密码，然后与**Authentication对象**中的密码进行比较。
   5. 如果校验成功，**AuthenticationManager**会返回一个包含用户信息的**Authentication**对象。

5. 将一部分必要的用户信息放入token，并生成token，返回前端。

### 权限管理

OJ2.0中存在四种角色，分别为student，admin，assistant，teacher，每个角色的功能不同，调用函数的权限也不同。

在前端针对不同用户暴露不同接口是进行权限管理的一种方式，但仅仅只是防君子不防小人，为了防止角色为student的用户拿着自己的token跳过前端直接访问admin接口，OJ2.0基于*Spring Security*框架实现了函数级的权限管理。

实现流程如下：

1. 在**JwtInterceptor**中，对用户的token进行解析，获取用户的角色。
2. 将用户角色放入**SecurityContext**中。
3. 在controller上添加注解**@PreAuthorize("hasAnyAuthority('admin')")**，即可将该接口的权限设置为仅管理员允许访问。

## 后端重构流程（以学生注册功能为例）

1. 撰写文档。

2. 确定功能模块：学生注册属于user模块，代码应该放置于user文件夹下。

3. 定位OJ1.0中的代码：请求的URL为userAction!signUser.action，代码位于userAction文件的signUser函数。

4. 复制代码：将userAction文件改名为userController，并全部复制到user/controller下。

5. 修改代码

   1. 删除所有红色的import。

   2. 删除类名上所有注解。添加如下注解：

      ```JAVA
      @RestController
      @RequestMapping("/user")
      ```

   3. 删除类名后的extends和implements。

   4. 删除final成员变量（一般是serialVersionUID和logger）。

   5. 删除所有带Redis的函数和成员变量。

   6. （可选）删除所有get和set函数，直接在成员变量上加@Resource进行注入。

   7. （可选）注释所有与signUser无关的函数和成员变量，减少工作量。

   8. 导包。如果缺少某些类，就将代码从OJ1.0复制过来，放入user下面对应的位置。这一步工作量比较大，可能会不断递归地复制代码，需要一些耐心。为了让idea正确的工作，请首先将红色的import删除。如果实在太多的话就注释掉一些不相关的，前面的区域以后再来探索吧～😄

   9. 修改signUser函数代码。

      1. 函数上添加注解。

         ```JAVA
         @PostMapping("/signUser") // url跟函数名保持一致，前面要加斜杠。注意请求的类型是post还是get或其他。
         @PreAuthorize("hasAnyAuthority('student','admin','teacher','assistant')") // 用于权限管理，表示允许访问函数的角色，可以先不写。
         ```

      2. OJ1.0中采用super.writeJson(j)将json传递给前端。OJ2.0中则通过返回RespBean。因此要将返回类型改为RespBean。

      3. 修改形参：请求中会发送用户填写的注册信息，OJ1.0中直接将其存储到了user成员变量中，user的类型是PMUser，在OJ2.0中为了接受请求中的信息，需要在形参中使用PMUser对象接受。此外如果需要获取用户信息，形参还要加上HttpServletRequest request(具体见：用户认证与权限管理/用户信息)。代码如下：

         ```JAVA
         // 前端传递的数据格式是json，因此需要使用@RequestBody注解进行接受
         public RespBean signUser(@RequestBody PMUser user)
         ```

      4. 修改响应体相关代码，使其满足apiFox中约定的响应体规范。

         ```JAVA
         // 删除形如以下两行的代码
         logger.info("修改学生注册信息成功");
         super.writeJson(j);
         
         // 需要将响应体封装为RespBean，以下提供3个例子。
         // 例子1:
         j.setSuccess(true);
         j.setMsg("修改学生注册信息成功");
         // 修改为：
         return RespBean.ok("修改学生注册信息成功");
         
         // 例子2:
         j.setSuccess(false);
         j.setMsg("修改学生注册信息失败");
         // 修改为：
         return RespBean.error("修改学生注册信息失败");
         
         // 例子3:
         j.setSuccess(false);
         j.setObj(u);
         j.setMsg("1");
         // 修改为
         return RespBean.error("1",u);
         ```

      5. 在apiFox中运行测试，有些测试可能需要数据库存在特定数据，直接在远程数据库添加即可。详情请看接口文档/测试。
   
   10. 测试通过后，将接口的状态从“开发中”修改为“测试中”。

## 安全性BUG

### 重置密码漏洞

直接访问/user/updatePasswordByUserName接口，请求中包含username和password，即可直接重置密码。

解决方案：验证问题答案通过后给该用户颁发token，访问/user/updatePasswordByUserName时需要携带token。

### 修改密码漏洞

1. 无论输入的当前密码是否正确都能修改密码。

2. 确认密码和新密码不一致时无提示。

解决方案：

1. 对输入的当前密码进行校验。
2. 前端进行修改。

## 更上一层楼

以下内容仅仅作为建议，完成后能够进一步提高项目的可维护性和美观度。

### 多表登陆方案

OJ2.0的用户信息用了两个表（adminusers，users）进行存放，**Spring Security**对多表登陆的支持并不好，因为**loadUserByUsername**方法无法获取当前登陆的角色信息，因此就不知道应该在哪张表中进行查询。

一个解决方法是对两张表都进行查询，但是这样做会产生一次无用的数据库查询，并且当adminusers，users表中碰巧存在相同的用户名和密码的用户时，会出现问题，我们不能抱有侥幸心理。

另一个解决方法是在**loadUserByUsername**方法中获取当前请求的URl，根据请求的URL来判断应该访问哪张表。这是目前的采用的方法，但是在service中处理HTTP请求并不是最佳实践。

### DAO改进

由于历史遗留问题，目前项目混用了mybatis和hibernate两个DAO框架，其中hibernate中的事务管理器是通过spring配置文件进行创建的，而不是使用springboot的自动事务管理器，这导致了必须在配置文件中使用:

```JAVA
spring.main.allow-bean-definition-overriding=true
```

将自定义事务管理器覆盖springboot的自动事务管理器并不是一个常用的做法，这会对mybatis的事务产生什么影响也是未知的。

### Model类改进

使用lombok的@Data注解取代get、set等操作。

### 响应体对象去除无用字段

对于**ResBean**中的**data**对象，在返回给前端时通常会包含很多无用的字段，有两个解决方法：

1. 对返回的对象加上**@JsonInclude**注解，去除值为NULL的属性。
2. 返回一个Map，Map仅包含所需的信息。

### 数据库字段名统一

adminusers表和users表字段名称设置比较不合理，如：

1. adminusers表中用户名为account，users表中用户名为username。
2. users表中班级叫做banjo而不是class。

修改数据库字段名会涉及到大量DAO代码的修改，工作量比较大，以后有时间可以慢慢改，也可以不改。

## 其他

### 手动向数据库添加加密后的密码

运行test中的**testBCryptPasswordEncoder**方法，首次运行之前需要往test/resources中添加application.properties。

### 配置免token认证的URL

在**InterceptorConfig**中**excludePathPatterns**添加路径即可。

### 设置token的载荷内容

要在token中放入更多信息，请在**genToken()**中设置。

## 联系方式

sygongrunze@126.com

