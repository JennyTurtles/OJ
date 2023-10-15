package edu.dhu.dao.impl;

import edu.dhu.dao.QuestionsDaoI;
import edu.dhu.model.Questions;
import edu.dhu.pageModel.PMQuestions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("questionsDao")
public class QuestionsDaoImpl extends BaseDaoImpl<Questions> implements
		QuestionsDaoI {

	@Override
	public List<PMQuestions> findAllQuestions() {
		// TODO Auto-generated method stub
		String hql = "from Questions";
		List<Questions> questions = this.find(hql);
		List<PMQuestions> pmQuestions = new ArrayList<PMQuestions>();
		for (int i = 0; i < questions.size(); i++) {
			Questions question = questions.get(i);
			PMQuestions p = new PMQuestions();
			p.setId(question.getId());
			p.setQuestion(question.getQuestion());
			pmQuestions.add(p);
		}
		return pmQuestions;
	}

}
