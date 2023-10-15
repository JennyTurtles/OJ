package edu.dhu.dao.impl;

import edu.dhu.dao.ExamLogDaoI;
import edu.dhu.model.ExamLog;
import org.springframework.stereotype.Repository;

@Repository("examlogDao")
public class ExamLogDaoImpl extends BaseDaoImpl<ExamLog> implements ExamLogDaoI{

}
