package edu.dhu.dao.impl;

import edu.dhu.dao.ProblemCommentDaoI;
import edu.dhu.model.ProblemComment;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("problemCommentDao")
public class ProblemCommentDaoImpl extends BaseDaoImpl<ProblemComment> implements ProblemCommentDaoI {

	@Override
	public String addProCommentByTch(ProblemComment problemComment) {
		try {
			this.save(problemComment);
			return null;
		} catch (Exception e) {
			return e.toString();
		}
	}

	@Override
	public int getWaitProCommentNum() {
		String hql = "select count(*) from ProblemComment pc where pc.reviewStatus =:reviewStatus ";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("reviewStatus","wait");
		return this.countInt(hql, params);
	}

	@Override
	public List<ProblemComment> getProblemCommentsByWaitStatus(String[] conditions,String[] languages) {
		//String hql = "select pc from ProblemComment pc where pc.reviewStatus =:reviewStatus order by pc.time desc";
		StringBuilder hql = new StringBuilder();
		hql.append("select pc from ProblemComment pc where pc.reviewStatus =:reviewStatus ");
		if(conditions != null){
			hql.append("and ( ");
			for(int i = 0;i < conditions.length;i++){
				hql.append(" pc.keywords like '%"+conditions[i]+"%' ");
				if(i != conditions.length - 1)
					hql.append(" or ");
			}
			hql.append(" ) ");	
		}
		
		if(languages != null){
			hql.append("and ( ");
			for(int i = 0;i < languages.length;i++){
				hql.append(" pc.language like '%"+languages[i]+"%' ");
				if(i != languages.length - 1)
					hql.append(" or ");
			}
			hql.append(" ) ");
		}		
		hql.append(" order by pc.time desc ");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("reviewStatus", "wait");
		return this.find(hql.toString(), params);
	}

	@Override
	public ProblemComment getProblemCommentDetailById(int id) {
		return this.get(ProblemComment.class, id);
	}

	@Override
	public void updateProblemComment(ProblemComment pc) {
		this.update(pc);
	}

	@Override
	public List<ProblemComment> getProblemCommentsByProId(int proId, String status,String[] conditions,String[] languages) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> params = new HashMap<String, Object>();
		hql.append("select pc from ProblemComment pc where pc.problemId=:proId ");
		if(status != null){
			hql.append(" and pc.reviewStatus=:status ");
			params.put("status", status);
		}
		if(conditions != null){
			hql.append("and ( ");
			for(int i = 0;i < conditions.length;i++){
				hql.append(" pc.keywords like '%"+conditions[i]+"%' ");
				if(i != conditions.length - 1)
					hql.append(" or ");
			}
			hql.append(" ) ");	
		}
		
		if(languages != null){
			hql.append("and ( ");
			for(int i = 0;i < languages.length;i++){
				hql.append(" pc.language like '%"+languages[i]+"%' ");
				if(i != languages.length - 1)
					hql.append(" or ");
			}
			hql.append(" ) ");
		}
		
		hql.append(" order by pc.time desc ");
		params.put("proId", proId);
		
		return this.find(hql.toString(), params);
	}

	@Override
	public void delProblemCommentById(ProblemComment pc) {
		this.delete(pc);
	}
	
	
	
}
