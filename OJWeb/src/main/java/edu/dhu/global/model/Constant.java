package edu.dhu.global.model;

// Constant类用来保存一些通用的常量
public class Constant {
	// 用户提交代码之后，状态为等待状态，solution的status的初始值
	public static final String CODE_WAIT = "WAIT";

	public static final String REMARK_WAIT = "WAIT:正在排队等待裁判机裁判";

	// solution被裁判机取出，存放于集合中，等待裁定
	public static final String CODE_QUEUE = "QUEUE";
	public static final String REMARK_QUEUE = "QUEUE:裁判机正在裁判";

	// solution编译通过，测试用例的运行检验也正确
	public static final String CODE_AC = "AC";
	public static final String REMARK_AC = "AC：运行结果正确";

	// 代码输出与标准测试用例的输出不一致，但在去除了空格、换行符、制表符后，两者依然不一致
	public static final String CODE_WA = "WA";
	public static final String REMARK_WA = "WA：测试数据运行错误。注意：有两种情况可能导致自己运行程序时正确但OJ系统给出错误结果：1、如果在程序中使用了fflush函数清空输入，可能导致WA。2、如果是输入字符串，OJ系统的测试数据最后一行后面不一定有换行符，但我们在键盘上输入时每一行后面都必然有换行符。所以，要当心这个区别。";

	// 测试用例的运行时间超过规定值
	public static final String CODE_TLE = "TLE";
	public static final String REMARK_TLE = "TLE：程序运行超时。可能的原因：1、程序中存在死循环 2、算法需要改进";

	// 测试用例的运行内存超过规定值（本项目中没有使用到）
	public static final String CODE_MLE = "MLE";

	// 代码输出与标准测试用例的输出不一致，但在去除了空格、换行符、制表符后，两者一致
	public static final String CODE_PE = "PE";
	public static final String REMARK_PE = "PE：测试数据结果正确，但空格、换行、tab等字符不正确。";

	// 提交的代码运行后出口值不为零
	public static final String CODE_RE = "RE";
	public static final String REMARK_RE = "RE：程序运行时错误。可能的原因：1、main函数return的不是0或者忘记写return 0语句； 2、除0或指针错误等原因造成程序崩溃。";

	// 代码编译错误
	public static final String CODE_CE = "CE";
	public static final String REMARK_CE = "CE：编译错误";

	// 提交的代码在使用测试用例的输入后运行，产生的输出大于规定的长度
	public static final String CODE_OLE = "OLE";
	public static final String REMARK_OLE = "OLE：程序运行输出内容超过了允许的最大长度";

	// 学生提交代码之后，solution中的分数默认为0分
	public static final float DEFAULT_SCORE = 0;

	// 学生提交代码之后，提示的测试用例hintCase默认为"-1"
	public static final String DEFAULT_HINTCASE = "null hintCases";

	// 学生提交代码之后，正确的测试用例默认为"-1"
	public static final String DEFAULT_CORRECTCASEIDS = "-1";

	// 学生提交代码之后，相似度默认设置成－1
	public static final float DEFAULT_SIMILARITY = -1;

	// 学生提交代码之后，remark默认设置成""
	public static final String DEFAULT_REMARK = "";

	// 学生提交本题，如果是第一次提交
	public static final String SUBMIT_FIRST = "FIRST";

	// 学生提交本题，如果涉嫌抄袭之后，坚持提交本题
	public static final String SUBMIT_SECOND = "SECOND";

	// 教师角色字符串
	public static final String ROLE_TEACHER = "teacher";

	// 管理员角色字符串
	public static final String ROLE_ADMIN = "admin";
	
	//提交本题失败
	public static final String SUBMIT_FAILURE = "failure";
	
	//通关之后提交本题
	public static final String SUBMIT_FINISHED = "afterFinished";
	
	//通关之前提交本题
	public static final String SUBMIT_UNFINISHED = "beforeFinished";

}