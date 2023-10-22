//package edu.dhu.util;
//
//import edu.dhu.model.*;
//import edu.dhu.pageModel.*;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.StringReader;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class OJWSUtil {
//
//	public String ChangeDateToString(String date) {
//		if (date != null) {
//			date = date.substring(0, 19);
//			try {
//				Date dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
//						Locale.CHINA).parse(date);
//				SimpleDateFormat sdf = new SimpleDateFormat(
//						"yyyy-MM-dd HH:mm:ss");
//				date = sdf.format(dates);
//			} catch (ParseException e) {
//
//				e.printStackTrace();
//			}
//		}
//
//		return date;
//
//	}
//
//	public String SimpleDateFormat(Date date) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间显示格式
//		String nowdateStr = sdf.format(date);// 将当前时间格式化为需要的类型
//		return nowdateStr;
//
//	}
//
//	Date nowdate = new Date();// 创建一个时间对象，获取到当前的时间
//	String timeStr = SimpleDateFormat(nowdate);
//
//	/**
//	 * @说明 使用DOM组装和解析XML
//	 * @author 黄鑫
//	 * @version 1.0
//	 * @since
//	 */
//	/**
//	 * 生成XML格式的字符串
//	 */
//	public String createXmlToString(PMUser user) {
//		String xmlStr = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//			Element root = document.createElement("root");
//			document.appendChild(root);
//			OJWSUtil oj = new OJWSUtil();
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent(user.getRspMsg());
//			root.appendChild(rspMsg);
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//
//			if (user.getRspMsg().equals("Success")) {
//				Element banji = document.createElement("banji");
//				Element chineseName = document.createElement("chineseName");
//				Element createDate = document.createElement("createDate");
//				Element email = document.createElement("email");
//				Element ip = document.createElement("ip");
//				Element id = document.createElement("id");
//				Element password = document.createElement("password");
//				Element studentNo = document.createElement("studentNo");
//				Element username = document.createElement("username");
//				Element nickName = document.createElement("nickName");
//
//				id.setTextContent(user.getId().toString());
//				banji.setTextContent(user.getBanji());
//				chineseName.setTextContent(user.getChineseName());
//				String ctime = oj.ChangeDateToString(user.getCreateDate()
//						.toString());
//				createDate.setTextContent(ctime);
//				email.setTextContent(user.getEmail());
//				ip.setTextContent(user.getIp());
//				password.setTextContent(user.getPassword());
//				studentNo.setTextContent(user.getStudentNo());
//				username.setTextContent(user.getUsername());
//				nickName.setTextContent(user.getNickName());
//				root.appendChild(id);
//				root.appendChild(banji);
//				root.appendChild(chineseName);
//				root.appendChild(email);
//				root.appendChild(ip);
//				root.appendChild(password);
//				root.appendChild(studentNo);
//				root.appendChild(username);
//				root.appendChild(nickName);
//				root.appendChild(createDate);
//			}
//
//			xmlStr = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlStr;
//	}
//
//	private String DocumentToString(Document doc) throws Exception {
//		String xmlStr = null;
//		TransformerFactory tf = TransformerFactory.newInstance();
//		Transformer t = tf.newTransformer();
//		t.setOutputProperty("encoding", "GBK");// 解决中文问题，试过用GBK不行
//		t.setOutputProperty(OutputKeys.INDENT, "yes");
//		t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		t.transform(new DOMSource(doc), new StreamResult(bos));
//		xmlStr = bos.toString();
//		return xmlStr;
//	}
//
//	/**
//	 * @说明 使用DOM组装和解析XML
//	 * @author 黄鑫
//	 * @version 1.0
//	 * @since
//	 */
//	/**
//	 * 生成XML格式的字符串 将ExamList即考试列表转换成xml格式
//	 */
//	public String ExamListToXml(List<PMExam> pmelist) {
//		String xmlStr = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//
//			Element root = document.createElement("root");
//			document.appendChild(root);
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//			if (pmelist != null) {
//				for (int i = 0; i < pmelist.size(); i++) {
//					Element exam = document.createElement("exam");
//					Element id = document.createElement("id");
//					Element name = document.createElement("name");
//					Element starttime = document.createElement("starttime");
//
//					Element endtime = document.createElement("endtime");
//					Element description = document.createElement("description");
//					Element problemNum = document.createElement("problemNum");
//					Element studentViewScore = document.createElement("studentViewScore");
//
//					Element canGetHint = document.createElement("canGetHint");
//					Element partialScore = document
//							.createElement("partialScore");
//					Element submitOnlyAC = document
//							.createElement("submitOnlyAC");
//
//					Element language = document.createElement("language");
//					Element teacherId = document.createElement("teacherId");
//					Element status = document.createElement("status");
//					Element updateTime = document.createElement("updateTime");
//					Element allowChangeSeat = document
//							.createElement("allowChangeSeat");
//					Element type = document.createElement("type");
//
//					id.setTextContent(pmelist.get(i).getId().toString());
//					name.setTextContent(pmelist.get(i).getName());
//					String stime = ChangeDateToString(pmelist.get(i)
//							.getStarttime().toString());
//					starttime.setTextContent(stime);
//					String etime = ChangeDateToString(pmelist.get(i)
//							.getEndtime().toString());
//					endtime.setTextContent(etime);
//					description.setTextContent(pmelist.get(i).getDescription());
//					problemNum.setTextContent(pmelist.get(i).getProblemNum()
//							.toString());
//					studentViewScore.setTextContent(pmelist.get(i).getStudentViewScore());
//					canGetHint.setTextContent(String.valueOf(pmelist.get(i)
//							.isCanGetHint()));
//					partialScore.setTextContent(String.valueOf(pmelist.get(i)
//							.isPartialScore()));
//					submitOnlyAC.setTextContent(String.valueOf(pmelist.get(i)
//							.isSubmitOnlyAC()));
//					language.setTextContent(pmelist.get(i).getLanguage());
//					teacherId.setTextContent(pmelist.get(i).getTeacherId()
//							.toString());
//					type.setTextContent(pmelist.get(i).getType());
//					String utime;
//					if (pmelist.get(i).getUpdateTime() != null) {
//						utime = ChangeDateToString(pmelist.get(i)
//								.getUpdateTime().toString());
//					} else {
//						utime = null;
//					}
//					updateTime.setTextContent(utime);
//					allowChangeSeat.setTextContent(String.valueOf(pmelist
//							.get(i).getAllowChangeSeat()));
//
//					status.setTextContent("未开始");
//
//					exam.appendChild(id);
//					exam.appendChild(name);
//					exam.appendChild(starttime);
//					exam.appendChild(endtime);
//					exam.appendChild(description);
//					exam.appendChild(problemNum);
//					exam.appendChild(studentViewScore);
//					exam.appendChild(canGetHint);
//					exam.appendChild(partialScore);
//					exam.appendChild(submitOnlyAC);
//					exam.appendChild(language);
//					exam.appendChild(teacherId);
//					exam.appendChild(status);
//					exam.appendChild(updateTime);
//					exam.appendChild(allowChangeSeat);
//					exam.appendChild(type);
//
//					root.appendChild(exam);
//				}
//			}
//			xmlStr = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlStr;
//	}
//
//	public String ExamByIdToXml(Exam examInfo) {
//		String xmlStr = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//
//			Element root = document.createElement("root");
//			document.appendChild(root);
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//			if (examInfo != null) {
//				Element exam = document.createElement("exam");
//				Element id = document.createElement("id");
//				Element name = document.createElement("name");
//				Element starttime = document.createElement("starttime");
//
//				Element endtime = document.createElement("endtime");
//				Element description = document.createElement("description");
//				Element problemNum = document.createElement("problemNum");
//				Element studentViewScore = document.createElement("studentViewScore");
//
//				Element canGetHint = document.createElement("canGetHint");
//				Element partialScore = document.createElement("partialScore");
//				Element submitOnlyAC = document.createElement("submitOnlyAC");
//
//				Element language = document.createElement("language");
//				Element teacherId = document.createElement("teacherId");
//				Element status = document.createElement("status");
//				Element updateTime = document.createElement("updateTime");
//
//				id.setTextContent(examInfo.getId().toString());
//				name.setTextContent(examInfo.getName());
//				String stime = ChangeDateToString(examInfo.getStarttime()
//						.toString());
//				starttime.setTextContent(stime);
//				String etime = ChangeDateToString(examInfo.getEndtime()
//						.toString());
//				endtime.setTextContent(etime);
//				description.setTextContent(examInfo.getDescription());
//				problemNum.setTextContent(examInfo.getProblemNum().toString());
//				studentViewScore.setTextContent(examInfo.getStudentViewScore());
//				canGetHint.setTextContent(String.valueOf(examInfo
//						.getCanGetHint()));
//				partialScore.setTextContent(String.valueOf(examInfo
//						.getPartialScore()));
//				submitOnlyAC.setTextContent(String.valueOf(examInfo
//						.isSubmitOnlyAC()));
//				language.setTextContent(examInfo.getLanguage());
//				teacherId.setTextContent(examInfo.getTeacherId().toString());
//
//				String utime;
//				if (examInfo.getUpdateTime() != null) {
//					utime = ChangeDateToString(examInfo.getUpdateTime()
//							.toString());
//				} else {
//					utime = null;
//				}
//				updateTime.setTextContent(utime);
//
//				status.setTextContent("未开始");
//
//				exam.appendChild(id);
//				exam.appendChild(name);
//				exam.appendChild(starttime);
//				exam.appendChild(endtime);
//				exam.appendChild(description);
//				exam.appendChild(problemNum);
//				exam.appendChild(studentViewScore);
//				exam.appendChild(canGetHint);
//				exam.appendChild(partialScore);
//				exam.appendChild(submitOnlyAC);
//				exam.appendChild(language);
//				exam.appendChild(teacherId);
//				exam.appendChild(status);
//				exam.appendChild(updateTime);
//
//				root.appendChild(exam);
//			}
//
//			xmlStr = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlStr;
//	}
//
//	/**
//	 * 生成XML格式的字符串 将ProblemsList即考试列表转换成xml格式
//	 */
//	public String ProblemsListToXml(PMProblemInfo proInfo,
//			List<Problemtestcases> procasesList) {
//        String xmlStr = "";
//        try {
//            // 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//            DocumentBuilderFactory factory = DocumentBuilderFactory
//                    .newInstance();
//            // 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            // Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//            Document document = builder.newDocument();
//            Element root = document.createElement("root");
//            document.appendChild(root);
//            Element rspMsg = document.createElement("rspMsg");
//            rspMsg.setTextContent("Success");
//            root.appendChild(rspMsg);
//
//            Element time = document.createElement("time");
//            time.setTextContent(timeStr);
//            root.appendChild(time);
//            Element TestCases = document.createElement("TestCases");
//            if (procasesList != null) {
//                for (int i = 0; i < procasesList.size(); i++) {
//                    Element Case = document.createElement("Case");
//
//                    Element id = document.createElement("id");
//                    Element problemId = document.createElement("problemId");
//                    Element input = document.createElement("input");
//                    Element output = document.createElement("output");
//
//                    id.setTextContent(procasesList.get(i).getId().toString());
//                    input.setTextContent(procasesList.get(i).getInput());
//                    output.setTextContent(procasesList.get(i).getOutput());
//
//                    Case.appendChild(id);
//                    Case.appendChild(problemId);
//                    Case.appendChild(input);
//                    Case.appendChild(output);
//
//                    TestCases.appendChild(Case);
//                }
//
//                Element id = document.createElement("id");
//                Element title = document.createElement("title");
//                Element description = document.createElement("description");
//
//                Element memory_limit = document.createElement("memory_limit");
//                Element time_limit = document.createElement("time_limit");
//                Element inputRequirement = document
//                        .createElement("inputRequirement");
//
//                Element outputRequirement = document
//                        .createElement("outputRequirement");
//                Element sample_input = document.createElement("sample_input");
//                Element sample_output = document.createElement("sample_output");
//
//                Element author = document.createElement("author");
//                Element difficulty = document.createElement("difficulty");
//                Element scoreGrade = document.createElement("scoreGrade");
//
//                Element chapterName = document.createElement("chapterName");
//                Element cheakSimilarity = document
//                        .createElement("cheakSimilarity");
//                Element similarityThreshold = document
//                        .createElement("similarityThreshold");
//                Element updateTime = document.createElement("updateTime");
//
//                id.setTextContent(proInfo.getId().toString());
//                title.setTextContent(proInfo.getTitle());
//                description.setTextContent(proInfo.getDescription());
//
//                memory_limit.setTextContent(String.valueOf(proInfo
//                        .getMemoryLimit()));
//                time_limit
//                        .setTextContent(String.valueOf(proInfo.getTimeLimit()));
//                inputRequirement.setTextContent(proInfo.getInputRequirement());
//
//                outputRequirement
//                        .setTextContent(proInfo.getOutputRequirement());
//                sample_input.setTextContent(proInfo.getSampleInput());
//                sample_output.setTextContent(proInfo.getSampleOuput());
//
//                author.setTextContent(proInfo.getAuthor());
//                difficulty.setTextContent(proInfo.getDifficulty());
//                scoreGrade.setTextContent(proInfo.getScoreGrade());
//
//                chapterName.setTextContent(proInfo.getChapterName());
//                cheakSimilarity.setTextContent(String.valueOf(proInfo
//                        .isCheckSimilarity()));
//                similarityThreshold.setTextContent(String.valueOf(proInfo
//                        .getSimilarityThreshold()));
//
//                String utime = null;
//                if (proInfo.getUpdateTime() != null) {
//                    utime = ChangeDateToString(proInfo.getUpdateTime()
//                            .toString());
//                }
//                updateTime.setTextContent(utime);
//
//                root.appendChild(id);
//                root.appendChild(title);
//                root.appendChild(description);
//
//                root.appendChild(memory_limit);
//                root.appendChild(time_limit);
//                root.appendChild(inputRequirement);
//
//                root.appendChild(outputRequirement);
//                root.appendChild(sample_input);
//                root.appendChild(sample_output);
//
//                root.appendChild(author);
//                root.appendChild(difficulty);
//                root.appendChild(scoreGrade);
//
//                root.appendChild(chapterName);
//                root.appendChild(cheakSimilarity);
//                root.appendChild(similarityThreshold);
//                root.appendChild(updateTime);
//
//                root.appendChild(TestCases);
//            }
//            xmlStr = DocumentToString(document);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！----util</rspMsg><time>"
//                    + timeStr + "</time></root>";
//        }
//        return xmlStr;
//	}
//
//	/**
//	 * 生成XML格式的字符串 将ExamproblemsList即考试列表转换成xml格式
//	 */
//	public String ExamproblemsListToXml(List<Examproblems> examproblemsList) {
//		String xmlStr = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//			Element root = document.createElement("root");
//			document.appendChild(root);
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//			if (examproblemsList != null) {
//				for (int i = 0; i < examproblemsList.size(); i++) {
//					Element problem = document.createElement("problem");
//					Element problemId = document.createElement("problemId");
//					Element score = document.createElement("score");
//					Element bestBefore = document.createElement("bestBefore");
//					Element scoreCoef = document.createElement("scoreCoef");
//					Element deadline = document.createElement("deadline");
//					Element displaySequence = document
//							.createElement("displaySequence");
//					Element updateTime = document.createElement("updateTime");
//
//					problemId.setTextContent(examproblemsList.get(i)
//							.getProblemId().toString());
//
//					score.setTextContent(String.valueOf(examproblemsList.get(i)
//							.getScore()));
//					String btime = null;
//					if (examproblemsList.get(i).getBestBefore() != null) {
//						btime = ChangeDateToString(examproblemsList.get(i)
//								.getBestBefore().toString());
//					}
//					bestBefore.setTextContent(btime);
//
//					String scoreCoefString = null;
//					if (String.valueOf(examproblemsList.get(i).getScoreCoef()) != null) {
//						scoreCoefString = String.valueOf(examproblemsList
//								.get(i).getScoreCoef());
//					}
//					scoreCoef.setTextContent(scoreCoefString);
//
//					String deadlineStr = null;
//					if (examproblemsList.get(i).getDeadline() != null) {
//						deadlineStr = ChangeDateToString(examproblemsList
//								.get(i).getDeadline().toString());
//					}
//					deadline.setTextContent(deadlineStr);
//					displaySequence.setTextContent(examproblemsList.get(i)
//							.getDisplaySequence().toString());
//					String utime = null;
//					if (examproblemsList.get(i).getUpdateTime() != null) {
//						utime = ChangeDateToString(examproblemsList.get(i)
//								.getUpdateTime().toString());
//					}
//					updateTime.setTextContent(utime);
//
//					problem.appendChild(problemId);
//					problem.appendChild(score);
//					problem.appendChild(bestBefore);
//					problem.appendChild(scoreCoef);
//					problem.appendChild(deadline);
//					problem.appendChild(displaySequence);
//					problem.appendChild(updateTime);
//
//					root.appendChild(problem);
//				}
//			}
//			xmlStr = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlStr;
//	}
//
//	// TODO add by 周海水
//	public String ExamproblemsInfoListToXml(List<PMProblems> examproblemsList) {
//        String xmlStr = "";
//        try {
//            // 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//            DocumentBuilderFactory factory = DocumentBuilderFactory
//                    .newInstance();
//            // 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            // Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//            Document document = builder.newDocument();
//            Element root = document.createElement("root");
//            document.appendChild(root);
//            Element rspMsg = document.createElement("rspMsg");
//            rspMsg.setTextContent("Success");
//            root.appendChild(rspMsg);
//
//            Element time = document.createElement("time");
//            time.setTextContent(timeStr);
//            root.appendChild(time);
//            if (examproblemsList != null) {
//                // p.id, ep.score,ep.displaySequence, p.title, p.difficulty
//                for (int i = 0; i < examproblemsList.size(); i++) {
//                    Element problem = document.createElement("problem");
//                    Element problemId = document.createElement("problemId");
//                    Element score = document.createElement("score");
//
//                    Element displaySequence = document
//                            .createElement("displaySequence");
//                    Element title = document.createElement("title");
//                    Element difficulty = document.createElement("difficulty");
//                    Element updateTime = document.createElement("updateTime");
//                    Element deadline = document.createElement("deadline");
//                    Element bestBefore = document.createElement("bestBefore");
//                    Element scoreCoef = document.createElement("scoreCoef");
//
//                    PMProblems examProblem = examproblemsList.get(i);
//                    problemId.setTextContent(examProblem.getId().toString());
//                    score.setTextContent(String.valueOf(examProblem.getProblemScore()));
//                    displaySequence.setTextContent(examProblem.getDisplaySequence().toString());
//                    title.setTextContent(examProblem.getTitle().toString());
//                    difficulty.setTextContent(examProblem.getDifficulty().toString());
//                    if (examProblem.getUpdateTime() == null) {
//                        updateTime.setTextContent("");
//                    } else {
//                        updateTime.setTextContent(ChangeDateToString(examProblem.getUpdateTime().toString()));
//                    }
//                    if (examProblem.getDeadline() == null) {
//                        deadline.setTextContent("");
//                    } else {
//                        deadline.setTextContent(examProblem.getDeadline().toString());
//                    }
//                    if (examProblem.getBestBefore() == null) {
//                        bestBefore.setTextContent("");
//                        scoreCoef.setTextContent("");
//                    } else {
//                        bestBefore.setTextContent(examProblem.getBestBefore().toString());
//                        scoreCoef.setTextContent(String.valueOf(examProblem.getScoreCoef()));
//                    }
//
//                    problem.appendChild(problemId);
//                    problem.appendChild(score);
//                    problem.appendChild(displaySequence);
//                    problem.appendChild(title);
//                    problem.appendChild(difficulty);
//                    problem.appendChild(updateTime);
//                    problem.appendChild(deadline);
//                    problem.appendChild(bestBefore);
//                    problem.appendChild(scoreCoef);
//
//                    root.appendChild(problem);
//                }
//            }
//            xmlStr = DocumentToString(document);
//
//        } catch (Exception e) {
//            xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//                    + timeStr + "</time></root>";
//        }
//        return xmlStr;
//}
//
//	public String ExamdetailListToXml(
//			List<Studentexamdetail> studentexamdetailList) {
//		String xmlStr = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//			Element root = document.createElement("root");
//			document.appendChild(root);
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//			if (studentexamdetailList != null) {
//				for (int i = 0; i < studentexamdetailList.size(); i++) {
//					Element problem = document.createElement("problem");
//
//					Element solutionId = document.createElement("solutionId");
//					Element problemId = document.createElement("problemId");
//					Element status = document.createElement("status");
//					Element hintCases = document.createElement("hintCases");
//					Element score = document.createElement("score");
//					Element elapsedTime = document.createElement("elapsedTime");
//					Element finished = document.createElement("finished");
//
//					solutionId.setTextContent(studentexamdetailList.get(i)
//							.getSolutionId().toString());
//					problemId.setTextContent(studentexamdetailList.get(i)
//							.getProblemId().toString());
//					status.setTextContent(studentexamdetailList.get(i)
//							.getStatus());
//					String hintCasesStr = null;
//					if (!studentexamdetailList.get(i).getHintCases()
//							.equals("null hintCases")) {
//						hintCasesStr = studentexamdetailList.get(i)
//								.getHintCases();
//					}
//					hintCases.setTextContent(hintCasesStr);
//					score.setTextContent(String.valueOf(studentexamdetailList
//							.get(i).getScore()));
//					elapsedTime.setTextContent(studentexamdetailList.get(i)
//							.getElapsedTime().toString());
//					finished.setTextContent(String
//							.valueOf(studentexamdetailList.get(i).isFinished()));
//
//					problem.appendChild(solutionId);
//					problem.appendChild(problemId);
//					problem.appendChild(status);
//					problem.appendChild(hintCases);
//					problem.appendChild(score);
//					problem.appendChild(elapsedTime);
//					problem.appendChild(finished);
//					root.appendChild(problem);
//				}
//			}
//			xmlStr = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlStr;
//	}
//
//	public String ExamProblemdetailToXml(Studentexamdetail studentexamdetail) {
//		// TODO Auto-generated method stub
//		String xmlStr = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//			Element root = document.createElement("root");
//			document.appendChild(root);
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//			if (studentexamdetail != null) {
//				Element problem = document.createElement("problem");
//				Element solutionId = document.createElement("solutionId");
//				Element problemId = document.createElement("problemId");
//				Element status = document.createElement("status");
//				Element hintCases = document.createElement("hintCases");
//				Element score = document.createElement("score");
//				Element elapsedTime = document.createElement("elapsedTime");
//				Element finished = document.createElement("finished");
//
//				solutionId.setTextContent(studentexamdetail.getSolutionId()
//						.toString());
//				problemId.setTextContent(studentexamdetail.getProblemId()
//						.toString());
//				status.setTextContent(studentexamdetail.getStatus());
//				String hintCasesStr = null;
//				if (!studentexamdetail.getHintCases().equals("null hintCases")) {
//					hintCasesStr = studentexamdetail.getHintCases();
//				}
//				hintCases.setTextContent(hintCasesStr);
//				score.setTextContent(String.valueOf(studentexamdetail
//						.getScore()));
//				elapsedTime.setTextContent(studentexamdetail.getElapsedTime()
//						.toString());
//				finished.setTextContent(String.valueOf(studentexamdetail
//						.isFinished()));
//
//				problem.appendChild(solutionId);
//				problem.appendChild(problemId);
//				problem.appendChild(status);
//				problem.appendChild(hintCases);
//				problem.appendChild(score);
//				problem.appendChild(elapsedTime);
//				problem.appendChild(finished);
//				root.appendChild(problem);
//			}
//
//			xmlStr = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlStr;
//	}
//
//	public String SubmittedcodeListToXml(List<Submittedcode> submittedcodeList) {
//		String xmlStr = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//			Element root = document.createElement("root");
//			document.appendChild(root);
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//			if (submittedcodeList != null) {
//				for (int i = 0; i < submittedcodeList.size(); i++) {
//					Element record = document.createElement("record");
//
//					Element id = document.createElement("id");
//					Element solutionId = document.createElement("solutionId");
//					Element code = document.createElement("code");
//
//					id.setTextContent(submittedcodeList.get(i).getId()
//							.toString());
//					solutionId.setTextContent(submittedcodeList.get(i)
//							.getSolutionId().toString());
//					code.setTextContent(submittedcodeList.get(i)
//							.getProcessedCode1());
//
//					record.appendChild(id);
//					record.appendChild(solutionId);
//					record.appendChild(code);
//
//					root.appendChild(record);
//				}
//			}
//			xmlStr = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlStr;
//	}
//
//	public String ViewWrongCaseToXml(PMWrongAndCorrect pMWrongAndCorrect) {
//		String xmlStr = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//			Element root = document.createElement("root");
//			document.appendChild(root);
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//			Element score = document.createElement("score");
//			score.setTextContent(String.valueOf(pMWrongAndCorrect.getScore()));
//			root.appendChild(score);
//			if (pMWrongAndCorrect.isCaseInfo()) {
//
//				Element solutionId = document.createElement("solutionId");
//				Element caseId = document.createElement("caseId");
//				Element input = document.createElement("input");
//				Element output = document.createElement("output");
//				Element wrongoutput = document.createElement("wrongoutput");
//
//				solutionId.setTextContent(String.valueOf(pMWrongAndCorrect
//						.getSolutionId()));
//				caseId.setTextContent(pMWrongAndCorrect.getProblemtestcases()
//						.getId().toString());
//				input.setTextContent(pMWrongAndCorrect.getProblemtestcases()
//						.getInput());
//				output.setTextContent(pMWrongAndCorrect.getProblemtestcases()
//						.getOutput());
//				wrongoutput.setTextContent(pMWrongAndCorrect.getWrongcases()
//						.getOutput());
//
//				root.appendChild(solutionId);
//				root.appendChild(caseId);
//				root.appendChild(input);
//				root.appendChild(output);
//				root.appendChild(wrongoutput);
//
//			} else {
//				xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>Success</rspMsg><time>"
//						+ timeStr + "</time></root>";
//			}
//			xmlStr = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlStr;
//	}
//
//	public String ExamProblemStatusToXml(
//			PMWrongAndCorrectIds pMWrongAndCorrectIds) {
//		String xmlStr = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//			Element root = document.createElement("root");
//			document.appendChild(root);
//			if (pMWrongAndCorrectIds != null) {
//				Element rspMsg = document.createElement("rspMsg");
//				rspMsg.setTextContent("Success");
//				root.appendChild(rspMsg);
//
//				Element time = document.createElement("time");
//				time.setTextContent(timeStr);
//				root.appendChild(time);
//
//				Element status = document.createElement("status");
//				Element hintCases = document.createElement("hintCases");
//				Element score = document.createElement("score");
//				Element elapsedtime = document.createElement("elapsedtime");
//				Element finished = document.createElement("finished");
//				Element remark = document.createElement("remark");
//				Element solutionId = document.createElement("solutionId");
//				Element code = document.createElement("code");
//				Element submit = document.createElement("submit");
//				Element correctCaseIds = document
//						.createElement("correctCaseIds");
//
//				status.setTextContent(pMWrongAndCorrectIds.getStatus());
//				if (!pMWrongAndCorrectIds.getHintCases().equals(
//						"null hintCases")) {
//					hintCases.setTextContent(pMWrongAndCorrectIds
//							.getHintCases());
//				} else {
//					hintCases.setTextContent("");
//				}
//				score.setTextContent(String.valueOf(pMWrongAndCorrectIds
//						.getScore()));
//				elapsedtime.setTextContent(String.valueOf(pMWrongAndCorrectIds
//						.getElapsedTime()));
//				finished.setTextContent(String.valueOf(pMWrongAndCorrectIds
//						.isFinished()));
//				remark.setTextContent(pMWrongAndCorrectIds.getRemark());
//				solutionId.setTextContent(String.valueOf(pMWrongAndCorrectIds
//						.getSolutionId()));
//				code.setTextContent(pMWrongAndCorrectIds.getCode());
//				submit.setTextContent(String.valueOf(pMWrongAndCorrectIds
//						.getSubmit()));
//
//				StringBuffer corCaseId = new StringBuffer();
//				for (int i = 0; i < pMWrongAndCorrectIds.getCorrectCaseIds().length; i++) {
//					corCaseId
//							.append(pMWrongAndCorrectIds.getCorrectCaseIds()[i]
//									+ ",");
//				}
//				correctCaseIds.setTextContent(corCaseId.toString());
//				root.appendChild(status);
//				root.appendChild(hintCases);
//				root.appendChild(score);
//				root.appendChild(elapsedtime);
//				root.appendChild(finished);
//				root.appendChild(remark);
//				root.appendChild(solutionId);
//				root.appendChild(code);
//				root.appendChild(submit);
//				root.appendChild(correctCaseIds);
//
//				Element wrongCase = document.createElement("wrongCase");
//				for (int i = 0; i < pMWrongAndCorrectIds.getWrongcases().size(); i++) {
//					Element Case = document.createElement("Case");
//
//					Element caseId = document.createElement("caseId");
//					Element output = document.createElement("output");
//
//					caseId.setTextContent(pMWrongAndCorrectIds.getWrongcases()
//							.get(i).getCaseId().toString());
//					output.setTextContent(pMWrongAndCorrectIds.getWrongcases()
//							.get(i).getOutput());
//
//					Case.appendChild(caseId);
//					Case.appendChild(output);
//
//					wrongCase.appendChild(Case);
//				}
//				root.appendChild(wrongCase);
//			} else {
//				xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>Success</rspMsg><time>"
//						+ timeStr + "</time></root>";
//			}
//			xmlStr = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlStr;
//	}
//
//	public static Document getDocument(String str)
//			throws ParserConfigurationException, SAXException, IOException {
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//		DocumentBuilder builder = factory.newDocumentBuilder();
//		// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//		StringReader read = new StringReader(str);
//		// 创建新的输入源 解析器将使用 InputSource 对象来确定如何读取 XML 输入
//		InputSource source = new InputSource(read);
//		Document document = builder.parse(source);
//		return document;
//	}
//
//	// 提交代码获取xml格式的字符串封装到solution对象中
//	public Solution SubmitCodeXmltoSolution(String codeXml) {
//		try {
//			Document document = getDocument(codeXml);
//			Solution sol = new Solution();
//			NodeList list = document.getElementsByTagName("solution");
//			for (int i = 0; i < list.getLength(); i++) {
//				Element ele = (Element) list.item(i);
//				sol.setExamId(Integer.valueOf(ele
//						.getElementsByTagName("examId").item(0)
//						.getTextContent()));
//				sol.setProblemId(Integer.valueOf(ele
//						.getElementsByTagName("problemId").item(0)
//						.getTextContent()));
//				sol.setLanguage(ele.getElementsByTagName("language").item(0)
//						.getTextContent());
//				sol.setSourceCode(ele.getElementsByTagName("sourceCode")
//						.item(0).getTextContent());
//				sol.setStatus(ele.getElementsByTagName("status").item(0)
//						.getTextContent());
//				sol.setCorrectCaseIds(ele
//						.getElementsByTagName("correctCaseIds").item(0)
//						.getTextContent());
//				sol.setRemark(ele.getElementsByTagName("remark").item(0)
//						.getTextContent());
//			}
//			return sol;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	// 提交代码获取xml格式的字符串封装到Wrongcases对象中
//	public List<Wrongcases> SubmitCodeXmltoWrongcases(String codeXml) {
//		try {
//			Document document = getDocument(codeXml);
//			List<Wrongcases> wrongcases = new ArrayList();
//			NodeList list = document.getElementsByTagName("case");
//			for (int i = 0; i < list.getLength(); i++) {
//				Element ele = (Element) list.item(i);
//				Wrongcases wrc = new Wrongcases();
//				wrc.setCaseId(Integer.valueOf(ele
//						.getElementsByTagName("caseId").item(0)
//						.getTextContent()));
//				wrc.setOutput(ele.getElementsByTagName("output").item(0)
//						.getTextContent());
//				wrongcases.add(wrc);
//			}
//			return wrongcases;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	// 提交本题获取xml格式的字符串封装到solution对象中,类PMSubmitProblemInfo是solution类的复制e
//	public PMSubmitProblemInfo SubProblemXmltoSolution(String codeXml) {
//		try {
//			Document document = getDocument(codeXml);
//			PMSubmitProblemInfo sol = new PMSubmitProblemInfo();
//			NodeList list = document.getElementsByTagName("root");
//			for (int i = 0; i < list.getLength(); i++) {
//				Element ele = (Element) list.item(i);
//				sol.setExamId(Integer.valueOf(ele
//						.getElementsByTagName("examId").item(0)
//						.getTextContent()));
//				sol.setProblemId(Integer.valueOf(ele
//						.getElementsByTagName("problemId").item(0)
//						.getTextContent()));
//				sol.setSimilarity(Float.valueOf(ele
//						.getElementsByTagName("similarity").item(0)
//						.getTextContent()));
//				if (ele.getElementsByTagName("similarId").item(0) != null) {
//					sol.setSimilarId(Integer.valueOf(ele
//							.getElementsByTagName("similarId").item(0)
//							.getTextContent()));
//				}
//				sol.setOverSimilarity(Boolean.valueOf(ele
//						.getElementsByTagName("isOverSimilarity").item(0)
//						.getTextContent()));
//				sol.setIfSubmit(Boolean.valueOf(ele
//						.getElementsByTagName("ifSubmit").item(0)
//						.getTextContent()));
//			}
//			return sol;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	// 获得裁判机需要裁判的solution等信息
//	public String GetSolutionsToXml(List<Solution> solutionList) {
//		String xmlStr = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//			Element root = document.createElement("root");
//			document.appendChild(root);
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//
//			for (int i = 0; i < solutionList.size(); i++) {
//				Element solution = document.createElement("solution");
//				Element solutionId = document.createElement("solutionId");
//				Element problemId = document.createElement("problemId");
//				Element submitTime = document.createElement("submitTime");
//				Element language = document.createElement("language");
//				Element code = document.createElement("code");
//
//				solutionId.setTextContent(String.valueOf(solutionList.get(i)
//						.getId()));
//				problemId.setTextContent(String.valueOf(solutionList.get(i)
//						.getProblemId()));
//				String stime = ChangeDateToString(solutionList.get(i)
//						.getSubmitTime().toString());
//				submitTime.setTextContent(stime);
//				language.setTextContent(solutionList.get(i).getLanguage());
//				code.setTextContent(solutionList.get(i).getSourceCode());
//
//				solution.appendChild(solutionId);
//				solution.appendChild(problemId);
//				solution.appendChild(submitTime);
//				solution.appendChild(language);
//				solution.appendChild(code);
//
//				root.appendChild(solution);
//			}
//
//			xmlStr = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlStr;
//	}
//
//	// 获取裁判后的xml的字符串封装到solution对象中
//	public Solution UpdateResultXmltoSolution(String resultXml) {
//		try {
//			Document document = getDocument(resultXml);
//			Solution sol = new Solution();
//			NodeList list = document.getElementsByTagName("solution");
//			for (int i = 0; i < list.getLength(); i++) {
//				Element ele = (Element) list.item(i);
//				sol.setId(Integer.valueOf(ele
//						.getElementsByTagName("solutionId").item(0)
//						.getTextContent()));
//				sol.setProblemId(Integer.valueOf(ele
//						.getElementsByTagName("problemId").item(0)
//						.getTextContent()));
//				sol.setStatus(ele.getElementsByTagName("status").item(0)
//						.getTextContent());
//				sol.setCorrectCaseIds(ele
//						.getElementsByTagName("correctCaseIds").item(0)
//						.getTextContent());
//				sol.setRemark(ele.getElementsByTagName("remark").item(0)
//						.getTextContent());
//			}
//			return sol;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	public String ExamInfoUUIDToXml(int examId, String loginuuid) {
//		// TODO Auto-generated method stub
//		String xmlStr = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//
//			Element root = document.createElement("root");
//			document.appendChild(root);
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//			Element IsPermit = document.createElement("IsPermit");
//
//			Element id = document.createElement("id");
//			Element uuid = document.createElement("uuid");
//			id.setTextContent(String.valueOf(examId));
//			uuid.setTextContent(loginuuid);
//			IsPermit.appendChild(id);
//			IsPermit.appendChild(uuid);
//			root.appendChild(IsPermit);
//
//			xmlStr = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlStr;
//	}
//
//	public String ExamProCatagoryInfoListToXml(List<Map<String,Object>> examProCatgoryList,List<StudentTrainCatDetail> trainCatDetailList,
//			Map<String,Object> data){
//		String xmlString = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder;
//			builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//			Element root = document.createElement("root");
//			document.appendChild(root);
//
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//
//			if(examProCatgoryList != null)
//			{
//				Element catagoryList = document.createElement("catagoryList");
//				root.appendChild(catagoryList);
//				for(int i = 0;i < examProCatgoryList.size();i++)
//				{
//					Element catagory = document.createElement("catagory");
//
//					Element id = document.createElement("id");
//					Element rowX = document.createElement("rowX");
//					Element colY = document.createElement("colY");
//					Element name = document.createElement("name");
//					Element description = document.createElement("description");
//					Element lowerlimit = document.createElement("lowerlimit");
//					Element upperlimit = document.createElement("upperlimit");
//					Element totalscore = document.createElement("totalscore");
//					Element bestBefore = document.createElement("bestBefore");
//					Element scoreCoef = document.createElement("scoreCoef");
//					Element deadline = document.createElement("deadline");
//
//
//					id.setTextContent(examProCatgoryList.get(i).get("secCatId").toString());
//					rowX.setTextContent(examProCatgoryList.get(i).get("rowX").toString());
//					colY.setTextContent(examProCatgoryList.get(i).get("colY").toString());
//					name.setTextContent(examProCatgoryList.get(i).get("name").toString());
//					description.setTextContent(examProCatgoryList.get(i).get("description").toString());
//					lowerlimit.setTextContent(examProCatgoryList.get(i).get("lowerLimit").toString());
//					upperlimit.setTextContent(examProCatgoryList.get(i).get("upperLimit").toString());
//					totalscore.setTextContent(examProCatgoryList.get(i).get("score").toString());
//
//					if(examProCatgoryList.get(i).get("bestBefore") != null)
//						bestBefore.setTextContent(examProCatgoryList.get(i).get("bestBefore").toString());
//					else
//						bestBefore.setTextContent("");
//
//					if(examProCatgoryList.get(i).get("scoreCoef") != null)
//						scoreCoef.setTextContent(examProCatgoryList.get(i).get("scoreCoef").toString());
//					else
//						scoreCoef.setTextContent("");
//
//					if(examProCatgoryList.get(i).get("deadline") != null)
//						deadline.setTextContent(examProCatgoryList.get(i).get("deadline").toString());
//					else
//						deadline.setTextContent("");
//
//					catagory.appendChild(id);
//					catagory.appendChild(rowX);
//					catagory.appendChild(colY);
//					catagory.appendChild(name);
//					catagory.appendChild(description);
//					catagory.appendChild(lowerlimit);
//					catagory.appendChild(upperlimit);
//					catagory.appendChild(totalscore);
//					catagory.appendChild(bestBefore);
//					catagory.appendChild(scoreCoef);
//					catagory.appendChild(deadline);
//
//					catagoryList.appendChild(catagory);
//				}
//			}
//
//			if(trainCatDetailList != null)
//			{
//				Element CatDetailList = document.createElement("trainCatDetailList");
//				root.appendChild(CatDetailList);
//				for(int i = 0;i < trainCatDetailList.size();i++)
//				{
//					Element catagory = document.createElement("catagory");
//
//					Element id = document.createElement("id");
//					Element score = document.createElement("score");
//					Element finished = document.createElement("finished");
//
//					id.setTextContent(trainCatDetailList.get(i).getCatId().toString());
//					score.setTextContent(String.valueOf(trainCatDetailList.get(i).getScore()));
//					finished.setTextContent(String.valueOf(trainCatDetailList.get(i).isFinished()));
//
//					catagory.appendChild(id);
//					catagory.appendChild(score);
//					catagory.appendChild(finished);
//
//					CatDetailList.appendChild(catagory);
//				}
//			}
//
//			Element progress = document.createElement("progress");
//			root.appendChild(progress);
//
//			Element examId = document.createElement("examId");
//			examId.setTextContent(data.get("examId").toString());
//			progress.appendChild(examId);
//
//			Element currentProcategory = document.createElement("currentProcategory");
//			if(data.get("currentProCatagory") != null)
//				currentProcategory.setTextContent(data.get("currentProCatagory").toString());
//			else
//				currentProcategory.setTextContent("");
//			progress.appendChild(currentProcategory);
//
//			Element currentProId = document.createElement("currentProId");
//			if(data.get("currentProId") != null)
//				currentProId.setTextContent(data.get("currentProId").toString());
//			else
//				currentProId.setTextContent("");
//			progress.appendChild(currentProId);
//
//			Element currentProTitle = document.createElement("currentProTitle");
//			if(data.get("currentProTitle") != null)
//				currentProTitle.setTextContent(data.get("currentProTitle").toString());
//			else
//				currentProTitle.setTextContent("");
//			progress.appendChild(currentProTitle);
//
//			xmlString = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//
//		return xmlString;
//	}
//
//	public String CanDoCategoryInfoToXml(Map<String,Object> data){
//		String xmlString = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder;
//			builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//			Element root = document.createElement("root");
//			document.appendChild(root);
//
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//
//			Element currentProcategory = document.createElement("currentProcategory");
//			if(data.get("currentProcategory") != null)
//				currentProcategory.setTextContent(data.get("currentProcategory").toString());
//			else
//				currentProcategory.setTextContent("");
//			root.appendChild(currentProcategory);
//
//			Element choice = document.createElement("choice");
//			if(data.get("choice") != null)
//				choice.setTextContent(data.get("choice").toString());
//			root.appendChild(choice);
//
//			Element catIds = document.createElement("ids");
//			catIds.setTextContent(data.get("catIds").toString());
//			root.appendChild(catIds);
//
//			xmlString = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlString;
//	}
//
//	public String SubmitHistoryToXml(Map<String,Object> data){
//		String xmlString = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder;
//			builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//			Element root = document.createElement("root");
//			document.appendChild(root);
//
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//
//			List<Map<String,Object>> catList = (List<Map<String, Object>>) data.get("categoryList");
//			if(catList != null)
//			{
//				Element categoryList = document.createElement("categoryList");
//				root.appendChild(categoryList);
//				for(int i = 0;i < catList.size();i++)
//				{
//					Element category = document.createElement("category");
//
//					Element id = document.createElement("id");
//					Element rowX = document.createElement("rowX");
//					Element colY = document.createElement("colY");
//					Element name = document.createElement("name");
//					Element description = document.createElement("description");
//					Element lowerlimit = document.createElement("lowerlimit");
//					Element upperlimit = document.createElement("upperlimit");
//					Element score = document.createElement("score");
//					Element totalscore = document.createElement("totalscore");
//					Element finished = document.createElement("finished");
//					Element bestBefore = document.createElement("bestBefore");
//					Element scoreCoef = document.createElement("scoreCoef");
//					Element deadline = document.createElement("deadline");
//
//					id.setTextContent(catList.get(i).get("catId").toString());
//					rowX.setTextContent(catList.get(i).get("rowX").toString());
//					colY.setTextContent(catList.get(i).get("colY").toString());
//					name.setTextContent(catList.get(i).get("name").toString());
//					description.setTextContent(catList.get(i).get("description").toString());
//					lowerlimit.setTextContent(catList.get(i).get("lowerLimit").toString());
//					upperlimit.setTextContent(catList.get(i).get("upperLimit").toString());
//					score.setTextContent(catList.get(i).get("score").toString());
//					totalscore.setTextContent(catList.get(i).get("totalScore").toString());
//					finished.setTextContent(String.valueOf(catList.get(i)));
//					if(catList.get(i).get("bestBefore") != null)
//						bestBefore.setTextContent(catList.get(i).get("bestBefore").toString());
//					else
//						bestBefore.setTextContent("");
//
//					if(catList.get(i).get("scoreCoef") != null)
//						scoreCoef.setTextContent(catList.get(i).get("scoreCoef").toString());
//					else
//						scoreCoef.setTextContent("");
//
//					if(catList.get(i).get("deadline") != null)
//						deadline.setTextContent(catList.get(i).get("deadline").toString());
//					else
//						deadline.setTextContent("");
//
//					category.appendChild(id);
//					category.appendChild(rowX);
//					category.appendChild(colY);
//					category.appendChild(name);
//					category.appendChild(description);
//					category.appendChild(lowerlimit);
//					category.appendChild(upperlimit);
//					category.appendChild(score);
//					category.appendChild(totalscore);
//					category.appendChild(finished);
//					category.appendChild(bestBefore);
//					category.appendChild(scoreCoef);
//					category.appendChild(deadline);
//
//					categoryList.appendChild(category);
//				}
//			}
//
//			List<Map<String,Object>> problemList = (List<Map<String, Object>>) data.get("problemList");
//			if(problemList != null)
//			{
//				Element proList = document.createElement("problemList");
//				root.appendChild(proList);
//				for(int i = 0;i < problemList.size();i++)
//				{
//					Element problem = document.createElement("problem");
//
//					Element id = document.createElement("id");
//					Element catId = document.createElement("catId");
//					Element title = document.createElement("title");
//					Element status = document.createElement("status");
//					Element startTime = document.createElement("startTime");
//
//					id.setTextContent(problemList.get(i).get("problemId").toString());
//					catId.setTextContent(problemList.get(i).get("catId").toString());
//					title.setTextContent(problemList.get(i).get("title").toString());
//					status.setTextContent(problemList.get(i).get("status").toString());
//					startTime.setTextContent(problemList.get(i).get("starttime").toString());
//
//					problem.appendChild(id);
//					problem.appendChild(catId);
//					problem.appendChild(title);
//					problem.appendChild(status);
//					problem.appendChild(startTime);
//
//					proList.appendChild(problem);
//				}
//			}
//
//			Map<String,Object> curProgress = (Map<String, Object>) data.get("progress");
//
//			Element progress = document.createElement("progress");
//			root.appendChild(progress);
//
//			Element examId = document.createElement("examId");
//			examId.setTextContent(curProgress.get("examId").toString());
//			progress.appendChild(examId);
//
//			Element currentProcategory = document.createElement("currentProcategory");
//			if(curProgress.get("currentProcategory") != null)
//				currentProcategory.setTextContent(curProgress.get("currentProcategory").toString());
//			else
//				currentProcategory.setTextContent("");
//			progress.appendChild(currentProcategory);
//
//			Element currentProId = document.createElement("currentProId");
//			if(curProgress.get("currentProId") != null)
//				currentProId.setTextContent(curProgress.get("currentProId").toString());
//			else
//				currentProId.setTextContent("");
//			progress.appendChild(currentProId);
//
//			xmlString = DocumentToString(document);
//
//		} catch (Exception e) {
//			xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlString;
//	}
//
//	public String AfterSubmitProblemDataSourceToXml(Map<String,Object> data){
//		String xmlString = "";
//		try {
//			// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			// 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
//			DocumentBuilder builder;
//			builder = factory.newDocumentBuilder();
//			// Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
//			Document document = builder.newDocument();
//			Element root = document.createElement("root");
//			document.appendChild(root);
//
//			Element rspMsg = document.createElement("rspMsg");
//			rspMsg.setTextContent("Success");
//			root.appendChild(rspMsg);
//
//			Element time = document.createElement("time");
//			time.setTextContent(timeStr);
//			root.appendChild(time);
//
//			Element operation = document.createElement("operation");
//			operation.setTextContent(data.get("operation").toString());
//			root.appendChild(operation);
//
//			if(data.containsKey("passOption") && data.get("passOption") != null)
//			{
//				Element passOption = document.createElement("passOption");
//				root.appendChild(passOption);
//
//				Element choice = document.createElement("choice");
//				choice.setTextContent(data.get("passOption").toString());
//				passOption.appendChild(choice);
//
//				Element ids = document.createElement("ids");
//				ids.setTextContent(data.get("ids").toString());
//				passOption.appendChild(ids);
//			}
//			xmlString = DocumentToString(document);
//		} catch (Exception e) {
//			xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlString;
//	}
//
//
//
//}
