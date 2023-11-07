package edu.dhu.solution.service.impl;
import edu.dhu.exam.dao.StudentTrainCatDetailDaoI;
import edu.dhu.exam.model.Studentexaminfo;
import edu.dhu.global.model.Constant;
import edu.dhu.global.model.Log;
import edu.dhu.global.util.SimilarityByLine;
import edu.dhu.problem.dao.*;
import edu.dhu.problem.model.*;
import edu.dhu.exam.dao.AdminusersDaoI;
import edu.dhu.exam.dao.StudentexamdetailDaoI;
import edu.dhu.exam.dao.StudentexaminfoDaoI;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.global.service.LogServiceI;
import edu.dhu.problem.service.GradeProblemServiceI;
import edu.dhu.solution.model.Submittedcode;
import edu.dhu.solution.service.SubmittedcodeServiceI;
import edu.dhu.user.model.Adminusers;
import edu.dhu.user.model.Json;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("submittedcodeService")
@Transactional
public class SubmittedcodeServiceImpl implements SubmittedcodeServiceI {
	@Resource
	private SolutionDaoI solutionDao;
	@Resource
	private StudentexamdetailDaoI studentexamdetailDao;
	@Resource
	private StudentexaminfoDaoI studentexaminfoDao;
	@Resource
	private ProblemsDaoI problemsDao;
	@Resource
	private LogServiceI logService;
	@Resource
	private WrongcasesDaoI wrongcasesDao;
	@Resource
	private StudentTrainCatDetailDaoI studentTrainCatDetailDao;
	@Resource
	private ItrainproblemDaoI itrainproblemDao;
	@Resource
	private StudentTrainProbDetailDaoI studentTrainProbDetailDao;
	@Resource
	private AdminusersDaoI adminusersDao;
	@Resource
	private GradeProblemServiceI gradeProblemService;
	@Resource
	private SubmittedcodeDaoI submittedcodeDao;
	@Override
	public Submittedcode getSubmittedcodeBySolutionIdAndProblemId(
			int solutionId, int problemId) {
		return submittedcodeDao.getSubmittedcodeBySolutionIdAndProblemId(
				solutionId, problemId);
	}
	public Solution WS_submitCode(Problems problem, Solution solution,
			Studentexamdetail studentexamdetail, List<Wrongcases> wrongcases,
			Date now, Date startTime, Date endTime) {

		Json json = new Json();
		json = submitCode(problem, solution, studentexamdetail, now, startTime,
				endTime);
		if (json.getMsg().equals("代码提交成功")) {
			// updateWrongCases();
			submitCodeCommon_updateWrongCases(wrongcases, solution);
			return solution;
		} else {
			solution.setId(-1);
			return solution;
		}
	}
	@Override
	public Solution WS_updateResult(Problems problem, Solution solution,List<Wrongcases> wrongcases) {
		try {
			solutionDao.update(solution);
			problemsDao.updateProblems(problem);
			for (int i = 0; i < wrongcases.size(); i++) {

				if (solution.getStatus().equals("WA")
						|| solution.getStatus().equals("PE")
						|| solution.getStatus().equals("RE")
						|| solution.getStatus().equals("TLE")) {
					List<Wrongcases> wcsList = wrongcasesDao
							.getWrongcasesBySolutionID(solution.getId());
					Wrongcases wcs = new Wrongcases();
					boolean flag = false;
					for (int j = 0; j < wcsList.size(); j++) {
						if (wcsList.get(j).getCaseId()
								.equals(wrongcases.get(i).getCaseId())) {
							flag = true;
						}
					}
					if (flag) {
						wcs.setSolutionId(solution.getId());
						wcs.setCaseId(wrongcases.get(i).getCaseId());
						if (solution.getStatus().equals("WA")
								|| solution.getStatus().equals("PE")) {
							wcs.setOutput(wrongcases.get(i).getOutput());
						} else if (solution.getStatus().equals("RE")
								|| solution.getStatus().equals("TLE")) {
							wcs.setOutput(solution.getRemark());
						}
						wrongcasesDao.alterWrongcasesBysolutionIdAndCaseId(wcs);
					} else {
						wcs.setSolutionId(solution.getId());
						wcs.setCaseId(wrongcases.get(i).getCaseId());
						//过滤ansii值
						String output=wrongcases.get(i).getOutput();
						StringBuffer replaceResult = new StringBuffer();
				        int index = output.indexOf("&#");  //output:"abc&#0;def"
				        while (index >= 0) {
				            replaceResult.append(output.substring(0, index));//replaceResult:"abc"
				            output = output.substring(index + 2);//output:"0;def"

				            int indexColon = output.indexOf(";");
				            if (indexColon < 0) {
				                replaceResult.append("&#");
				                break;
				            }
				            String num = output.substring(0, indexColon);//num:0
				            if (num.length() > 3) {
				                replaceResult.append("&#" + num + ";");
				            } else {
				                int number = 0;
				                try {
				                    number = Integer.parseInt(num);
				                    replaceResult.append(String.valueOf((char) (number)));
				                } catch (Exception e) {
				                    replaceResult.append("&#" + num + ";");
				                }
				            }

				            output = output.substring(indexColon + 1);//output:"def"
				            index = output.indexOf("&#");
				        }
				        replaceResult.append(output);
				        //replaceResult.trimToSize(64000);
				        output = replaceResult.toString();
						wcs.setOutput(output);
						wrongcasesDao.save(wcs);
					}
				}
			}
			return solution;
		} catch (Exception e) {
			solution.setId(-1);
			return solution;
		}
	}

	@Override
	public Json submitCode(Problems problem, Solution solution,
						   Studentexamdetail studentexamdetail, Date now, Date startTime,
						   Date endTime) {
		// problemsDao.updateAll(problem,
		// solution,studentexamdetail,now,startTime,endTime,j);
		// TODO Auto-generated method stub
		Json j = new Json();
		problemsDao.updateProblems(problem);
		// 插入数据到solution表中
		// 设置分数默认为0分
		solution.setScore(Constant.DEFAULT_SCORE);
		// 设置codelength字段
		solution.setCodelength(solution.getSourceCode().length());
		// 设置相似度默认为－1
		solution.setSimilarity(Constant.DEFAULT_SIMILARITY);
		// solution是否插入成功的标志
		boolean success = false;
		success = solutionDao.submitCode(solution);

		if (success) {
			// 如果studentExamDetail表中存在记录
			if (studentexamdetail != null) {
				// 更新StudentExamDetail表中的solutionId字段
				studentexamdetail.setSolutionId(solution.getId());
				studentexamdetail.setSubmit(studentexamdetail.getSubmit() + 1);
				studentexamdetail.setStatus(solution.getStatus());
				studentexamdetail
						.setElapsedTime((int) (now.getTime() / 1000 - startTime
								.getTime() / 1000));
				// 更新Studentexamdetail表
				studentexamdetailDao.updateStudentexamdetail(studentexamdetail);
			}
			// 第一次提交
			else {
				studentexamdetail = new Studentexamdetail();
				studentexamdetail.setSolutionId(solution.getId());
				studentexamdetail.setExamId(solution.getExamId());
				studentexamdetail.setProblemId(solution.getProblemId());
				studentexamdetail.setUserId(solution.getUserid());
				studentexamdetail.setSubmit(1);
				studentexamdetail.setStatus(solution.getStatus());
				studentexamdetail.setScore(0);
				studentexamdetail.setHintCases(Constant.DEFAULT_HINTCASE);
				studentexamdetail.setFinished(false);
				studentexamdetail
						.setElapsedTime((int) (now.getTime() / 1000 - startTime
								.getTime() / 1000));
				// 插入Studentexamdetail表

				studentexamdetailDao.save(studentexamdetail);
			}
			j.setSuccess(true);
			j.setObj(solution);
			j.setMsg("代码提交成功");

			return j;
		} else {
			j.setSuccess(false);
			j.setMsg("代码提交失败");
			return j;
		}
	}

	// int userId, int examId, int problemId,String status
	@Override
	public boolean submitThisProblem(Solution solu,
			Studentexamdetail stuexamdetail) {
		try {
			int userId = solu.getUserid();
			int problemId = solu.getProblemId();
			int examId = solu.getExamId();
			Solution solution = solu;
			Studentexamdetail studentexamdetail = stuexamdetail;
			// 根据problemID获取problem
			Problems problems = problemsDao.findProblemById(problemId);
			int tid = problems.getTeacherId();
			// 根据教师Id获取教师信息
			Adminusers adminuser = adminusersDao.get(Adminusers.class, tid);

			// 根据userID，examID在Studentexaminfo表中获取记录,如果不存在，则新创建
			Studentexaminfo studentexaminfo = studentexaminfoDao
					.getStudentexaminfoByUserIdAnExamId(userId, examId);
			studentexamdetail.setFinished(true);
			if (solution.getScore() <= 0) {
				// 重新计算分数
				float score = gradeProblemService
						.gradeProblemBySolution(solution);
				// 如果因为其他原因导致裁判机没有裁判而用户进入到提交本题
				if (score < 0) {
					return false;
				}
				studentexamdetail.setScore(score);
			} else {
				studentexamdetail.setScore(solution.getScore());
			}

			studentexaminfo.setSubmit(studentexaminfo.getSubmit()
					+ studentexamdetail.getSubmit());
			studentexaminfo.setScore(studentexaminfo.getScore()
					+ solution.getScore());
			studentexaminfo.setElapsedTime(studentexamdetail.getElapsedTime());
			studentexaminfo.setSubmitTime(new Date());
			if (studentexamdetail.getStatus().equals(Constant.CODE_AC)) {
				studentexaminfo.setSolved(studentexaminfo.getSolved() + 1);

				if (problems.getSolved() == null) {
					problems.setSolved(1);
				} else {
					// 设置这道题目的成功解决加1
					problems.setSolved(problems.getSolved() + 1);
				}
			}
			// 构造将要保存到Submittedcode表中的对象
			Submittedcode submittedcode = new Submittedcode();
			// 设置problemID
			submittedcode.setProblemId(solution.getProblemId());
			// 设置solutionID
			submittedcode.setSolutionId(solution.getId());
			// 设置schoolId
			submittedcode.setSchoolId(adminuser.getSchoolId());
			// 设置time
			submittedcode.setTime(new Date());
			// TODO 预先处理代码
			submittedcode.setProcessedCode1(SimilarityByLine
					.getPreProcessedCode(solution.getSourceCode()));
			// submittedcode.setProcessedCode2(SimilarityByLine.getPreProcessedCode(solution.getSourceCode()));
			// 更新studentexamdetail
			studentexamdetailDao.updateStudentexamdetail(studentexamdetail);

			// 更新problems表
			problemsDao.updateProblems(problems);

			// 设置考试排名
			studentexaminfo.setRank(studentexamdetail.getElapsedTime()
					+ studentexaminfo.getSubmit() * 30);

			// 更新studentexaminfo表
			studentexaminfoDao.update(studentexaminfo);

			// 保存到数据库
			boolean saveflag = false;
			saveflag = submittedcodeDao.saveSubmittedcode(submittedcode);
			return saveflag;
		} catch (Exception e) {
			String sOut = "";
			StackTraceElement[] trace = e.getStackTrace();
			for (StackTraceElement s : trace) {
				sOut += "\tat " + s + "\r\n";
			}
			// 异常信息最大记录19000个字符，数据库该字段最大为20K
			int count = sOut.length() > 19000 ? 19000 : sOut.length();
			sOut = sOut.substring(0, count - 1);
			int leng = e.getLocalizedMessage().length() > 1800 ? 1800 : e
					.getLocalizedMessage().length();
			String localMessage = "";
			if (e.getLocalizedMessage() != null) {
				localMessage = e.getLocalizedMessage().substring(0, leng - 1);
			}
			Log log = new Log();
			log.setType("代码提交");
			log.setOptime(new Date());
			log.setUserId(solu.getUserid());
			log.setUserType("student");
			log.setContent(sOut);
			log.setAbstractContent("学生id:" + solu.getUserid() + "考试id:"
					+ solu.getExamId() + "题目id:" + solu.getProblemId() + "\n"
					+ localMessage);
			logService.WriteLog(log);
			return false;
		}
	}
//
	@Override
	public Solution WS_ItrainsubmitCode(Problems problem, Solution solution, StudentTrainProbDetail stpd,
			List<Wrongcases> wrongcases, Date now, Date startTime, Date endTime) {
		Json json = new Json();
		json = itrainSubmitCode(problem, solution, stpd, now, startTime,
				endTime);
		if (json.getMsg().equals("代码提交成功")) {
			// updateWrongCases();
			submitCodeCommon_updateWrongCases(wrongcases, solution);
			return solution;
		} else {
			solution.setId(-1);
			return solution;
		}
	}
//
	@Override
	public Json itrainSubmitCode(Problems problem, Solution solution, StudentTrainProbDetail stpd, Date now,
			Date startTime, Date endTime) {
		Json j = new Json();
		problemsDao.updateProblems(problem);
		// 插入数据到solution表中
		// 设置分数默认为0分
		solution.setScore(Constant.DEFAULT_SCORE);
		// 设置codelength字段
		solution.setCodelength(solution.getSourceCode().length());
		// 设置相似度默认为－1
		solution.setSimilarity(Constant.DEFAULT_SIMILARITY);
		// solution是否插入成功的标志
		boolean success = false;
		success = solutionDao.submitCode(solution);
		if(success){
			stpd.setSolutionId(solution.getId());
			stpd.setSubmit(stpd.getSubmit()+1);
			stpd.setStatus(solution.getStatus());
			stpd.setElapsedTime((int) (now.getTime() / 1000 - startTime
					.getTime() / 1000));

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String lastSubmitTime = sdf.format(now);
			try {
				stpd.setLastSubmitTime(sdf.parse(lastSubmitTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			studentTrainProbDetailDao.saveOrUpdate(stpd);
			j.setSuccess(true);
			j.setObj(solution);
			j.setMsg("代码提交成功");

			return j;
		}else
		{
			j.setSuccess(false);
			j.setMsg("代码提交失败");
			return j;
		}
	}
//
	@Override
	public String itrainSubmitThisProblem(Solution solu, StudentTrainProbDetail stpd, String continueTrain) {

		String res = "";

		try {
			int userId = solu.getUserid();
			int problemId = solu.getProblemId();
			int examId = solu.getExamId();
			Solution solution = solu;
			StudentTrainProbDetail studentTrainProbdetail = stpd;
			// 根据problemID获取problem
			Problems problems = problemsDao.findProblemById(problemId);
			int tid = problems.getTeacherId();
			// 根据教师Id获取教师信息
			Adminusers adminuser = adminusersDao.get(Adminusers.class, tid);

			// 根据userID，examID在Studentexaminfo表中获取记录,
			Studentexaminfo studentexaminfo = studentexaminfoDao.getStudentexaminfoByUserIdAnExamId(userId, examId);

			//studentTrainProbdetail.setFinished(true);
			if (solution.getScore() <= 0) {
				// 重新计算分数
				float score = gradeProblemService.gradeItrainProblemBySolution(solution,stpd.getCatId());
				// 如果因为其他原因导致裁判机没有裁判而用户进入到提交本题
				if (score < 0) {
					res = "failure";
					return res;
				}
				studentTrainProbdetail.setScoreCoef(score);;
			} else {
				studentTrainProbdetail.setScoreCoef(solution.getScore());
			}

			/*studentexaminfo.setSubmit(studentexaminfo.getSubmit()
					+ studentTrainProbdetail.getSubmit());*/
			studentexaminfo.setSubmitTime(new Date());
			//studentexaminfo.setCurrentProb(null);

			if (studentTrainProbdetail.getStatus().equals(Constant.CODE_AC)) {
				studentexaminfo.setSolved(studentexaminfo.getSolved() + 1);

				if (problems.getSolved() == null) {
					problems.setSolved(1);
				} else {
					// 设置这道题目的成功解决加1
					problems.setSolved(problems.getSolved() + 1);
				}
			}

			// 构造将要保存到Submittedcode表中的对象
			Submittedcode submittedcode = new Submittedcode();
			// 设置problemID
			submittedcode.setProblemId(solution.getProblemId());
			// 设置solutionID
			submittedcode.setSolutionId(solution.getId());
			// 设置schoolId
			submittedcode.setSchoolId(adminuser.getSchoolId());
			// 设置time
			submittedcode.setTime(new Date());
			// 预先处理代码
			submittedcode.setProcessedCode1(SimilarityByLine.getPreProcessedCode(solution.getSourceCode()));

			//更新本关的信息
			StudentTrainCatDetail stcd = studentTrainCatDetailDao.getStudentTrainCatDetailInfo(userId, examId, stpd.getCatId());
			float points = gradeProblemService.calculateItrainProblemPoints(stpd, solu.getSubmitTime());
			stpd.setPoints(points);

			//判断该类别是否已结束
			if(stcd.isFinished())
				res = "afterFinished";
			else{
				//通关之后，提交本题，该题状态不再设置为finished = true
				studentTrainProbdetail.setFinished(true);
				//通关之后，提交本题，该类别points、elapsedTime、submit不再累加
				stcd.setPoints(stcd.getPoints() + points);
				stcd.setElapsedTime(stpd.getElapsedTime());
				stcd.setSubmit(stcd.getSubmit()+stpd.getSubmit());
				res = "beforeFinished";
			}

			if(stcd.getProbFinished() == null)
				stcd.setProbFinished(problemId+"");
			else{
				String[] arr = stcd.getProbFinished().split(",");
				String strPro = String.valueOf(problemId);
				if(!ArrayUtils.contains(arr, strPro))
					stcd.setProbFinished(stcd.getProbFinished()+","+problemId);
			}

			//如果满足通关条件则，计算分数
			if(stcd.getPoints() >= 1){

				float score = gradeProblemService.calculateItrianCategoryScore(stcd);
				//该题完成刚好通关，第一次开始计算本关得分,更新studentexaminfo
				if(!stcd.isFinished()){
					studentexaminfo.setScore(score+studentexaminfo.getScore());
					studentexaminfo.setSubmit(studentexaminfo.getSubmit()+stcd.getSubmit());

					studentexaminfo.setElapsedTime(stcd.getElapsedTime());
					// 设置考试排名
					studentexaminfo.setRank(stcd.getElapsedTime()+ studentexaminfo.getSubmit() * 30);

					//设置本关分数
					stcd.setScore(score);
					stcd.setPassTime(new Date());
				}

				//stcd.setScore(score);
				stcd.setFinished(true);
			}

			//若该题不是从做题记录页面传来，需更新当前题目id为NULL或者做题记录提交的是当前题目也需设置
			if("yes".equals(continueTrain) || (studentexaminfo.getCurrentProb() != null && problemId == studentexaminfo.getCurrentProb())){
				studentexaminfo.setCurrentProb(null);
				stcd.setCurrentProb(null);
			}
			//从做题记录提交题目，该题目是该类别的当前题目(但该类别不是当前该考试当前类别)
			if(stcd.getCurrentProb() != null && problemId == stcd.getCurrentProb())
				stcd.setCurrentProb(null);

			studentTrainCatDetailDao.update(stcd);

			//更新该场考试中分类题目的做题平均时间
			Itrainproblems ip = itrainproblemDao.getItrainPro(examId, problemId);
			float oldDuration = ip.getDuration();
			int commitNum = ip.getCommitPerNum();
			Date now = new Date();
			float duration = (now.getTime()-stpd.getStartTime().getTime())/(1000*60);

			if(duration <= 0.8 * oldDuration)
				duration = (float) (0.8 * oldDuration);

			if(duration >= 3 * oldDuration)
				duration = 3 * oldDuration;

			float newDuration = (oldDuration*commitNum + duration)/(commitNum+1);
			ip.setCommitPerNum(commitNum+1);
			ip.setDuration(newDuration);
			itrainproblemDao.update(ip);


			// 更新studentTrainProbdetail
			studentTrainProbDetailDao.update(studentTrainProbdetail);
			// 更新problems表
			problemsDao.updateProblems(problems);

			// 更新studentexaminfo表
			studentexaminfoDao.update(studentexaminfo);

			// 保存到数据库
			boolean saveflag = false;
			saveflag = submittedcodeDao.saveSubmittedcode(submittedcode);
			if(!saveflag)
				res = "failure";
			return res;

		} catch (Exception e) {
			String sOut = "";
			StackTraceElement[] trace = e.getStackTrace();
			for (StackTraceElement s : trace) {
				sOut += "\tat " + s + "\r\n";
			}
			// 异常信息最大记录19000个字符，数据库该字段最大为20K
			int count = sOut.length() > 19000 ? 19000 : sOut.length();
			sOut = sOut.substring(0, count - 1);
			int leng = e.getLocalizedMessage().length() > 1800 ? 1800 : e
					.getLocalizedMessage().length();
			String localMessage = "";
			if (e.getLocalizedMessage() != null) {
				localMessage = e.getLocalizedMessage().substring(0, leng - 1);
			}
			Log log = new Log();
			log.setType("代码提交");
			log.setOptime(new Date());
			log.setUserId(solu.getUserid());
			log.setUserType("student");
			log.setContent(sOut);
			log.setAbstractContent("学生id:" + solu.getUserid() + "考试id:"
					+ solu.getExamId() + "题目id:" + solu.getProblemId() + "\n"
					+ localMessage);
			logService.WriteLog(log);
			return "failure";
		}
	}
//
//
	public void submitCodeCommon_updateWrongCases(List<Wrongcases> wrongcases,Solution solution){
		for (int i = 0; i < wrongcases.size(); i++) {
			Wrongcases wcs = new Wrongcases();
			wcs.setSolutionId(solution.getId());
			wcs.setCaseId(wrongcases.get(i).getCaseId());

			//过滤ansii值
			String output=wrongcases.get(i).getOutput();
			StringBuffer replaceResult = new StringBuffer();
	        int index = output.indexOf("&#");  //output:"abc&#0;def"
	        while (index >= 0) {
	            replaceResult.append(output.substring(0, index));//replaceResult:"abc"
	            output = output.substring(index + 2);//output:"0;def"

	            int indexColon = output.indexOf(";");
	            if (indexColon < 0) {
	                replaceResult.append("&#");
	                break;
	            }
	            String num = output.substring(0, indexColon);//num:0
	            if (num.length() > 3) {
	                replaceResult.append("&#" + num + ";");
	            } else {
	                int number = 0;
	                try {
	                    number = Integer.parseInt(num);
	                    replaceResult.append(String.valueOf((char) (number)));
	                } catch (Exception e) {
	                    replaceResult.append("&#" + num + ";");
	                }
	            }

	            output = output.substring(indexColon + 1);//output:"def"
	            index = output.indexOf("&#");
	        }
	        replaceResult.append(output);
	        //replaceResult.trimToSize(64000);
	        output = replaceResult.toString();
			wcs.setOutput(output);
			wrongcasesDao.save(wcs);
		}
	}
//
}
