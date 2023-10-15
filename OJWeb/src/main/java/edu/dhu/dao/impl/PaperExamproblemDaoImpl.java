package edu.dhu.dao.impl;

import edu.dhu.dao.PaperExamproblemDaoI;
import edu.dhu.model.PaperExamproblems;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("paperexamproblemDao")
public class PaperExamproblemDaoImpl extends BaseDaoImpl<PaperExamproblems> implements
		PaperExamproblemDaoI {

	@Override
	public int getPaperMaxDisplaySequenceByExamId(int paperexamId) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		String hql = "select count(*) from  PaperExamproblems where exam_id=:paperexamId";
		params.put("paperexamId", paperexamId);
		return countInt(hql, params);
	}
	
	@Override
	public void AddDisplaySequence(int paperexamId, int start, int end) {
		Map<String, Object> params = new HashMap<String, Object>();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间显示格式
		String updateTime = sdf.format(date);// 将当前时间格式化为需要的类型
		String createTime = sdf.format(date);
		//System.out.println("进来拉1****2019.9.15");
		String hql = "update PaperExamproblems set create_Time='" + createTime + "',update_Time='" + updateTime +"',displaySequence=displaySequence+1 where examId=:paperexamId and displaySequence>=:start and displaySequence<:end";
		params.put("paperexamId", paperexamId);
		params.put("start", start);
		params.put("end", end);
		executeHql(hql, params);
		//System.out.println("进来拉2****2019.9.15");
		
		String examhql = "update PaperExam set update_Time='" + updateTime
				+ "' where id=" + paperexamId;
		//System.out.println("进来拉3****2019.9.15");
		executeHql(examhql);
	}
	
	@Override
	public int getPaperExamproblemNum(int paperexamId) {
		// TODO Auto-generated method stub

		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select count(*) from PaperExamproblems where examId=:paperexamId";
		params.put("paperexamId", paperexamId);
		return countInt(hql, params);
	}
	
	@Override
	public List<PaperExamproblems> findAllPaperExamProblemsByPaperExamId(int paperexamId){
		
		String hql = "from PaperExamproblems e where e.examId="  + paperexamId;
		return this.find(hql);
		
	}
}
