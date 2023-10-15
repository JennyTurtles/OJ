package edu.dhu.dao.impl;

import edu.dhu.dao.SubmittedcodeDaoI;
import edu.dhu.model.Submittedcode;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository("submittedcodeDao")
public class SubmittedcodeDaoImpl extends BaseDaoImpl<Submittedcode> implements
		SubmittedcodeDaoI {

	@Override
	public boolean saveSubmittedcode(Submittedcode submittedcode) {
		this.save(submittedcode);
		return true;
	}

	@Override
	public List<Submittedcode> getAllSubmittedcodeByProblemId(int solutionId,
			int problemId) {
		String hql = "from Submittedcode where problemId = " + problemId
				+ " and solutionId != " + solutionId;
		List<Submittedcode> submittedcodeList = this.find(hql);
		return submittedcodeList;
	}

	@Override
	public Submittedcode getSubmittedcodeBySolutionIdAndProblemId(
			int solutionId, int problemId) {
		String hql = "from Submittedcode where solutionId = " + solutionId
				+ " and problemId = " + problemId;
		List<Submittedcode> submittedcodeList = this.find(hql);
		if (submittedcodeList.size() == 0) {
			return null;
		}
		return submittedcodeList.get(0);
	}

	@Override
	public List<Submittedcode> getSubmittedListByProblemIdAndSchoolId(
			int problemId, int schoolId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String time = format.format(y);
      //  System.out.println("一年前："+year);
		
		String hql = "from Submittedcode where problemId  = " + problemId
				+ " and schoolId=" + schoolId +" and time >= '"+ time +"'";
		List<Submittedcode> submittedcodeList = this.find(hql);
		if (submittedcodeList == null || submittedcodeList.size() == 0) {
			return null;
		}
		return submittedcodeList;
	}

	@Override
	public void deleteCodeBySolutionId(int solutionId) {
		// TODO Auto-generated method stub
		String hql = "delete from Submittedcode where solutionId=" + solutionId;
		this.executeHql(hql);
	}

	@Override
	public List<Submittedcode> getSubmittedListByProblemId(int problemId) {
		String hql = "from Submittedcode where problemId  = " + problemId;
		List<Submittedcode> submittedcodeList = this.find(hql);
		if (submittedcodeList == null || submittedcodeList.size() == 0) {
			return null;
		}
		return submittedcodeList;
	}

}
