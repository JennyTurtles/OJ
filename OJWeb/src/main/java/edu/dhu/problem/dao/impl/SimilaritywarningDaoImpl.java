package edu.dhu.problem.dao.impl;

import edu.dhu.exam.dao.StudentexamdetailDaoI;
import edu.dhu.exam.dao.impl.BaseDaoImpl;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.problem.dao.SimilaritywarningDaoI;
import edu.dhu.problem.dao.StudentTrainDetailDaoI;
import edu.dhu.problem.model.Similaritywarning;
import edu.dhu.problem.model.StudentTrainProbDetail;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("similaritywarningDao")
public class SimilaritywarningDaoImpl extends BaseDaoImpl<Similaritywarning>
		implements SimilaritywarningDaoI {

	private StudentexamdetailDaoI studentexamdetailDao;
	private StudentTrainDetailDaoI studentTrainDetailDao;
	@Autowired
	public void setStudentexamdetailDao(
			StudentexamdetailDaoI studentexamdetailDao) {
		this.studentexamdetailDao = studentexamdetailDao;
	}

	public StudentexamdetailDaoI getStudentexamdetailDao() {
		return studentexamdetailDao;
	}

	public StudentTrainDetailDaoI getStudentTrainDetailDao() {
		return studentTrainDetailDao;
	}
	@Autowired
	public void setStudentTrainDetailDao(StudentTrainDetailDaoI studentTrainDetailDao) {
		this.studentTrainDetailDao = studentTrainDetailDao;
	}

	@Override
	public void saveSimilaritywarning(Similaritywarning similaritywarning) {
		this.save(similaritywarning);
	}
	
	@Override
	public Similaritywarning getSimilaritywarningBySolutionId(int solutionId) {
		String hql = "from Similaritywarning where solutionId = " + solutionId + "  order by warningTime desc ";
		List<Similaritywarning> similaritywarningList = this.find(hql);
		if (similaritywarningList != null && !similaritywarningList.isEmpty())
			return similaritywarningList.get(0);
		return null;
	}

	@Override
	public void deleteSubmitBySolutionId(int solutionId, boolean isCopy) {
		// TODO Auto-generated method stub
		if (isCopy) {
			String hql = "update Similaritywarning set submited =:submited,eversubmit=1 where solutionId =:solutionId";
			Query q = this.getCurrentSession().createQuery(hql);
			q.setParameter("submited", false);
			q.setParameter("solutionId", solutionId);
			q.executeUpdate();
		} else {
			Studentexamdetail detail = studentexamdetailDao.get(
					Studentexamdetail.class, solutionId);
			if (detail != null) {
				Similaritywarning warning = this.get(Similaritywarning.class,
						solutionId);
				if (warning != null && warning.getSubmited() == true) // submitd==1
				{
					String hql = "update Similaritywarning set eversubmit=1 where solutionId =:solutionId";
					Query q = this.getCurrentSession().createQuery(hql);
					q.setParameter("solutionId", solutionId);
					q.executeUpdate();
				}
			}
		}
	}

	@Override
	public void deleteTrainSubmitBySolutionId(int solutionId, boolean isCopy) {
		// TODO Auto-generated method stub
		if (isCopy) {
			String hql = "update Similaritywarning set submited =:submited,eversubmit=1 where solutionId =:solutionId";
			Query q = this.getCurrentSession().createQuery(hql);
			q.setParameter("submited", false);
			q.setParameter("solutionId", solutionId);
			q.executeUpdate();
		} else {
			StudentTrainProbDetail trainProDetail = studentTrainDetailDao.get(StudentTrainProbDetail.class, solutionId);
			if (trainProDetail != null) {
				Similaritywarning warning = this.get(Similaritywarning.class,
						solutionId);
				if (warning != null && warning.getSubmited() == true) // submitd==1
				{
					String hql = "update Similaritywarning set eversubmit=1 where solutionId =:solutionId";
					Query q = this.getCurrentSession().createQuery(hql);
					q.setParameter("solutionId", solutionId);
					q.executeUpdate();
				}
			}
		}
	}

}