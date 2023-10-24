package edu.dhu.exam.dao.impl;

import edu.dhu.exam.dao.ItrainProblemCatDaoI;
import edu.dhu.exam.model.ItrainProbCatgory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("itrainProblemCatDao")
public class ItrainProblemCatDaoImpl extends BaseDaoImpl<ItrainProbCatgory> implements ItrainProblemCatDaoI {

	@Override
	public List<Map<String, Object>> findProblemsBySecCatgory(int secCatId, int examId) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select p.id,p.title,p.description,ch2.name courseName,ch1.name chapterName, p.source,p.difficulty,p.duration ");
		sql.append(" from problems p ");
		sql.append("left join chapters ch1 on p.chapterCode = ch1.code ");
		sql.append("left join chapters ch2 on ch2.code = substring(ch1.code,1,3) ");
		sql.append("where category like '%," + secCatId + ",%' ");

		sql.append("and not exists (select * from  gdoj.itrainproblems where  p.id = problemId and examId =" + examId
				+ " ");
		// 编辑题目页面使用
		sql.append("and catId !=" + secCatId + ") ");

		System.out.println(sql.toString());
		return this.findBySql(sql.toString(), null);
	}

	@Override
	public List<Map<String, Object>> getUseProNum(int secCatId, int examId) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();

		sql.append("select count(p.id) num from problems p where p.category like '%," + secCatId + ",%' and ");
		sql.append(
				"not exists (select * from itrainproblems where examId =:examId and p.id = problemId and catId !=:secCatId)");

		params.put("examId", examId);
		params.put("secCatId", secCatId);
		System.out.println(sql.toString());
		return this.findBySql(sql.toString(), params);
	}

	@Override
	public List<Map<String, Object>> getExamCatagoryList(int examId, int secCatId) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();

		sql.append("SELECT ip.rowX,ip.colY, ip.examId,ip.catId secCatId,pc2.id priCatId, concat_ws(" + "'->'"
				+ ",pc2.name,pc1.name) name,ip.problemNum,ip.lowerLimit,ip.upperLimit,ip.score,ip.bestBefore,ip.scoreCoef,ip.deadline,pc1.description ");
		sql.append("FROM gdoj.itrainprobcat ip ");
		sql.append("left join gdoj.problemcategory pc1 on ip.catId = pc1.id ");
		sql.append("left join gdoj.problemcategory pc2 on pc1.parentId = pc2.id ");
		sql.append("where ip.examId =:examId ");

		if (secCatId != 0) {
			sql.append(" and ip.catId =:secCatId ");
			params.put("secCatId", secCatId);
		}
		sql.append("order by ip.rowX,ip.colY ");

		params.put("examId", examId);
		System.out.println(sql.toString());
		return this.findBySql(sql.toString(), params);
	}

	/*
	 * @Override public List<Map<String, Object>> getProblemSeq(int examId) {
	 * StringBuffer sql = new StringBuffer(); Map<String,Object> params = new
	 * HashMap<String, Object>();
	 * 
	 * sql.append(" select id,name from gdoj.problemcategory p "); sql.
	 * append(" where exists (select * from gdoj.itrainprobcat ipc left join gdoj.problemcategory pc "
	 * ); sql.
	 * append(" on ipc.catId = pc.id where (p.id = ipc.catId or p.id = pc.parentId) and ipc.examId =:examId) "
	 * );
	 * 
	 * params.put("examId", examId); System.out.println(sql.toString()); return
	 * this.findBySql(sql.toString(), params); }
	 */

	@Override
	public List<Map<String, Object>> getCatagoryByexamId(int examId) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();

		sql.append("select  ip.catId,pc2.id,pc2.name name1,pc1.name name2,ip.rowX,ip.colY from gdoj.itrainprobcat ip ");
		sql.append("left join gdoj.problemcategory pc1 on ip.catId = pc1.id  ");
		sql.append("left join gdoj.problemcategory pc2 on pc1.parentId = pc2.id ");
		sql.append("where ip.examId =:examId ");
		sql.append("order by ip.rowX,ip.colY ");

		params.put("examId", examId);
		System.out.println(sql.toString());
		return this.findBySql(sql.toString(), params);
	}

	@Override
	public List<ItrainProbCatgory> getCatagoryListByExamId(int examId) {
		 String hql = "from ItrainProbCatgory ipc where ipc.examId="+examId;    
		 List<ItrainProbCatgory> itrainProbCatgoryList = this.find(hql);   
		 return itrainProbCatgoryList;
		
	}


	@Override
	public ItrainProbCatgory getItrainProbCatgory(int examId, int catId) {
		String sql = "select ipc from ItrainProbCatgory ipc where examId =:examId and catId =:catId ";
		Map<String,Object> params = new HashMap<String, Object>();
		
		params .put("examId", examId);
		params.put("catId", catId);
		
		ItrainProbCatgory ipc = this.get(sql, params);
		return ipc;
	}

	@Override
	public Map<String, Object> getCatDescriptionAndTimelimit(int examId, int catId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ip.catId,pc.description,ip.bestBefore,ip.scoreCoef,ip.deadline ");
		sql.append("from gdoj.itrainprobcat ip left join gdoj.problemcategory pc on ip.catId = pc.id ");
		sql.append("where ip.examId =:examId and ip.catId =:catId ");
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("examId", examId);
		params.put("catId", catId);
		
		List<Map<String,Object>> list = this.findBySql(sql.toString(), params);
		if(list != null && !list.isEmpty())
			return list.get(0);
		else
			return null;
	}

	@Override
	public int getExamCategoryCount(int examId) {
		Map<String,Object> params = new HashMap<String, Object>();
		String hql = "select count(*) from ItrainProbCatgory where examId =:examId";		
		params.put("examId", examId);
		
		return countInt(hql, params);
	}


	
	
}
