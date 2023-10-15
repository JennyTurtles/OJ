package edu.dhu.dao;

import edu.dhu.model.Similaritywarning;

public interface SimilaritywarningDaoI extends BaseDaoI<Similaritywarning> {

	// 保存一条数据
	public void saveSimilaritywarning(Similaritywarning similaritywarning);

	// 根据solutionID获取Similaritywarning
	public Similaritywarning getSimilaritywarningBySolutionId(int solutionId);

	public void deleteSubmitBySolutionId(int solutionId, boolean isCopy); // 撤销similaritywarning表里面的submit

	public void deleteTrainSubmitBySolutionId(int solutionId, boolean isCopy); // 撤销similaritywarning表里面的submit
}
