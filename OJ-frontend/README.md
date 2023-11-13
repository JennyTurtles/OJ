# OJ2.0前端重构指南

OJ2.0项目前端基于Vue3



## 开始


Install dependencies(Recommended use pnpm: https://pnpm.io/zh/installation)
npm i -g pnpm # Installed and can be ignored
pnpm i # or npm i


pnpm dev
```

### Build and Release

```shell
# Test Environment
pnpm build:test

# Github Environment
pnpm build:github

# Prod Environment
pnpm build
```



```shell
# eslint check
pnpm lint

# eslint check and fix
pnpm lint:fix

# Preview（Need to build first）
pnpm preview

# Commit（husky+commitlint）
pnpm cz
```



## 技术栈
Vue3
https://vuejs.org/
Pinia 
https://pinia.web3doc.top/
Naive UI
https://www.naiveui.com/zh-CN/os-theme
Unocss
https://alfred-skyblue.github.io/unocss-docs-cn/





## 目录描述

```
Vue Naive Admin
|-- .github                             // github相关，如推送github仓库后自动部署gh pages
|-- .husky                              // git commit钩子
|-- .vscode                             // vscode编辑器相关
|   |-- extensions.json                 // 插件推荐
|   |-- settings.json                   // 项目级别的vscode配置，优先级大于全局vscode配置
|-- build                               // 构建相关配置
|   |-- constant.js                     // 构建相关的常量
|   |-- utils.js                        // 构建相关的工具方法
|   |-- config                          
|   |   |-- define.js                   // 注入全局常量，启动或打包后将添加到window中
|   |   |-- proxy.js                    // 代理配置
|   |-- plugin                          
|   |   |-- html.js                     // vite-plugin-html插件，用于注入变量或者html标签
|   |   |-- mock.js                     // vite-plugin-mock插件，处理mock
|   |   |-- unplugin.js                 // unplugin相关插件，包含DefineOptions和自动导入
|   |-- script                          // 打包完成后执行的一些node脚本（不重要）
|       |-- build-cname.js              // 自动生成cname
|-- mock                                // mock
|   |-- utils.js                        // mock请求需要用到的工具方法
|   |-- api                             // mock接口
|-- public                              // 公共资源，文件夹下的文件会在打包后会直接加到dist根目录下
|-- settings                            // 项目配置
|   |-- proxy-config.js                 // 代理配置文件
|   |-- theme.json                      // 主题配置项，主题色等
|-- src
|   |-- api                             // 公共api
|   |-- assets                          // 静态资源
|   |   |-- images                      // 图片
|   |   |-- svg                         // svg图标
|   |-- components                      // 全局组件
|   |   |-- common                      // 公共组件
|   |   |-- icon                        // icon相关组件
|   |   |-- page                        // 页面组件
|   |   |-- query-bar                   // 查询选项
|   |   |-- table                       // 封装的表格组件
|   |-- composables                     // 封装的组合式函数
|   |-- layout                          // 布局相关组件
|   |   |-- components
|   |       |-- AppMain.vue             // 主体内容
|   |       |-- header                  // 头部
|   |       |-- sidebar                 // 侧边菜单栏
|   |       |-- tags                    // 多页签栏
|   |-- router                          // 路由
|   |   |-- guard                       // 路由守卫
|   |   |-- routes                      // 路由列表
|   |-- store                           // 状态管理（pinia）
|   |   |-- modules                     // 模块
|   |       |-- app                     // 管理页面重新加载、折叠菜单栏和keepAlive等
|   |       |-- permission              // 权限相关，管理权限菜单
|   |       |-- tags                    // 管理多页签
|   |       |-- user                    // 用户模块，管理用户信息、登录登出
|   |-- styles                          // 样式
|   |-- utils                           // 封装的工具方法
|   |   |-- auth                        // 权限相关，如token、跳转登录页等
|   |   |-- common                      // 通用
|   |   |-- http                        // 封装axios
|   |   |-- storage                     // 封装localStorage和sessionStorage
|   |-- views                           // 页面
|   |   |-- user                        // 用户端
|   |   |-- admin                       // 管理端
|   |   |-- error-page                  // 错误页面
|   |-- App.vue
|   |-- main.js
|-- .cz-config.js                       // git提交配置
|-- .editorconfig                       // 编辑器配置
|-- .env                                // 环境文件，所有环境都会载入
|-- .env.development                    // 开发环境文件
|-- .env.production                     // 生产环境文件
|-- .env.test                           // 测试环境文件
|-- .eslintignore                       // eslint忽略
|-- .eslintrc.js                        // eslint配置
|-- .gitignore                          // git忽略
|-- .prettierignore                     // prettier格式化忽略
|-- commitlint.config.js                // commitlint规范配置
|-- index.html                          
|-- jsconfig.json                       // js配置
|-- LICENSE                             // 协议
|-- package.json                        // 依赖描述文件
|-- pnpm-lock.yaml                      // 依赖锁定文件
|-- prettier.config.js                  // prettier格式化配置
|-- README.md                           // 项目描述文档（英文）
|-- README.zh-CN.md                     // 项目描述文档（中文）
|-- unocss.config.js                    // unocss配置
|-- vite.config.js                      // vite配置
```


## 路由设计

user/take-class
user/software-list
user/faq
user/person-setting
user/re-pw-info
user/reset-password
user/signup

user/exam-list 【考试列表】

user/contest/[examId] 【比赛：比赛题目列表页】
user/contest/[examId]/problems/[problemId] 【比赛题目】
user/contest/[examId]/score-rank-status【比赛成绩页面】
user/contest/[examId]/score-only-rank
user/contest/[examId]/score-top10

user/training/[examId]【训练流程页】
user/training/[examId]/problems/[problemId] 【训练题目】
user/training/[examId]/score-train-rank 【训练成绩页面】



### 权限控制和认证

#### 认证
admin端和user端分开存token

#### 权限控制
定义路由时定义角色
角色： student, admin ...


## 提示、通知和弹框

全局声明了弹框相关的API

提示（toast）
```js
$message.success('登录成功')
$message.error('登录失败')
$message.loading('正在尝试重新登录...')
$message.removeMessage() //移除toast
```
通知
```js
$notification.info({
	content: '信息',
	duration: 2500,
})

```

对话框
```js

$dialog.confirm
$dialog.success
$dialog.error
```


## 请求规范


## CSS规范




## 关于废弃页面

submitList.jsp（没有内容）


题解（tab）：目前没有用
addProCommentByStudent （用于takeAnExam.jsp, viewTestCases.jsp , takeItranExam.jsp）、
viewStuProblemCommentDetail (用于takeAnExam.jsp,  viewTestCases.jsp, takeItranExam.jsp)、
viewTestCases (没有用到jsp，而是弹框).

