package edu.dhu.problem.dao.impl;

import edu.dhu.exam.dao.impl.BaseDaoImpl;
import edu.dhu.problem.dao.SolutionDaoI;
import edu.dhu.problem.model.Solution;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("solutionDao")
public class SolutionDaoImpl extends BaseDaoImpl<Solution> implements SolutionDaoI {

	@Override
	public boolean submitCode(Solution solution) {
		this.save(solution);
		return true;
	}

	// 根据userID,examID,problemID查找solutionID最大的记录
	@Override
	public Solution getLastSolutionByUserIdExamIdProblemId(int userId, int examId, int problemId) {
		String hql = "from Solution where userid = " + userId + " and examId = " + examId + " and problemId = "
				+ problemId + " order by id desc";
		List<Solution> solutions = this.find(hql);
		if (solutions.size() != 0) {
			return solutions.get(0);
		}
		return null;
	}

	@Override
	public Solution getLastSolutionByUserIdExamIdProblemIdAndStatus(int userId, int examId, int problemId,
			String status) {
		String hql = "from Solution where userid = " + userId + " and examId = " + examId + " and problemId = "
				+ problemId + " and status = '" + status + "' order by id desc";
		List<Solution> solutions = this.find(hql);
		if (solutions == null || solutions.isEmpty()) {
			return null;
		}
		return solutions.get(0);
	}

	@Override
	public List<Solution> getExamSubmitSolution(boolean isLast, int examId,int teacherId, int nowPage, int pageSize,
			String displaySequence, String studentNo, String name, String banji, float similarity, String searchTime) {
		// TODO Auto-generated method stub
		int start = (nowPage - 1) * pageSize;
		String hql = "";
		if (isLast == true)
//			hql = "select s from Solution s,Studentexamdetail st where st.examId=" + examId +" and s.userid "
//					+ " in(select cs.userId from classstudents cs where cs.classId "
//					+ " in(select ecs.classId from examclasses ecs where ecs.examId="+ examId 
//					+ " and ecs.classId in(select c.id from classes c where c.teacherId=1)) )";
			hql = "select s from Solution s,Studentexamdetail st where st.examId=" + examId;
		else
			hql = "from Solution s where s.examId=" + examId;
		if (displaySequence != null && displaySequence.equals("") == false) {
			String number[] = displaySequence.split("-");
			if (number.length == 2 && number[0].equals("") == false && number[1].equals("") == false) {
				try {
					if (Integer.parseInt(number[0]) > Integer.parseInt(number[1])) {
						String temp = number[0];
						number[0] = number[1];
						number[1] = temp;
					}
					hql = hql + " and s.problemId in (select e.problemId from Examproblems e where e.examId=" + examId
							+ " and e.displaySequence between " + Integer.parseInt(number[0]) + " and "
							+ Integer.parseInt(number[1]) + ")";
				} catch (Exception e) {

				}
			}
		}
		if (studentNo != null && studentNo.equals("") == false && studentNo.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.studentNo like '%" + studentNo + "%')";
		}
		if (name != null && name.equals("") == false && name.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.chineseName like '%" + name + "%')";
		}
		if (banji.equals("0") == false && banji != null) {
			hql = hql + " and s.userid in (select c.userId from Classstudents c where c.classId=" + banji + ")";
		}else if(banji.equals("0") == true && teacherId!=4){
			hql = hql + " and s.userid "
					+ " in (select cs.userId from Classstudents cs where cs.classId "
					+ " in (select ecs.classId from Examclasses ecs where ecs.examId="+ examId 
					+ " and ecs.classId in (select c.id from Classes c where c.teacherId="+teacherId+")) )";
		}else if(banji.equals("0") == true && teacherId==4){
			hql = hql + " and s.userid "
					+ " in (select cs.userId from Classstudents cs where cs.classId "
					+ " in (select ecs.classId from Examclasses ecs where ecs.examId="+ examId 
					+ " and ecs.classId in (select c.id from Classes c)) )";
		}
		if (searchTime.equals(":") == false) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					hql = hql + " and s.submitTime between '" + time[0] + "' and '" + time[1] + "'";
				}
			}
		}
		if (similarity != -2) {
			hql = hql + " and s.similarity>" + similarity;
		}
		if (isLast == true) {
			hql = hql + " and s.id =st.solutionId";
		}
		hql = hql + " order by s.submitTime desc";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setFirstResult(start); // 开始记录
//		q.setMaxResults(pageSize); // 查询多少条
		
		List<Solution> solutionList = q.list();
		
		//使用sql语句
//		String sql = "select * from Solution s,Studentexamdetail st where st.examId="+examId
//				+ " and s.userid  in(select cs.userId from classstudents cs where cs.classId  "
//				+ " in(select ecs.classId from examclasses ecs where ecs.examId="+ examId+ " and ecs.classId "
//				+ " in(select c.id from classes c where c.teacherId=1)) ) and s.id =st.solutionId order by s.submitTime desc";
//		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
//		query.addEntity(Solution.class);
//		List<Solution> solutionList = query.list(); 
		
		return solutionList;
	}
	
	@Override
	public List<Solution> getTrainSubmitSolution(boolean isLast, int examId,int teacherId, int nowPage, int pageSize,
			String studentNo, String name, String banji, float similarity, String searchTime) {
		int start = (nowPage - 1) * pageSize;
		String hql = "";
		if (isLast == true)
			hql = "select s from Solution s,StudentTrainProbDetail st where st.examId=" + examId;
		else
			hql = "from Solution s where s.examId=" + examId;
		if (studentNo != null && studentNo.equals("") == false && studentNo.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.studentNo like '%" + studentNo + "%')";
		}
		if (name != null && name.equals("") == false && name.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.chineseName like '%" + name + "%')";
		}
		if (banji.equals("0") == false && banji != null) {
			hql = hql + " and s.userid in (select c.userId from Classstudents c where c.classId=" + banji + ")";
		}else if(banji.equals("0") == true && teacherId!=4){
			hql = hql + " and s.userid "
					+ " in (select cs.userId from Classstudents cs where cs.classId "
					+ " in (select ecs.classId from Examclasses ecs where ecs.examId="+ examId 
					+ " and ecs.classId in (select c.id from Classes c where c.teacherId="+teacherId+")) )";
		}else if(banji.equals("0") == true && teacherId==4){
			hql = hql + " and s.userid "
					+ " in (select cs.userId from Classstudents cs where cs.classId "
					+ " in (select ecs.classId from Examclasses ecs where ecs.examId="+ examId 
					+ " and ecs.classId in (select c.id from Classes c)) )";
		}
		if (searchTime.equals(":") == false) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					hql = hql + " and s.submitTime between '" + time[0] + "' and '" + time[1] + "'";
				}
			}
		}
		if (similarity != -2) {
			hql = hql + " and s.similarity>" + similarity;
		}
		if (isLast == true) {
			hql = hql + " and s.id =st.solutionId";
		}
		hql = hql + " order by s.submitTime desc";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setFirstResult(start); // 开始记录
//		q.setMaxResults(pageSize); // 查询多少条
		
		List<Solution> solutionList = q.list();
		
		//使用sql语句
//		String sql = "select * from Solution s,Studentexamdetail st where st.examId="+examId
//				+ " and s.userid  in(select cs.userId from classstudents cs where cs.classId  "
//				+ " in(select ecs.classId from examclasses ecs where ecs.examId="+ examId+ " and ecs.classId "
//				+ " in(select c.id from classes c where c.teacherId=1)) ) and s.id =st.solutionId order by s.submitTime desc";
//		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
//		query.addEntity(Solution.class);
//		List<Solution> solutionList = query.list(); 
		
		return solutionList;
	}

	@Override
	public List<Solution> exportExamSearchSolution(boolean isLast, int examId, String displaySequence, String studentNo,
			String name, String banji, float similarity, String searchTime) {
		// TODO Auto-generated method stub
		String hql = "";
		if (isLast == true)
			hql = "select s from Solution s,Studentexamdetail st where st.examId=" + examId;
		else
			hql = "from Solution s where s.examId=" + examId;
		if (displaySequence != null && displaySequence.equals("") == false) {
			String number[] = displaySequence.split("-");
			if (number.length == 2 && number[0].equals("") == false && number[1].equals("") == false) {
				try {
					if (Integer.parseInt(number[0]) > Integer.parseInt(number[1])) {
						String temp = number[0];
						number[0] = number[1];
						number[1] = temp;
					}
					hql = hql + " and s.problemId in (select e.problemId from Examproblems e where e.examId=" + examId
							+ " and e.displaySequence between " + Integer.parseInt(number[0]) + " and "
							+ Integer.parseInt(number[1]) + ")";
				} catch (Exception e) {

				}
			}
		}
		if (studentNo != null && studentNo.equals("") == false && studentNo.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.studentNo like '%" + studentNo + "%')";
		}
		if (name != null && name.equals("") == false && name.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.chineseName like '%" + name + "%')";
		}
		if (banji.equals("0") == false && banji != null) {
			hql = hql + " and s.userid in (select c.userId from Classstudents c where c.classId=" + banji + ")";
		}
		if (searchTime.equals(":") == false) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					hql = hql + " and s.submitTime between '" + time[0] + "' and '" + time[1] + "'";
				}
			}
		}
		if (similarity != -2) {
			hql = hql + " and s.similarity>" + similarity;
		}
		if (isLast == true) {
			hql = hql + " and s.id =st.solutionId";
		}
		hql = hql + " order by s.submitTime desc";
		Query q = this.getCurrentSession().createQuery(hql);
		List<Solution> solutionList = q.list();
		return solutionList;
	}

	@Override
	public List<Solution> exportExamCode(int examId) {
		// TODO Auto-generated method stub
		String hql = "from Solution s where s.examId=" + examId + " order by s.submitTime desc";
		;
		return this.find(hql);
	}
	
	@Override
	public List<Solution> exportExamCode(int examId,int teacherId) {
		// TODO Auto-generated method stub
		String hql = "from Solution s where s.examId=" + examId + " and s.userid in (select c.userId from Classstudents c where c.classId in (select ec.classId from Examclasses ec "
    + "where ec.examId = "+examId+" and ec.classId in ( select id from Classes cs where cs.teacherId = "+teacherId+") ) ) order by s.submitTime desc";
		;
		return this.find(hql);
	}
	
	@Override
	public List<Solution> exportTrainCode(int examId) {
		// TODO Auto-generated method stub
		String hql = "from Solution s where s.examId=" + examId + " order by s.submitTime desc";
		;
		return this.find(hql);
	}
	
	@Override
	public List<Solution> exportTrainCode(int examId,int teacherId) {
		// TODO Auto-generated method stub
		String hql = "from Solution s where s.examId=" + examId + " and s.userid in (select c.userId from Classstudents c where c.classId in (select ec.classId from Examclasses ec "
    + "where ec.examId = "+examId+" and ec.classId in ( select id from Classes cs where cs.teacherId = "+teacherId+") ) ) order by s.submitTime desc";
		;
		return this.find(hql);
	}


	@Override
	public Solution getSourceCode(int id) {
		// TODO Auto-generated method stub
		String hql = "from Solution s where s.id=" + id + "";

		return this.find(hql).get(0);
	}

	@Override
	public List<Object[]> getCopyList(int examId, int teacherId, String displaySequence, String studentNo, String name, String banji,
			float similarity, String searchTime) {
		// TODO Auto-generated method stub
//		String hql = "select s.userid,s.problemId,s.id,s.submitTime,s.similarity,s.similarId,si.warningTime,si.submited,si.eversubmit from Solution s,Similaritywarning si,Examproblems e where s.examId="
//				+ examId + " and s.id=si.solutionId and s.problemId =e.problemId and e.examId=" + examId + ")";
		String hql = "select s.userid,s.problemId,s.id,s.submitTime,s.similarity,s.similarId,si.warningTime,si.submited,si.eversubmit from Solution s,Similaritywarning si,Examproblems e where s.examId="
				+ examId + " and s.id=si.solutionId and s.problemId =e.problemId and e.examId=" + examId;
		if (displaySequence != null && displaySequence.equals("") == false) {
			String number[] = displaySequence.split("-");
			if (number.length == 2 && number[0].equals("") == false && number[1].equals("") == false) {
				try {
					if (Integer.parseInt(number[0]) > Integer.parseInt(number[1])) {
						String temp = number[0];
						number[0] = number[1];
						number[1] = temp;
					}
					hql = hql + " and s.problemId in (select e.problemId from Examproblems e where e.examId=" + examId
							+ " and e.displaySequence between " + Integer.parseInt(number[0]) + " and "
							+ Integer.parseInt(number[1]) + ")";
				} catch (Exception e) {

				}
			}
		}
		if (studentNo != null && studentNo.equals("") == false && studentNo.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.studentNo like '%" + studentNo + "%')";
		}
		if (name != null && name.equals("") == false && name.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.chineseName like '%" + name + "%')";
		}
		if (banji.equals("0") == false && banji != null) {
			hql = hql + " and s.userid in (select c.userId from Classstudents c where c.classId=" + banji + ")";
		}else if(banji.equals("0") == true && teacherId!=4){
			hql = hql + " and s.userid "
					+ " in (select cs.userId from Classstudents cs where cs.classId "
					+ " in (select ecs.classId from Examclasses ecs where ecs.examId="+ examId 
					+ " and ecs.classId in (select c.id from Classes c where c.teacherId="+teacherId+")) )";
		}else if(banji.equals("0") == true && teacherId==4){
			hql = hql + " and s.userid "
					+ " in (select cs.userId from Classstudents cs where cs.classId "
					+ " in (select ecs.classId from Examclasses ecs where ecs.examId="+ examId 
					+ " and ecs.classId in (select c.id from Classes c)) )";
		}
		if (searchTime.equals(":") == false) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					hql = hql + " and s.submitTime between '" + time[0] + "' and '" + time[1] + "'";
				}
			}
		}
		if (similarity != -2) {
			hql = hql + " and s.similarity>" + similarity;
		}
		hql = hql + " order by si.warningTime desc";
		List<Object[]> solutionList = this.relationalQuery(hql);
		List<Object[]> list = new ArrayList<Object[]>();
		if (solutionList.size() != 0) {
			list.add(solutionList.get(solutionList.size() - 1));
			if (solutionList.size() >= 2) // 消除重复
			{
				for (int i = solutionList.size(); i > 1; i--) {
					Object[] obj1 = solutionList.get(i - 1);
					Object[] obj2 = solutionList.get(i - 2);
					int id, sid, id2, sid2;
					id = ((Integer) obj1[2]).intValue();
					sid = ((Integer) obj1[5]).intValue();
					id2 = ((Integer) obj2[2]).intValue();
					sid2 = ((Integer) obj2[5]).intValue();
					if (id != id2 || sid != sid2) {
						list.add(obj2);
					}
				}
				return list;
			} else {
				return solutionList;
			}
		} else
			return solutionList;
	}
	
	@Override
	public List<Object[]> getTrainCopyList(int examId, int teacherId, String studentNo, String name, String banji,
			float similarity, String searchTime) {
		// TODO Auto-generated method stub
//		String hql = "select s.userid,s.problemId,s.id,s.submitTime,s.similarity,s.similarId,si.warningTime,si.submited,si.eversubmit from Solution s,Similaritywarning si,Examproblems e where s.examId="
//				+ examId + " and s.id=si.solutionId and s.problemId =e.problemId and e.examId=" + examId + ")";
		String hql = "select s.userid,s.problemId,s.id,s.submitTime,s.similarity,s.similarId,si.warningTime,si.submited,si.eversubmit from Solution s,Similaritywarning si,Itrainproblems e where s.examId="
				+ examId + " and s.id=si.solutionId and s.problemId =e.problemId and e.examId=" + examId;
		if (studentNo != null && studentNo.equals("") == false && studentNo.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.studentNo like '%" + studentNo + "%')";
		}
		if (name != null && name.equals("") == false && name.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.chineseName like '%" + name + "%')";
		}
		if (banji.equals("0") == false && banji != null) {
			hql = hql + " and s.userid in (select c.userId from Classstudents c where c.classId=" + banji + ")";
		}else if(banji.equals("0") == true && teacherId!=4){
			hql = hql + " and s.userid "
					+ " in (select cs.userId from Classstudents cs where cs.classId "
					+ " in (select ecs.classId from Examclasses ecs where ecs.examId="+ examId 
					+ " and ecs.classId in (select c.id from Classes c where c.teacherId="+teacherId+")) )";
		}else if(banji.equals("0") == true && teacherId==4){
			hql = hql + " and s.userid "
					+ " in (select cs.userId from Classstudents cs where cs.classId "
					+ " in (select ecs.classId from Examclasses ecs where ecs.examId="+ examId 
					+ " and ecs.classId in (select c.id from Classes c)) )";
		}
		if (searchTime.equals(":") == false) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					hql = hql + " and s.submitTime between '" + time[0] + "' and '" + time[1] + "'";
				}
			}
		}
		if (similarity != -2) {
			hql = hql + " and s.similarity>" + similarity;
		}
		hql = hql + " order by si.warningTime desc";
		List<Object[]> solutionList = this.relationalQuery(hql);
		List<Object[]> list = new ArrayList<Object[]>();
		if (solutionList.size() != 0) {
			list.add(solutionList.get(solutionList.size() - 1));
			if (solutionList.size() >= 2) // 消除重复
			{
				for (int i = solutionList.size(); i > 1; i--) {
					Object[] obj1 = solutionList.get(i - 1);
					Object[] obj2 = solutionList.get(i - 2);
					int id, sid, id2, sid2;
					id = ((Integer) obj1[2]).intValue();
					sid = ((Integer) obj1[5]).intValue();
					id2 = ((Integer) obj2[2]).intValue();
					sid2 = ((Integer) obj2[5]).intValue();
					if (id != id2 || sid != sid2) {
						list.add(obj2);
					}
				}
				return list;
			} else {
				return solutionList;
			}
		} else
			return solutionList;
	}

	@Override
	public List<Solution> getSimilarityObject(int examId) {
		// TODO Auto-generated method stub
		String hql = "select s2 from Solution s2,Solution s where s2.id =s.similarId and s.examId=" + examId
				+ " group by s2.id";
		return this.find(hql);

	}

	/*@Override
	public long getExamSolutionCount(boolean isLast, int examId, int teacherId,String displaySequence, String studentNo, String name,
			String banji, float similarity, String searchTime) {
		// TODO Auto-generated method stub
		String hql = "";
		if (isLast == true)
			hql = "select count(s) from Solution s,Studentexamdetail st where st.examId=" + examId;
		else
			hql = "select count(*) from Solution s where s.examId=" + examId;
		if (displaySequence != null && displaySequence.equals("") == false) {
			String number[] = displaySequence.split("-");
			if (number.length == 2 && number[0].equals("") == false && number[1].equals("") == false) {
				try {
					if (Integer.parseInt(number[0]) > Integer.parseInt(number[1])) {
						String temp = number[0];
						number[0] = number[1];
						number[1] = temp;
					}
					hql = hql + " and s.problemId in (select e.problemId from Examproblems e where e.examId=" + examId
							+ " and e.displaySequence between " + Integer.parseInt(number[0]) + " and "
							+ Integer.parseInt(number[1]) + ")";
				} catch (Exception e) {

				}
			}
		}
		if (studentNo != null && studentNo.equals("") == false && studentNo.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.studentNo like '%" + studentNo + "%')";
		}
		if (name != null && name.equals("") == false && name.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.chineseName like '%" + name + "%')";
		}
		if (banji.equals("0") == false && banji != null) { //如果查询某个班级的提交情况
			hql = hql + " and s.userid in (select c.userId from Classstudents c where c.classId=" + banji + ")";
		}else if(banji.equals("0") == true && teacherId!=4){ //如果查询全部班级的提交情况，并且当前登录者不是管理员
			hql = hql + " and s.userid "
					+ " in (select cs.userId from Classstudents cs where cs.classId "
					+ " in (select ecs.classId from Examclasses ecs where ecs.examId="+ examId 
					+ " and ecs.classId in (select c.id from Classes c where c.teacherId="+teacherId+")) )";
		}else if(banji.equals("0") == true && teacherId==4){  //如果查询全部班级的提交情况，并且当前登录者是管理员
			hql = hql + " and s.userid "
					+ " in (select cs.userId from Classstudents cs where cs.classId "
					+ " in (select ecs.classId from Examclasses ecs where ecs.examId="+ examId 
					+ " and ecs.classId in (select c.id from Classes c)) )";
		}
		if (searchTime.equals(":") == false) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					hql = hql + " and s.submitTime between '" + time[0] + "' and '" + time[1] + "'";
				}
			}
		}
		if (similarity != -2) {
			hql = hql + " and s.similarity>" + similarity;
		}
		if (isLast == true) {
			hql = hql + " and s.id =st.solutionId";
		}
		long count = this.count(hql).longValue();
		return count;
	}*/
	@Override
	public long getExamSolutionCount(boolean isLast, int examId, int teacherId,String displaySequence, String studentNo, String name,
			String banji, float similarity, String searchTime) {
		StringBuilder sql = new StringBuilder();
		Map<String,Object> params = new HashMap<String, Object>();
		
		sql.append("select count(*) num  ");
		sql.append("from gdoj.solution s ");
		sql.append("straight_join gdoj.users u on s.userid = u.id ");
		sql.append("straight_join gdoj.problems p on s.problemId = p.id ");
		sql.append("straight_join gdoj.examproblems ep on s.problemId = ep.problemId and s.examId = ep.examId ");
		if(isLast)
			sql.append("straight_join gdoj.studentexamdetail sted on s.id = sted.solutionId ");
		else
			sql.append("left join gdoj.studentexamdetail sted on s.id = sted.solutionId ");
		/*sql.append("where s.examId =:examId and ");
		sql.append("EXISTS( SELECT * FROM  gdoj.classstudents ct LEFT JOIN gdoj.examclasses ec ON ec.classId = ct.classId ");
		sql.append("LEFT JOIN gdoj.classes c ON c.id = ec.classId WHERE s.userId = ct.userId AND examId =:examId ");*/
		
		sql.append("straight_join gdoj.classstudents ct on s.userId = ct.userId ");
		sql.append("straight_join  gdoj.examclasses ec ON ec.classId = ct.classId and s.examId = ec.examId ");
		sql.append("straight_join gdoj.classes c ON c.id = ec.classId ");
		
		sql.append("where s.examId =:examId  ");
		//选定班级
		if(!"0".equals(banji) && banji != null){
			sql.append("and ct.classId =:banji ");
			params.put("banji", Integer.parseInt(banji));
		}
		//若未选定班级且不为管理员登录
		else if("0".equals(banji) && teacherId != 4){
			sql.append("and c.teacherId =:teacherId ");
			params.put("teacherId", teacherId);
		}
		/*sql.append(") ");*/
		
		//题号查询条件不为空
		if(displaySequence != null && !displaySequence.equals("")){
			String number[] = displaySequence.split("-");
			if (number.length == 2 && !number[0].equals("") && !number[1].equals("")) {
				try {
					if (Integer.parseInt(number[0]) > Integer.parseInt(number[1])) {
						String temp = number[0];
						number[0] = number[1];
						number[1] = temp;
					}
					sql.append("and ep.displaySequence between "+ Integer.parseInt(number[0]) + " and "+ Integer.parseInt(number[1])+ " ");
				} catch (Exception e) {

				}
			}
		}
		
		//学号查询条件不为空
		if(studentNo != null && !studentNo.equals("") && !studentNo.equals("null")){
			/*sql.append("and u.studentNo =:studentNo " );
			params.put("studentNo", studentNo);*/
			sql.append("and u.studentNo like '%" + studentNo + "%'  ");
		}
		
		//姓名查询条件不为空
		if(name != null && !name.equals("") && !name.equals("null") ){
			sql.append("and u.chineseName like '%" + name + "%'  ");
		}
		
		//提交时间查询条件不为空
		if (!searchTime.equals(":")) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					sql.append("and s.submitTime between '" + time[0] + "' and '" + time[1] + "' ");
				}
			}
		}
		
		//相似度查询条件不为空
		if (similarity != -2) {
			sql.append("and s.similarity >:similarity  ");
			params.put("similarity", similarity);
		}
			
		sql.append("order by s.submitTime desc ");
		
		params.put("examId", examId);
		
		SQLQuery sqry = this.getCurrentSession().createSQLQuery(sql.toString());
		if(null != params && !params.isEmpty())
			sqry.setProperties(params);
		return Long.parseLong(sqry.uniqueResult().toString());
	}
	
	
	/*@Override
	public long getTrainSolutionCount(boolean isLast, int examId, int teacherId, String studentNo, String name,
			String banji, float similarity, String searchTime) {
		// TODO Auto-generated method stub
		String hql = "";
		if (isLast == true)
			hql = "select count(s) from Solution s,StudentTrainProbDetail st where st.examId=" + examId;
		else
			hql = "select count(*) from Solution s where s.examId=" + examId;
//		if (displaySequence != null && displaySequence.equals("") == false) {
//			String number[] = displaySequence.split("-");
//			if (number.length == 2 && number[0].equals("") == false && number[1].equals("") == false) {
//				try {
//					if (Integer.parseInt(number[0]) > Integer.parseInt(number[1])) {
//						String temp = number[0];
//						number[0] = number[1];
//						number[1] = temp;
//					}
//					hql = hql + " and s.problemId in (select e.problemId from Examproblems e where e.examId=" + examId
//							+ " and e.displaySequence between " + Integer.parseInt(number[0]) + " and "
//							+ Integer.parseInt(number[1]) + ")";
//				} catch (Exception e) {
//
//				}
//			}
//		}
		if (studentNo != null && studentNo.equals("") == false && studentNo.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.studentNo like '%" + studentNo + "%')";
		}
		if (name != null && name.equals("") == false && name.equals("null") == false) {
			hql = hql + " and s.userid in (select u.id from Users u where u.chineseName like '%" + name + "%')";
		}
		if (banji.equals("0") == false && banji != null) { //如果查询某个班级的提交情况
			hql = hql + " and s.userid in (select c.userId from Classstudents c where c.classId=" + banji + ")";
		}else if(banji.equals("0") == true && teacherId!=4){ //如果查询全部班级的提交情况，并且当前登录者不是管理员
			hql = hql + " and s.userid "
					+ " in (select cs.userId from Classstudents cs where cs.classId "
					+ " in (select ecs.classId from Examclasses ecs where ecs.examId="+ examId 
					+ " and ecs.classId in (select c.id from Classes c where c.teacherId="+teacherId+")) )";
		}else if(banji.equals("0") == true && teacherId==4){  //如果查询全部班级的提交情况，并且当前登录者是管理员
			hql = hql + " and s.userid "
					+ " in (select cs.userId from Classstudents cs where cs.classId "
					+ " in (select ecs.classId from Examclasses ecs where ecs.examId="+ examId 
					+ " and ecs.classId in (select c.id from Classes c)) )";
		}
		if (searchTime.equals(":") == false) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					hql = hql + " and s.submitTime between '" + time[0] + "' and '" + time[1] + "'";
				}
			}
		}
		if (similarity != -2) {
			hql = hql + " and s.similarity>" + similarity;
		}
		if (isLast == true) {
			hql = hql + " and s.id =st.solutionId";
		}
		long count = this.count(hql).longValue();
		return count;
	}*/
	
	@Override
	public long getTrainSolutionCount(boolean isLast, int examId, int teacherId, String role, String studentNo, String name,
			String banji, float similarity, String searchTime) {
		StringBuilder sql = new StringBuilder();
		Map<String,Object> params = new HashMap<String, Object>();
		
		sql.append("select count(*) num  ");
		sql.append("from gdoj.solution s ");
		sql.append("straight_join  gdoj.users u on s.userid = u.id ");
		sql.append("straight_join  gdoj.problems p on s.problemId = p.id ");
		sql.append("straight_join  gdoj.itrainproblems ip on ip.problemId = p.id and ip.examId = s.examId ");
		if(isLast)
			sql.append("straight_join gdoj.studenttrainprobdetail stpd on s.id = stpd.solutionId ");
		else
			sql.append("left join gdoj.studenttrainprobdetail stpd on s.id = stpd.solutionId ");
		
		sql.append("straight_join gdoj.classstudents ct on s.userId = ct.userId ");
		sql.append("straight_join  gdoj.examclasses ec ON ec.classId = ct.classId and s.examId = ec.examId ");
		sql.append("straight_join gdoj.classes c ON c.id = ec.classId ");
		
		sql.append("where s.examId =:examId  ");
		//选定班级
		if(!"0".equals(banji) && banji != null){
			sql.append("and ct.classId =:banji  ");
			params.put("banji", Integer.parseInt(banji));
		}		
		//若未选定班级且不为管理员登录
		else if("0".equals(banji) && !role.equals("admin")){
			//如果是教师登录
			if(role.equals("teacher")){
				sql.append("and c.teacherId =:teacherId  ");
				params.put("teacherId", teacherId);
			}else if(role.equals("assistant")){  //如果是助教登录
				sql.append("and c.id =(select assi.classId from assistantclass assi where assi.assistantId =:teacherId) ");
				params.put("teacherId", teacherId);
			}
		}	
		
		//学号查询条件不为空
		if(studentNo != null && !studentNo.equals("") && !studentNo.equals("null")){
			/*sql.append("and u.studentNo =:studentNo " );
			params.put("studentNo", studentNo);*/
			sql.append("and u.studentNo like '%" + studentNo + "%'  ");
		}		
				
		//姓名查询条件不为空
		if(name != null && !name.equals("") && !name.equals("null") ){
			sql.append("and u.chineseName like '%" + name + "%'  ");
		}				

		//提交时间查询条件不为空
		if (!searchTime.equals(":")) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					sql.append("and s.submitTime between '" + time[0] + "' and '" + time[1] + "' ");
				}
			}
		}
		
		//相似度查询条件不为空
		if (similarity != -2) {
			sql.append("and s.similarity >:similarity  ");
			params.put("similarity", similarity);
		}
		
		sql.append("order by s.submitTime desc ");
		
		params.put("examId", examId);		
		SQLQuery sqry = this.getCurrentSession().createSQLQuery(sql.toString());
		if(null != params && !params.isEmpty())
			sqry.setProperties(params);
		return Long.parseLong(sqry.uniqueResult().toString());
	}

	@Override
	public List<Solution> exportExamLastCode(int examId) {
		// TODO Auto-generated method stub
		String hql = "select s from Solution s,Studentexamdetail st where st.examId=" + examId
				+ " and s.id=st.solutionId order by s.submitTime desc";
		;
		return this.find(hql);
	}
	
	@Override
	public List<Solution> exportExamLastCode(int examId,int teacherId) {
		// TODO Auto-generated method stub
		String hql = "select s from Solution s,Studentexamdetail st where st.examId=" + examId
				+ " and s.id=st.solutionId and s.userid in (select c.userId from Classstudents c where c.classId in (select ec.classId from Examclasses ec "
				+ "where ec.examId = "+examId+" and ec.classId in ( select id from Classes cs where cs.teacherId = "+teacherId+") ) ) order by s.submitTime desc";
		;
		return this.find(hql);
	}
	
	@Override
	public List<Solution> exportTrainLastCode(int examId) {
		// TODO Auto-generated method stub
		String hql = "select s from Solution s,StudentTrainProbDetail st where st.examId=" + examId
				+ " and s.id=st.solutionId order by s.submitTime desc";
		;
		return this.find(hql);
	}
	
	@Override
	public List<Solution> exportTrainLastCode(int examId,int teacherId) {
		// TODO Auto-generated method stub
		String hql = "select s from Solution s,StudentTrainProbDetail st where st.examId=" + examId
				+ " and s.id=st.solutionId and s.userid in (select c.userId from Classstudents c where c.classId in (select ec.classId from Examclasses ec "
				+ "where ec.examId = "+examId+" and ec.classId in ( select id from Classes cs where cs.teacherId = "+teacherId+") ) ) order by s.submitTime desc";
		;
		return this.find(hql);
	}

	@Override
	public List<Solution> exportClassExamCode(int examId, int classId) {
		// TODO Auto-generated method stub
		String hql = "from Solution s where s.examId=" + examId
				+ " and s.userid in (select c.userId from Classstudents c where c.classId=" + classId
				+ " ) order by s.submitTime desc";
		;
		return this.find(hql);
	}
	
	@Override
	public List<Solution> exportClassTrainCode(int examId, int classId) {
		// TODO Auto-generated method stub
		String hql = "from Solution s where s.examId=" + examId
				+ " and s.userid in (select c.userId from Classstudents c where c.classId=" + classId
				+ " ) order by s.submitTime desc";
		;
		return this.find(hql);
	}

	@Override
	public List<Solution> exportClassExamLastCode(int examId, int classId) {
		// TODO Auto-generated method stub
		String hql = "select s from Solution s,Studentexamdetail st where st.examId=" + examId
				+ " and s.id =st.solutionId and s.userid in (select c.userId from Classstudents c where c.classId="
				+ classId + " ) order by s.submitTime desc";

		return this.find(hql);
	}
	
	@Override
	public List<Solution> exportClassTrainLastCode(int examId, int classId) {
		// TODO Auto-generated method stub
		String hql = "select s from Solution s,StudentTrainProbDetail st where st.examId=" + examId
				+ " and s.id =st.solutionId and s.userid in (select c.userId from Classstudents c where c.classId="
				+ classId + " ) order by s.submitTime desc";

		return this.find(hql);
	}

	@Override
	public List<Object[]> getExamLastSolutionStatus(int examId) { // 获取考试试题提交最后的状态
		// TODO Auto-generated method stub
		String hql = "select s.id,s.status from Solution s,Studentexamdetail st where s.id=st.solutionId and st.examId="
				+ examId + " group by s.id";

		List<Object[]> solutionStatusList = this.relationalQuery(hql);
		return solutionStatusList;
	}

	@Override
	public void deleteSubmit(int id, int reason, String remark) {
		// TODO Auto-generated method stub
		String hql = "update Solution set status=:status,remark=:remark where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		if (reason == 1) // 抄袭
		{
			q.setParameter("id", id);
			q.setParameter("status", "COPY");
			q.setParameter("remark", remark);
		} else {
			q.setParameter("id", id);
			q.setParameter("status", "REDO");
			q.setParameter("remark", remark);
		}
		q.executeUpdate();
	}

	@Override
	public List<Solution> getSolutionsByNumber(int number) {
		String hql = "select s from Solution s where s.status = 'WAIT'order by id asc";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(number);
		List<Solution> rs = query.list();
		return rs;

	}

	@Override
	public int editStudentScore(Solution solution) {
		// TODO Auto-generated method stub
		String hql = "update Solution set score=:score,remark=:remark where userid=:userid and problemId=:problemId and examId=:examId";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("score", solution.getScore());
		q.setParameter("userid", solution.getUserid());
		q.setParameter("problemId", solution.getProblemId());
		q.setParameter("examId", solution.getExamId());
		int num = q.executeUpdate();
		return num;
	}

	@Override
	public List<Object[]> getExamStudentInfo(int examId, int classId) {
		String sql = null;
		if (classId != 0) {
			sql="select u.id,u.chineseName name,u.studentNo,c.Name className from classstudents cs left join classes c on c.id=cs.classId left join users u on u.id=cs.userId where cs.classId ="+classId;
		}else{
			sql = "select u.id,u.chineseName name,u.studentNo,c.Name className from classstudents cs left join classes c on c.id=cs.classId left join users u on u.id=cs.userId where cs.classId in (select  classId from examclasses where examId ="
					+ examId + ")";
		}
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return q.list();
	}

	@Override
	public List<Map<String, Object>> getExamSubmitSolutionInfo(boolean isLast, int examId, int teacherId, int nowPage,
			int pageSize, String displaySequence, String studentNo, String name, String banji, float similarity,
			String searchTime) {
		StringBuilder sql = new StringBuilder();
		Map<String,Object> params = new HashMap<String, Object>();
		
		sql.append("select s.submitTime, u.studentNo,u.chineseName,u.banji,s.id,s.problemId,s.status,s.score,s.similarity,s.language,   ");
		sql.append("s.correctCaseIds,s.sourceCode,s.similarId,ep.displaySequence,p.title,p.id proId,p.similarityThreshold,sted.solutionId,sted.finished  ");
		sql.append("from gdoj.solution s ");
		sql.append("straight_join gdoj.users u on s.userid = u.id ");
		sql.append("straight_join  gdoj.problems p on s.problemId = p.id ");
		sql.append("straight_join  gdoj.examproblems ep on s.problemId = ep.problemId and s.examId = ep.examId ");
		if(isLast)
			sql.append("straight_join  gdoj.studentexamdetail sted on s.id = sted.solutionId ");
		else
			sql.append("left join gdoj.studentexamdetail sted on s.id = sted.solutionId ");
		
		/*sql.append("where s.examId =:examId and ");
		sql.append("EXISTS( SELECT * FROM  gdoj.classstudents ct LEFT JOIN gdoj.examclasses ec ON ec.classId = ct.classId ");
		sql.append("LEFT JOIN gdoj.classes c ON c.id = ec.classId WHERE s.userId = ct.userId AND examId =:examId ");*/
		
		sql.append("straight_join gdoj.classstudents ct on s.userId = ct.userId ");
		sql.append("straight_join  gdoj.examclasses ec ON ec.classId = ct.classId and s.examId = ec.examId ");
		sql.append("straight_join gdoj.classes c ON c.id = ec.classId ");
		
		sql.append("where s.examId =:examId  ");
		//选定班级
		if(!"0".equals(banji) && banji != null){
			sql.append("and ct.classId =:banji ");
			params.put("banji", Integer.parseInt(banji));
		}
		//若未选定班级且不为管理员登录
		else if("0".equals(banji) && teacherId != 4){
			sql.append("and c.teacherId =:teacherId ");
			params.put("teacherId", teacherId);
		}
		/*sql.append(") ");*/
		
		//题号查询条件不为空
		if(displaySequence != null && !displaySequence.equals("")){
			String number[] = displaySequence.split("-");
			if (number.length == 2 && !number[0].equals("") && !number[1].equals("")) {
				try {
					if (Integer.parseInt(number[0]) > Integer.parseInt(number[1])) {
						String temp = number[0];
						number[0] = number[1];
						number[1] = temp;
					}
					sql.append("and ep.displaySequence between "+ Integer.parseInt(number[0]) + " and "+ Integer.parseInt(number[1])+ " ");
				} catch (Exception e) {

				}
			}
		}
		
		//学号查询条件不为空
		if(studentNo != null && !studentNo.equals("") && !studentNo.equals("null")){
			/*sql.append("and u.studentNo =:studentNo " );
			params.put("studentNo", studentNo);*/
			sql.append("and u.studentNo like '%" + studentNo + "%'  ");
		}
		
		//姓名查询条件不为空
		if(name != null && !name.equals("") && !name.equals("null") ){
			sql.append("and u.chineseName like '%" + name + "%'  ");
		}
		
		//提交时间查询条件不为空
		if (!searchTime.equals(":")) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					sql.append("and s.submitTime between '" + time[0] + "' and '" + time[1] + "' ");
				}
			}
		}
		
		//相似度查询条件不为空
		if (similarity != -2) {
			sql.append("and s.similarity >:similarity  ");
			params.put("similarity", similarity);
		}
			
		sql.append("order by s.submitTime desc ");
		
		sql.append("limit " + (nowPage - 1) * pageSize + ","+pageSize);
		
		params.put("examId", examId);
		
		return this.findBySql(sql.toString(), params);
	}

	@Override
	public List<Map<String, Object>> getCopyListInfo(int examId, int teacherId, String displaySequence,
			String studentNo, String name, String banji, float similarity, String searchTime) {
		StringBuilder sql = new StringBuilder();
		Map<String,Object> params = new HashMap<String, Object>();
		
		sql.append("select  u1.studentNo,u1.chineseName,u1.banji,s.id,si.warningTime submitTime,s.problemId,e.displaySequence, ");
		sql.append("p.title,s.similarId,u2.studentNo studentNo2,u2.chineseName chineseName2,s.similarity,p.similarityThreshold,si.submited,si.eversubmit ");
		sql.append("from  gdoj.Solution s ");
		sql.append("straight_join gdoj.similaritywarning si on si.solutionId = s.id ");
		sql.append("straight_join  gdoj.solution s1 on s1.id = s.similarId ");
		sql.append("straight_join  gdoj.users u1 on s.userId = u1.id ");
		sql.append("straight_join  gdoj.users u2 on s1.userId = u2.id ");
		sql.append("straight_join gdoj.Examproblems e on s.problemId = e.problemId and s.examId = e.examId ");
		sql.append("straight_join gdoj.problems p on p.id = s.problemId ");
		sql.append("straight_join gdoj.classstudents ct on s.userId = ct.userId ");
		sql.append("straight_join  gdoj.examclasses ec ON ec.classId = ct.classId and s.examId = ec.examId ");
		sql.append("straight_join gdoj.classes c ON c.id = ec.classId ");		
		
		sql.append("where s.examId =:examId  ");
		//选定班级
		if(!"0".equals(banji) && banji != null){
			sql.append("and ct.classId =:banji  ");
			params.put("banji", Integer.parseInt(banji));
		}
		//若未选定班级且不为管理员登录
		else if("0".equals(banji) && teacherId != 4){
			sql.append("and c.teacherId =:teacherId  ");
			params.put("teacherId", teacherId);
		}
		
		//题号查询条件不为空
		if(displaySequence != null && !displaySequence.equals("")){
			String number[] = displaySequence.split("-");
			if (number.length == 2 && !number[0].equals("") && !number[1].equals("")) {
				try {
					if (Integer.parseInt(number[0]) > Integer.parseInt(number[1])) {
						String temp = number[0];
						number[0] = number[1];
						number[1] = temp;
					}
					sql.append("and e.displaySequence between "+ Integer.parseInt(number[0]) + " and "+ Integer.parseInt(number[1])+ " ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//学号查询条件不为空
		if(studentNo != null && !studentNo.equals("") && !studentNo.equals("null")){
			/*sql.append("and u1.studentNo =:studentNo " );
			params.put("studentNo", studentNo);*/
			sql.append("and u1.studentNo like '%" + studentNo + "%'  ");
		}
		
		//姓名查询条件不为空
		if(name != null && !name.equals("") && !name.equals("null") ){
			sql.append("and u1.chineseName like '%" + name + "%'  ");
		}

		//提交时间查询条件不为空
		if (!searchTime.equals(":")) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					sql.append("and s.submitTime between '" + time[0] + "' and '" + time[1] + "' ");
				}
			}
		}
		
		//相似度查询条件不为空
		if (similarity != -2) {
			sql.append("and s.similarity >:similarity  ");
			params.put("similarity", similarity);
		}
		
		sql.append("order by si.warningTime desc ");
		
		params.put("examId", examId);
		
		return this.findBySql(sql.toString(), params);
	}

	@Override
	public List<Map<String, Object>> getTrainSubmitSolutionInfo(boolean isLast, int examId, int teacherId, String role,int nowPage,
			int pageSize, String studentNo, String name, String banji, float similarity, String searchTime) {
		StringBuilder sql = new StringBuilder();
		Map<String,Object> params = new HashMap<String, Object>();
		
		sql.append("select  u.studentNo,u.chineseName,u.banji,s.id,s.submitTime,s.problemId,ip.catId displaySequence, ");
		sql.append("p.title,p.similarityThreshold,s.status,s.score,s.similarity,s.language,s.correctCaseIds,s.sourceCode,s.similarId,stpd.finished ");
		sql.append("from gdoj.solution s ");
		sql.append("straight_join  gdoj.users u on s.userid = u.id ");
		sql.append("straight_join  gdoj.problems p on s.problemId = p.id ");
		sql.append("straight_join  gdoj.itrainproblems ip on ip.problemId = p.id and ip.examId = s.examId ");
		if(isLast)
			sql.append("straight_join gdoj.studenttrainprobdetail stpd on s.id = stpd.solutionId ");
		else
			sql.append("left join gdoj.studenttrainprobdetail stpd on s.id = stpd.solutionId ");
		
		sql.append("straight_join gdoj.classstudents ct on s.userId = ct.userId ");
		sql.append("straight_join  gdoj.examclasses ec ON ec.classId = ct.classId and s.examId = ec.examId ");
		sql.append("straight_join gdoj.classes c ON c.id = ec.classId ");
		
		sql.append("where s.examId =:examId  ");
		//选定班级
		if(!"0".equals(banji) && banji != null){
			sql.append("and ct.classId =:banji  ");
			params.put("banji", Integer.parseInt(banji));
		}		
		//若未选定班级且不为管理员登录
		else if("0".equals(banji) &&!role.equals("admin")){
			//如果是教师登录
			if(role.equals("teacher")){
				sql.append("and c.teacherId =:teacherId  ");
				params.put("teacherId", teacherId);
			}else if(role.equals("assistant")){  //如果是助教登录
				sql.append("and c.id =(select assi.classId from assistantclass assi where assi.assistantId=:teacherId )  ");
				params.put("teacherId", teacherId);
			}
		}	
		
		//学号查询条件不为空
		if(studentNo != null && !studentNo.equals("") && !studentNo.equals("null")){
			/*sql.append("and u.studentNo =:studentNo " );
			params.put("studentNo", studentNo);*/
			sql.append("and u.studentNo like '%" + studentNo + "%'  ");
		}		
				
		//姓名查询条件不为空
		if(name != null && !name.equals("") && !name.equals("null") ){
			sql.append("and u.chineseName like '%" + name + "%'  ");
		}				

		//提交时间查询条件不为空
		if (!searchTime.equals(":")) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					sql.append("and s.submitTime between '" + time[0] + "' and '" + time[1] + "' ");
				}
			}
		}
		
		//相似度查询条件不为空
		if (similarity != -2) {
			sql.append("and s.similarity >:similarity  ");
			params.put("similarity", similarity);
		}
		
		sql.append("order by s.submitTime desc ");
		
		sql.append("limit " + (nowPage - 1) * pageSize + ","+pageSize);
		
		params.put("examId", examId);
		
		return this.findBySql(sql.toString(), params);
	}

	@Override
	public List<Map<String, Object>> getTrainCopyListInfo(int examId, int teacherId, String role, String studentNo, String name,
			String banji, float similarity, String searchTime) {
		StringBuilder sql = new StringBuilder();
		Map<String,Object> params = new HashMap<String, Object>();
		
		sql.append("select  u1.studentNo,u1.chineseName,u1.banji,s.id,si.warningTime submitTime,s.problemId, ");
		sql.append("p.title,s.similarId,u2.studentNo studentNo2,u2.chineseName chineseName2,s.similarity,p.similarityThreshold,si.submited,si.eversubmit ");
		sql.append("from  gdoj.Solution s ");
		sql.append("straight_join gdoj.similaritywarning si on si.solutionId = s.id ");
		sql.append("straight_join  gdoj.solution s1 on s1.id = s.similarId ");
		sql.append("straight_join  gdoj.users u1 on s.userId = u1.id ");
		sql.append("straight_join  gdoj.users u2 on s1.userId = u2.id ");
		sql.append("straight_join gdoj.problems p on p.id = s.problemId ");
		sql.append("straight_join gdoj.classstudents ct on s.userId = ct.userId ");
		sql.append("straight_join  gdoj.examclasses ec ON ec.classId = ct.classId and s.examId = ec.examId ");
		sql.append("straight_join gdoj.classes c ON c.id = ec.classId ");		
		
		sql.append("where s.examId =:examId  ");
		//选定班级
		if(!"0".equals(banji) && banji != null){
			sql.append("and ct.classId =:banji  ");
			params.put("banji", Integer.parseInt(banji));
		}
		//若未选定班级且不为管理员登录
		 else if("0".equals(banji) && !role.equals("admin")){
		   //如果是教师登录
		   if(role.equals("teacher")){
		    sql.append("and c.teacherId =:teacherId  ");
		    params.put("teacherId", teacherId);
		   }else if(role.equals("assistant")){  //如果是助教登录
			   sql.append("and c.id =(select assi.classId from assistantclass assi where assi.assistantId=:teacherId )  ");
		    params.put("teacherId", teacherId);
		   }
		  } 
		
		//学号查询条件不为空
		if(studentNo != null && !studentNo.equals("") && !studentNo.equals("null")){
			/*sql.append("and u1.studentNo =:studentNo " );
			params.put("studentNo", studentNo);*/
			sql.append("and u1.studentNo like '%" + studentNo + "%'  ");
		}
		
		//姓名查询条件不为空
		if(name != null && !name.equals("") && !name.equals("null") ){
			sql.append("and u1.chineseName like '%" + name + "%'  ");
		}

		//提交时间查询条件不为空
		if (!searchTime.equals(":")) {
			String time[] = searchTime.split(":");
			if (time.length == 2) {
				if (time[0].equals("") == false && time[1].equals("") == false) {
					if (time[0].compareTo(time[1]) > 0) {
						String temp = time[0];
						time[0] = time[1];
						time[1] = temp;
					}
					time[1] += " 23:59:59";
					sql.append("and s.submitTime between '" + time[0] + "' and '" + time[1] + "' ");
				}
			}
		}
		
		//相似度查询条件不为空
		if (similarity != -2) {
			sql.append("and s.similarity >:similarity  ");
			params.put("similarity", similarity);
		}
		
		sql.append("order by si.warningTime desc ");
		
		params.put("examId", examId);
		
		return this.findBySql(sql.toString(), params);
	}
	
	
	
}
