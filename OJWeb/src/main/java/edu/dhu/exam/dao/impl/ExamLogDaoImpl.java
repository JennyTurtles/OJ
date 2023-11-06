package edu.dhu.exam.dao.impl;

import edu.dhu.exam.dao.ExamLogDaoI;
import edu.dhu.exam.model.ExamLog;
import org.springframework.stereotype.Repository;

@Repository("examlogDao")
public class ExamLogDaoImpl extends BaseDaoImpl<ExamLog> implements ExamLogDaoI {

}
