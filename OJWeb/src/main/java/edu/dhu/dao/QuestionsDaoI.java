package edu.dhu.dao;

import edu.dhu.model.Questions;
import edu.dhu.pageModel.PMQuestions;

import java.util.List;

public interface QuestionsDaoI extends BaseDaoI<Questions> {

	List<PMQuestions> findAllQuestions();

}
