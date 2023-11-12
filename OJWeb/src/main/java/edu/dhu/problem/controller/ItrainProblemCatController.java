package edu.dhu.problem.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import edu.dhu.exam.model.ItrainProbCatgory;
import edu.dhu.global.model.RespBean;
import edu.dhu.global.service.LogServiceI;
import edu.dhu.problem.dao.ItrainproblemDaoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/itrainProblemCat")
public class ItrainProblemCatController{
	
//	@Autowired
//	private ItrainProblemCatServiceI iTrainProCatService;
//
//	@Autowired
//	private StudentTrainCatDetailServiceI studentTrainCatDetailService;
//
//	@Autowired
//	private StudentexaminfoServiceI studentexaminfoService;
	
	@Autowired
	private LogServiceI logService;
	
	@Autowired
	private ItrainproblemDaoI itrainproblemDao;

//	//获取该二级分类下的题目列表
//	public void findProblemsBySecCatgory(){
//		// 返回前台的json数据
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//		List<Map<String,Object>> priCatgoryList = iTrainProCatService.findProblemsBySecCatgory(secCatgory_id,examId);
//
//		if (priCatgoryList != null) {
//			j.setSuccess(true);
//			j.setMsg("获取题目列表成功");
//			j.setObj(priCatgoryList);
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("获取题目列表失败");
//			super.writeJson(j);
//		}
//	}
//
//	//获取该二级分类下的可用题目数量
//	public void getUseProNum(){
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//		List<Map<String,Object>> list =  iTrainProCatService.getUseProNum(secCatgory_id,examId);
//
//		if (list != null) {
//			j.setSuccess(true);
//			j.setMsg("获取可用题目数量成功");
//			j.setObj(list);
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("获取可用题目数量失败");
//			super.writeJson(j);
//		}
//	}
//
//
//	//获取该场考试下所有已存在的题目
//	public void findExitsProblemsByExamId(){
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//		List<Map<String,Object>> list = iTrainProCatService.findExitsProblemsByExamId(examId);
//
//		if(list != null){
//			j.setSuccess(true);
//			j.setMsg("获取已存在的题目成功！");
//			j.setObj(list);
//		} else{
//			j.setMsg("获取已存在的题目失败！");
//			j.setSuccess(false);
//		}
//		super.writeJson(j);
//	}
//
//	//获取该考试下的所有分类列表()
//	public void getExamCatagoryList(){
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//		List<Map<String,Object>> list = iTrainProCatService.getExamCatagoryList(examId,secCatgory_id);
//
//		if(list != null){
//			j.setMsg("获取分类数据成功");
//			j.setSuccess(true);
//			j.setObj(list);
//			super.writeJson(j);
//		}else {
//			j.setMsg("获取分类数据失败");
//			j.setSuccess(false);
//			super.writeJson(j);
//		}
//	}
//
//	// 向该考试下的二级分类添加题目
//	public void insertItrainProblem(){
//		Json j = new Json();
//		String[] proId = problemIds.split(",");
//		String[] mandatoryId = mandatoryIds.split(",");
//		String[] duration = durations.split(",");
//
//		int[] proIds = new int[proId.length];
//		int[] manIds = new int[mandatoryId.length];
//		int[] dura = new int[duration.length];
//
//		for(int i = 0;i < proId.length;i++)
//			proIds[i] = Integer.parseInt(proId[i]);
//
//		for(int i = 0;i < duration.length;i++)
//			dura[i] = Integer.parseInt(duration[i]);
//
//		if(mandatoryIds.length() != 0)
//		{
//			for(int k = 0;k < mandatoryId.length;k++)
//				manIds[k] = Integer.parseInt(mandatoryId[k]);
//		}
//
//		String msg = iTrainProCatService.insertItrainProblem(proIds,manIds,dura,itrainProCatgory,position,direction);
//		if(msg == "success")
//		{
//			j.setMsg("添加题目成功！");
//			j.setSuccess(true);
//			super.writeJson(j);
//		}
//		else
//		{
//			j.setMsg("添加题目失败！");
//			j.setSuccess(false);
//			super.writeJson(j);
//		}
//	}
//
//	// 删除该考试下的二级分类
//	public void delExamCatgoryProblem(){
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//		secCatgory_id = itrainProCatgory.getCatId();
//
//		String msg = iTrainProCatService.delExamCatgoryProblem(examId,secCatgory_id);
//
//		if(msg == "success")
//		{
//			j.setMsg("删除考试分类题目成功！");
//			j.setSuccess(true);
//		}
//		else
//		{
//			j.setMsg("删除考试分类题目失败！");
//			j.setSuccess(false);
//		}
//		super.writeJson(j);
//	}
//
//
//	//修改该场考试下的二级分类
//	public void updateItrainProblem(){
//		Json j = new Json();
//
//		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		try {
//			Date bestBefore = sdf.parse(bestB);
//			Date deadline = sdf.parse(deadL);
//			itrainProCatgory.setBestBefore(bestBefore);
//			itrainProCatgory.setDeadline(deadline);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}*/
//
//		String[] id = problemIds.split(",");
//		String[] mandatoryId = mandatoryIds.split(",");
//		String[] duration = durations.split(",");
//
//		int[] proIds = new int[id.length];
//		int[] manIds = new int[mandatoryId.length];
//		int[] dura = new int[duration.length];
//
//		for(int i = 0;i < id.length;i++)
//			proIds[i] = Integer.parseInt(id[i]);
//
//		for(int i = 0;i < duration.length;i++)
//			dura[i] = Integer.parseInt(duration[i]);
//
//		//每道题目对应的分数
//		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
//
//		for(int i = 0;i < proIds.length;i++)
//			map.put(proIds[i], dura[i]);
//
//		if(mandatoryIds.length() != 0)
//		{
//			for(int k = 0;k < mandatoryId.length;k++)
//				manIds[k] = Integer.parseInt(mandatoryId[k]);
//		}
//
//		String info = iTrainProCatService.updateItrainProblem(proIds,manIds,map,itrainProCatgory,position,direction);
//		if(info == "success")
//		{
//			j.setMsg("修改考试题目成功！");
//			j.setSuccess(true);
//			super.writeJson(j);
//		}
//		else
//		{
//			j.setMsg("修改考试题目失败！");
//			j.setSuccess(false);
//			super.writeJson(j);
//		}
//
//	}
//
//	//编辑二级分类做题时间
//	public void updateItrainTime(){
//		Json j = new Json();
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		try {
//			if(bestB != null && !("".equals(bestB))){
//				Date bestBefore = sdf.parse(bestB);
//				itrainProCatgory.setBestBefore(bestBefore);
//			}
//
//			if(deadL != null && !("".equals(deadL))){
//				Date deadline = sdf.parse(deadL);
//				itrainProCatgory.setDeadline(deadline);
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//			return;
//		}
//
//		String info = iTrainProCatService.updateItrainTime(itrainProCatgory);
//
//		if(info == "success")
//		{
//			j.setMsg("修改考试时间成功！");
//			j.setSuccess(true);
//			super.writeJson(j);
//		}
//		else
//		{
//			j.setMsg("修改考试时间失败！");
//			j.setSuccess(false);
//			super.writeJson(j);
//		}
//	}
//
//	//获取做题顺序下拉列表
//	public void getProblemSeq(){
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//		List<Map<String,Object>> list = iTrainProCatService.getProblemSeq(examId);
//
//		if(list != null){
//			j.setMsg("获取最题顺序下拉列表成功");
//			j.setSuccess(true);
//			j.setObj(list);
//		}else{
//			j.setMsg("获取做题顺序下拉列表失败");
//			j.setSuccess(false);
//		}
//		super.writeJson(j);
//	}
//
//	//获取页面做题分类顺序可视化结构图填充数据元
//	public void getExamCatagorySeqGraph(){
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//
//		Map<String,Object> res = iTrainProCatService.getExamCatagorySeqGraph(examId);
//
//		if(res != null){
//			j.setMsg("获取分类顺序可视化结构图填充数据元成功");
//			j.setObj(res);
//			j.setSuccess(true);
//		}else{
//			j.setMsg("获取分类顺序可视化结构图填充数据元失败");
//			j.setSuccess(false);
//		}
//		super.writeJson(j);
//	}
	//更改做题顺序
//	public void updateGraphSeq(){
//		Json j = new Json();
//
//		String info = iTrainProCatService.updateGraphSeq(itrainProCatgory,position,direction);
//
//		if(info == "success")
//		{
//			j.setMsg("修改分类做题顺序成功！");
//			j.setSuccess(true);
//		}else
//		{
//			j.setMsg("修改分类做题顺序失败！");
//			j.setSuccess(false);
//		}
//		super.writeJson(j);
//	}
//
//	//获取学生登录考试类别为智能训练首页数据
//	@ResponseBody
//	public RespBean getStudentItrainCatagoryList(@RequestBody ItrainProbCatgory itrainProCatgory){
//		int examId = itrainProCatgory.getExamId();
//		Map<String,Object> res = iTrainProCatService.getStudentItrainCatagoryList(examId,userId);
//
//		if(!res.isEmpty())
//		{
//			return RespBean.ok("获取数据成功！",res);
//		}else
//		{
//			return RespBean.error("获取数据失败！");
//		}
//	}
//
//	//获取可切换的题目类别
//	public void getCanChangeCategory(){
//		Json j = new Json();
//		Map<String,Object> res = new HashMap<String, Object>();
//		String choice ="";
//
//		examId = itrainProCatgory.getExamId();
//		List<Map<String,Object>> category = studentTrainCatDetailService.getCanChoiceProCategory(userId, examId);
//		res.put("category", category);
//
//		Studentexaminfo examInfo = studentexaminfoService.getStudentexaminfoByUserIdAnExamId(userId, examId);
//		if(examInfo.getCurrentCat() == null)
//			choice = "finished";
//		else
//			choice = studentTrainCatDetailService.getItrainCatChoice(userId,examId,examInfo.getCurrentCat());
//		res.put("choice", choice);
//
//		j.setObj(res);
//		super.writeJson(j);
//	}
//
//	//获取类别描述信息和时间限制信息
//	public void getCatDescriptionAndTimelimit(){
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//
//		Map<String,Object> res = iTrainProCatService.getCatDescriptionAndTimelimit(examId,secCatgory_id);
//
//		if(res != null){
//			j.setMsg("获取数据成功！");
//			j.setObj(res);
//			j.setSuccess(true);
//		}else{
//			j.setMsg("获取数据失败！");
//			j.setSuccess(false);
//		}
//		super.writeJson(j);
//	}
//
//	//"我要抽题"操作获取抽取的题目Id
//	public void drawProblem(){
//		Json j = new Json();
//
//		lock.lock();// 获得锁
//		try {
//			examId = itrainProCatgory.getExamId();
//
//			Map<String,Object> res =studentTrainCatDetailService.getExtractProIdDataSource(userId, examId, secCatgory_id);
//
//			j.setObj(res);
//			super.writeJson(j);
//		} catch (Exception e) {
//			String sOut = "";
//			StackTraceElement[] trace = e.getStackTrace();
//			for (StackTraceElement s : trace) {
//				sOut += "\tat " + s + "\r\n";
//			}
//			// 异常信息最大记录19000个字符，数据库该字段最大为20K
//			int count = sOut.length() > 19000 ? 19000 : sOut.length();
//			sOut = sOut.substring(0, count - 1);
//			int leng = e.getLocalizedMessage().length() > 1800 ? 1800 : e
//					.getLocalizedMessage().length();
//			String localMessage = "";
//			if (e.getLocalizedMessage() != null) {
//				localMessage = e.getLocalizedMessage().substring(0, leng - 1);
//			}
//			Log log = new Log();
//			log.setType("抽取题目");
//			log.setOptime(new Date());
//			log.setUserId(userId);
//			log.setUserType("student");
//			log.setContent(sOut);
//			log.setAbstractContent("学生id:" + userId
//					+ "考试id:" + examId + "类别id:"
//					+ secCatgory_id + "\n" + localMessage);
//			logService.WriteLog(log);
//
//			// 返回前台的json数据
//			j = new Json();
//			j.setSuccess(false);
//			j.setMsg("服务器内部发生错误，请报告管理员。");
//			super.writeJson(j);
//		} finally{
//			lock.unlock();
//		}
//
//	}
//
//	//获取提交本题后的结果
//	public void getSubmitProblemResult(){
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//
//		Map<String,Object> data = studentTrainCatDetailService.getAfterSubmitProblemDataSource(userId,examId,secCatgory_id);
//
//		j.setObj(data);
//		super.writeJson(j);
//	}
//
//	//暂时跳过本题操作
//	public void skipThisProblem(){
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//
//		boolean res = studentTrainCatDetailService.skipThisProblem(userId, examId, secCatgory_id, proId);
//		if(res){
//			j.setMsg("success");
//		}else
//			j.setMsg("failure ");
//
//		super.writeJson(j);
//	}
//
//	//我要通关按钮操作
//	public void passThisCategory(){
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//
//		List<Map<String,Object>> category = studentTrainCatDetailService.passThisCategory(userId,examId,secCatgory_id);
//
//		j.setObj(category);
//		super.writeJson(j);
//	}
//
//	//获取做题记录tree数据源
//	public void getRecordTreeData(){
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//
//		Map<String,Object> res = iTrainProCatService.getRecordTreeData(userId,examId);
//		if(res == null || res.isEmpty()){
//			j.setMsg("该类别没有做题记录!");
//			j.setSuccess(false);
//		}else{
//			j.setMsg("获取该类别做题记录成功！");
//			j.setObj(res);
//			j.setSuccess(true);
//		}
//		super.writeJson(j);
//	}
//
//	//获取页面类别所需要信息
//	public void getCategoryInfo(){
//		Json j = new Json();
//		examId = itrainProCatgory.getExamId();
//
//		Map<String,Object> res = studentTrainCatDetailService.getCategoryInfo(userId,examId,secCatgory_id);
//
//		ItrainProbCatgory ipc = iTrainProCatService.getItrainProCategoryByExamIdAndCatId(examId, secCatgory_id);
//
//		int proFinishedNum = 0;
//		if(res.get("ProbFinished") != null)
//			proFinishedNum = res.get("ProbFinished").toString().split(",").length;
//
//		int proSequenceNum = 0;
//		if(res.get("ProbSequence") != null)
//			proSequenceNum = res.get("ProbSequence").toString().split(",").length;
//
//		Map<String,Object> params = new HashMap<String, Object>();
//		params.put("examId", examId);
//		params.put("catId", secCatgory_id);
//		List<Itrainproblems> itrainproblems = itrainproblemDao.getItrainProblem(params);
//
//		if(itrainproblems.size() > proSequenceNum && proFinishedNum >= 0.8 * ipc.getUpperLimit())
//			res.put("passOption", "continueCurrent");
//		else
//			res.put("passOption", "finished");
//
//		if(res == null || res.isEmpty()){
//			j.setSuccess(false);
//		}else{
//			j.setSuccess(true);
//			j.setObj(res);
//		}
//
//		super.writeJson(j);
//	}
//
//	//获取该场考试类别的数目
//	public void getExamCategoryCount(){
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//
//		if(sessionInfo != null){
//			int num = -1;
//			try {
//				num = iTrainProCatService.getExamCategoryCount(itrainProCatgory.getExamId());
//			} catch (Exception e) {
//				j.setSuccess(false);
//				j.setMsg("获取考试类别数失败");
//				j.setObj(null);
//				logger.info("获取考试类别数失败");
//				super.writeJson(j);
//			}
//			j.setSuccess(true);
//			j.setMsg("获取考试类别数成功");
//			j.setObj(num);
//			logger.info("获取考试类别数成功");
//			super.writeJson(j);
//		}else{
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	//克隆该场智能训练练习
//	public void cloneItrainExam(){
//		Map<String,Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if(sessionInfo != null) {
//			//克隆该场考试的所有分类
//			List<ItrainProbCatgory> newCategoryList = iTrainProCatService.getAllItrainProbCategoryByExamId(itrainProCatgory.getExamId());
//			List<ItrainProbCatgory> exCategoryList = iTrainProCatService.getAllItrainProbCategoryByExamId(oldExamId);
//			int categoryNum = 0;
//			int categoryId = 0;
//			if(newCategoryList.size() == 0){
//				ItrainProbCatgory ipc = new ItrainProbCatgory();
//				for(int i = 0;i < exCategoryList.size();i++){
//					ipc.setExamId(itrainProCatgory.getExamId());
//					ipc.setCatId(exCategoryList.get(i).getCatId());
//					ipc.setProblemNum(exCategoryList.get(i).getProblemNum());
//					ipc.setLowerLimit(exCategoryList.get(i).getLowerLimit());
//					ipc.setUpperLimit(exCategoryList.get(i).getUpperLimit());
//					ipc.setScore(exCategoryList.get(i).getScore());
//					ipc.setRowX(exCategoryList.get(i).getRowX());
//					ipc.setColY(exCategoryList.get(i).getColY());
//
//					String msg = iTrainProCatService.addCategory(ipc);
//
//					if(msg == "success"){
//						categoryNum++;
//					}else{
//						categoryId = exCategoryList.get(i).getCatId();
//						break;
//					}
//				}
//
//				if(categoryNum == exCategoryList.size()){
//					//克隆该场考试所有的题目
//					List<Itrainproblems> newProblemsList = iTrainProCatService.getAllItrainProblemsByExamId(itrainProCatgory.getExamId());
//					List<Itrainproblems> exProblemList = iTrainProCatService.getAllItrainProblemsByExamId(oldExamId);
//					int proNum = 0;
//					int proId = 0;
//					if(newProblemsList.size() == 0){
//						Itrainproblems ip = new Itrainproblems();
//						for(int i = 0;i < exProblemList.size();i++){
//							ip.setExamId(itrainProCatgory.getExamId());
//							ip.setCatId(exProblemList.get(i).getCatId());
//							ip.setProblemId(exProblemList.get(i).getProblemId());
//							ip.setMandatory(exProblemList.get(i).getMandatory());
//							ip.setDuration(30);
//							ip.setCommitPerNum(10);
//
//							String info = iTrainProCatService.addItrainProblems(ip);
//
//							if(info == "success"){
//								proNum++;
//							}else{
//								proId = exProblemList.get(i).getProblemId();
//								break;
//							}
//						}
//						if(proNum == exProblemList.size()){
//							j.setSuccess(true);
//							j.setMsg("克隆考试题目成功");
//							logger.info("克隆考试题目成功");
//						}else{
//							j.setSuccess(false);
//							j.setMsg("克隆考试题目Id:" + proId + "失败");
//							logger.info("克隆考试部分题目Id:" + proId + "失败");
//						}
//					}
//
//				}else{
//					j.setSuccess(false);
//					j.setMsg("克隆考试类别Id:" + categoryId + "失败");
//					logger.info("克隆考试部分类别Id:" + categoryId + "失败");
//				}
//
//			}else{
//				j.setSuccess(false);
//				j.setMsg("只有新的试卷才能克隆，如果想要克隆请先删除本场考试中的所有题目类别。");
//				logger.info("克隆考试部分题目Id:" + categoryId + "失败");
//			}
//			super.writeJson(j);
//		}else{
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
	
}
