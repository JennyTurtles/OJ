package edu.dhu.problem.service.impl;

import edu.dhu.problem.dao.SimilaritywarningDaoI;
import edu.dhu.problem.model.Similaritywarning;
import edu.dhu.problem.service.SimilaritywarningServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("similaritywarningService")
@Transactional
public class SimilaritywarningServiceImpl implements SimilaritywarningServiceI {

	private SimilaritywarningDaoI similaritywarningDao;

	public SimilaritywarningDaoI getSimilaritywarningDao() {
		return similaritywarningDao;
	}

	@Autowired
	public void setSimilaritywarningDao(
			SimilaritywarningDaoI similaritywarningDao) {
		this.similaritywarningDao = similaritywarningDao;
	}

	@Override
	public void saveSimilaritywarning(Similaritywarning similaritywarning) {
		similaritywarningDao.saveSimilaritywarning(similaritywarning);
	}

	@Override
	public void updateSimilaritywarning(Similaritywarning similaritywarning) {
		similaritywarningDao.update(similaritywarning);
	}

	@Override
	public Similaritywarning getSimilaritywarningBySolutionId(int solutionId) {
		return similaritywarningDao
				.getSimilaritywarningBySolutionId(solutionId);
	}

}
