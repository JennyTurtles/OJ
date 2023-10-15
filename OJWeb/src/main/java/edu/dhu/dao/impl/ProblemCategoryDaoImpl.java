package edu.dhu.dao.impl;

import edu.dhu.dao.ProblemCategoryDaoI;
import edu.dhu.model.ProblemCategory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("ProblemCategoryDao")
public class ProblemCategoryDaoImpl extends BaseDaoImpl<ProblemCategory> implements ProblemCategoryDaoI {

	@Override
	public List<ProblemCategory> findAllProblemCategory() {
		String hql = "select pc.id, pc.name, pc.parentId,pc.description, pc.problemNum " + "from ProblemCategory pc ";

		List<Object[]> List = this.relationalQuery(hql);
		// 将problemCategoryList 转换为 List<problemCategoryList>
		List<ProblemCategory> problemCategoryList = new ArrayList<ProblemCategory>();
		for (int i = 0; i < List.size(); i++) {
			// 每行记录不在是一个对象 而是一个数组
			Object[] object = List.get(i);
			ProblemCategory pc = new ProblemCategory();
			pc.setId(Integer.valueOf(object[0].toString()));
			pc.setName((String) object[1]);
			pc.setParentId(Integer.valueOf(object[2].toString()));
			pc.setDescription((String) object[3]);
			pc.setProblemNum(Integer.valueOf(object[4].toString()));
			problemCategoryList.add(pc);
		}
		return problemCategoryList;
	}

	@Override
	public List<ProblemCategory> findAllProblemCategoryByParentId(int parentId) {
		String hql = "select pc.id, pc.name, pc.parentId,pc.description, pc.problemNum "
				+ "from ProblemCategory pc where pc.parentId=:parentId";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", parentId);

		List<Object[]> List = this.relationalQuery(hql, params);

		// 将problemCategoryList 转换为 List<problemCategoryList>
		List<ProblemCategory> problemCategoryList = new ArrayList<ProblemCategory>();
		for (int i = 0; i < List.size(); i++) {
			// 每行记录不在是一个对象 而是一个数组
			Object[] object = List.get(i);
			ProblemCategory pc = new ProblemCategory();
			pc.setId(Integer.valueOf(object[0].toString()));
			pc.setName((String) object[1]);
			pc.setParentId(Integer.valueOf(object[2].toString()));
			pc.setDescription((String) object[3]);
			pc.setProblemNum(Integer.valueOf(object[4].toString()));
			problemCategoryList.add(pc);
		}
		return problemCategoryList;
	}

	@Override
	public ProblemCategory findProblemCategoryById(int id) {
		return this.get(ProblemCategory.class, id);
	}

	@Override
	public int addProblemCategory(ProblemCategory problemCategory) {

		this.save(problemCategory);
		int id = problemCategory.getId();
		return id;

	}

	@Override
	public String editProblemCategoryById(ProblemCategory problemCategory) {
		try {
			String hql = "update ProblemCategory set name=:name, description=:description where id=:id";
			Query query = this.getCurrentSession().createQuery(hql);
			query.setParameter("name", problemCategory.getName());
			query.setParameter("description", problemCategory.getDescription());
			query.setParameter("id", problemCategory.getId());
			query.executeUpdate();
			return null; // 返回信息null,表示添加成功，没有错误信息
		} catch (Exception e) {
			return e.toString();
		}
	}

	@Override
	public String deleteFirstProblemCategoryById(int id) {
		try {
			// 判断当前分类下是否有子分类
			List<ProblemCategory> problemCategoryChildList = this.findAllProblemCategoryByParentId(id);
			if (problemCategoryChildList.size() != 0) {
				return "删除失败！该题目分类下存在子分类！";
			}
			// 判断当前分类下是否存在题目
			String hql_problem = "select p.id from Problems p WHERE p.category like'%," + id + ",%'";
			Query queryObject = getCurrentSession().createQuery(hql_problem);
			List<Integer> pidList = queryObject.list();
			if (pidList.size() != 0) {
				return "删除失败！该题目分类下存在题目！";
			}
			// 判断当前分类下是否存在智能训练题目分类表
			// 判断当前分类下是否存在智能训练题目表
			String hql = "delete from ProblemCategory where id=" + id;
			this.executeHql(hql);
			return null; // 返回信息null,表示添加成功，没有错误信息
		} catch (Exception e) {
			return e.toString();
		}
	}
	
	@Override
	public List<Map<String, Object>> getAllPriCatgory() {
		String sql = "select * from problemCategory where parentId = 0";
		return this.findBySql(sql, null);
	}

	@Override
	public List<Map<String, Object>> getSecCatagoryByPriCatId(int parentId,int examId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select pc.id,pc.name from gdoj.problemcategory pc where pc.parentId =:parentId  ");
		sql.append("and not exists (select * from gdoj.itrainprobcat ip where ip.examId =:examId and pc.id = ip.catId)");
		Map<String,Object> params = new HashMap<String, Object>();
		
		System.out.println(sql.toString());
		params.put("parentId", parentId);
		params.put("examId", examId);
		return this.findBySql(sql.toString(), params);
	}

	@Override
	public ProblemCategory getDescriptionBySecCatId(int secCatId) {
		String hql = "select pc from ProblemCategory pc where id = " + secCatId + " and parentId != 0 ";
		return this.get(hql);
	}
	
}
