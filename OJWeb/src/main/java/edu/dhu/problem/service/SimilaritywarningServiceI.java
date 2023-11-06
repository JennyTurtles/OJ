package edu.dhu.problem.service;


import edu.dhu.problem.model.Similaritywarning;

public interface SimilaritywarningServiceI {
	// 保存一条数据
	public void saveSimilaritywarning(Similaritywarning similaritywarning);

	// 更新一条数据
	public void updateSimilaritywarning(Similaritywarning similaritywarning);

	// 根据solutionID获取Similaritywarning
	public Similaritywarning getSimilaritywarningBySolutionId(int solutionId);

}
