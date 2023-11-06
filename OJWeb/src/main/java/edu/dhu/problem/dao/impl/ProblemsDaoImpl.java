package edu.dhu.problem.dao.impl;

import edu.dhu.exam.dao.AdminusersDaoI;
import edu.dhu.exam.dao.impl.BaseDaoImpl;
import edu.dhu.problem.dao.ChapterDaoI;
import edu.dhu.problem.dao.ProblemCategoryDaoI;
import edu.dhu.problem.dao.ProblemsDaoI;
import edu.dhu.problem.model.PMProblems;
import edu.dhu.problem.model.ProblemCategory;
import edu.dhu.problem.model.Problems;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("problemsDao")
public class ProblemsDaoImpl extends BaseDaoImpl<Problems> implements ProblemsDaoI {
	private ChapterDaoI chapterDao;
	private AdminusersDaoI adminusersDao;
	private ProblemCategoryDaoI problemCategoryDao;

	public ProblemCategoryDaoI getProblemCategoryDao() {
		return problemCategoryDao;
	}

	@Autowired
	public void setProblemCategoryDao(ProblemCategoryDaoI problemCategoryDao) {
		this.problemCategoryDao = problemCategoryDao;
	}

	@Override
	public List<PMProblems> findAllProblemsByExamId(int examId) {
		String hql = "select p.id, ep.score,ep.displaySequence, p.title, p.difficulty, p.updateTime, ep.bestBefore,ep.scoreCoef,ep.deadline  "
				+ "from Examproblems ep, Problems p " + "where ep.problemId = p.id " + "and ep.examId = :examId "
				+ "order by ep.displaySequence asc";

		// 传递exam id参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("examId", examId);

		List<Object[]> problemList = this.relationalQuery(hql, params);

		// 将要返回
		List<PMProblems> pMProblems = new ArrayList<PMProblems>();

		// 将problemList 转换为 List<PMProblems>
		for (int i = 0; i < problemList.size(); i++) {
			// 每行记录不在是一个对象 而是一个数组
			Object[] object = problemList.get(i);
			PMProblems p = new PMProblems();
			p.setId(Integer.valueOf(object[0].toString()));
			p.setProblemScore(Float.valueOf(object[1].toString()));
			p.setDisplaySequence(Integer.valueOf(object[2].toString()));
			p.setTitle((String) object[3]);
			p.setDifficulty((String) object[4]);
			p.setUpdateTime((Date) object[5]);
			p.setBestBefore((Date)object[6]);
			p.setScoreCoef(Float.valueOf(object[7].toString()));
			p.setDeadline((Date)object[8]);
			pMProblems.add(p);
		}
		return pMProblems;
	}

	@Override
	public Problems findProblemById(int problemId) {
		return this.get(Problems.class, problemId);
	}

	// 添加查询章节的服务
	@Autowired
	public void setChapterDao(ChapterDaoI chapterDao) {
		this.chapterDao = chapterDao;
	}

	public ChapterDaoI getChapterDao() {
		return chapterDao;
	}

	@Autowired
	public void setAdminusersDao(AdminusersDaoI adminusersDao) {
		this.adminusersDao = adminusersDao;
	}

	public AdminusersDaoI getAdminusersDao() {
		return adminusersDao;
	}

	// 查找所有的问题
	@Override
	public List<PMProblems> findProblemsByCondition(String keywords, String courseCode, String chapterCode,
			String source, String difficulty, int teacherId, String sortContent, String sortType) {
		// TODO Auto-generated method stub
		String hql = "from Problems p where (p.title like :keywords or p.description like :keywords)";
		/*
		 * if(!courseCode.equals("") && chapterCode.equals("") ){
		 * hql=hql+" and chapterCode="+chapterCode; }
		 */
		if (!courseCode.equals("") && chapterCode.equals("")) {
			hql = hql + " and chapterCode like'" + courseCode + "%'";
		}
		if (!chapterCode.equals("")) // 刷选chapterCode ，0代表全部
		{
			hql = hql + " and chapterCode='" + chapterCode + "'";
		}
		if (source.equals("") == false) {
			hql = hql + " and source like :source";
		}
		if (difficulty.equals("全部") == false) // 筛选difficulty
		{
			hql = hql + " and difficulty='" + difficulty + "'";
		}
		if (teacherId != 0) // 筛选老师
		{
			hql = hql + " and teacherId=" + teacherId;
		}
		if (sortContent.equals("编号")) {
			hql = hql + " order by p.id";
			if (sortType.equals("升序"))
				hql = hql + " asc";
			else
				hql = hql + " desc";
		}
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("keywords", "%" + keywords + "%");
		if (source.equals("") == false)
			q.setParameter("source", "%" + source + "%");
		List<Problems> problemList = q.list();
		List<PMProblems> pMProblems = new ArrayList<PMProblems>();
		for (int i = 0; i < problemList.size(); i++) {
			Problems problems = problemList.get(i);
			PMProblems p = new PMProblems();
			p.setId(problems.getId());
			p.setTitle(problems.getTitle());
			p.setSource(problems.getSource());
			p.setDescription(problems.getDescription());
			p.setChapterCode(problems.getchapterCode());
			p.setDifficulty(problems.getDifficulty());
			p.setTeacherId(problems.getTeacherId());
			p.setCategory(problems.getCategory());
			pMProblems.add(p);
		}
		return pMProblems;
	}

	// 查找所有的问题
	@Override
	public List<PMProblems> findExcludeProblemsByCondition(String keywords, String courseCode, String chapterCode,
			String source, String difficulty, String excludeCategory) {

		// TODO Auto-generated method stub
		String hql = "from Problems p where (p.title like :keywords or p.description like :keywords)";
		/*
		 * if(!courseCode.equals("") && chapterCode.equals("") ){
		 * hql=hql+" and chapterCode="+chapterCode; }
		 */
		if (!courseCode.equals("") && chapterCode.equals("")) {
			hql = hql + " and chapterCode like'" + courseCode + "%'";
		}
		if (!chapterCode.equals("")) // 刷选chapterCode ，0代表全部
		{
			hql = hql + " and chapterCode='" + chapterCode + "'";
		}
		if (source.equals("") == false) {
			hql = hql + " and source like :source";
		}
		if (difficulty.equals("全部") == false) // 筛选difficulty
		{
			hql = hql + " and difficulty='" + difficulty + "'";
		}
		if (excludeCategory != null && excludeCategory.equals("0")){
			//排除全部分类下的题目  即查找分类为空的题目
			hql = hql + " and (p.category='' or p.category is null)";
		}
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("keywords", "%" + keywords + "%");
		if (source.equals("") == false)
			q.setParameter("source", "%" + source + "%");
		List<Problems> problemList = q.list();

		List<PMProblems> pMProblems = new ArrayList<PMProblems>();
		for (int i = 0; i < problemList.size(); i++) {
			Problems problems = problemList.get(i);
			PMProblems p = new PMProblems();
			p.setId(problems.getId());
			p.setTitle(problems.getTitle());
			p.setSource(problems.getSource());
			p.setDescription(problems.getDescription());
			p.setChapterCode(problems.getchapterCode());
			p.setDifficulty(problems.getDifficulty());
			p.setTeacherId(problems.getTeacherId());
			p.setCategory(problems.getCategory());
			p.setDuration(problems.getDuration());
			pMProblems.add(p);
		}

		// 如果是排除多个一级分类 注意倒序删除，否则报错
		if (excludeCategory != null && !excludeCategory.equals("0")){
			//将多选的一级分类Id转换成数组
			String[] eCategorys = excludeCategory.split(";");
			if (eCategorys.length > 0) {
				for (int i = 0; i < eCategorys.length; i++) {
					if (!eCategorys[i].equals("")) {
						//取出每个一级分类ID
						int categoryId = Integer.parseInt(eCategorys[i]);
						//将此一级分类下的所有子类查找出来
						List<ProblemCategory> problemCategoryChildList = problemCategoryDao
								.findAllProblemCategoryByParentId(categoryId);
						ProblemCategory pc = new ProblemCategory();
						for (int j = 0; j < problemCategoryChildList.size(); j++) {
							pc = problemCategoryChildList.get(j);
							//取出每一子类的Id
							String pcid = String.valueOf(pc.getId());
							for (int k = pMProblems.size() - 1; k >= 0; k--) {
								//将题目中含有此子类Id的题目删掉
								if (pMProblems.get(k).getCategory() != null && pMProblems.get(k).getCategory().contains(pcid)) {
									pMProblems.remove(k);
								}
							}
						}
						
					}
				}
			}
		}
		
		return pMProblems;
	}

	@Override
	public void updateProblems(Problems problems) {
		this.update(problems);
	}

	@Override
	public void editProblem(PMProblems problem) {
		// TODO Auto-generated method stub
		int id = problem.getId();
		String title = problem.getTitle();
		String author = problem.getAuthor();
		String description = problem.getDescription();
		float memoryLimit = problem.getMemoryLimit();
		float timeLimit = problem.getTimeLimit();
		String source = problem.getSource();
		String inputRequirement = problem.getInputRequirement();
		String outputRequirement = problem.getOutputRequirement();
		String sampleInput = problem.getSampleInput();
		String sampleOuput = problem.getSampleOuput();
		String difficulty = problem.getDifficulty();
		String sourceCode = problem.getSourceCode();
		String srcCodeLanguage = problem.getSrcCodeLanguage();
		String scoreGrade = problem.getScoreGrade();
		String chapterCode = problem.getChapterCode();
		boolean checkSimilarity = problem.isCheckSimilarity();
		float similarityThreshold = problem.getSimilarityThreshold();

		Date updateTime = problem.getUpdateTime();
		String hql = "update Problems set title=:title,author=:author,description=:description,memoryLimit=:memoryLimit,timeLimit=:timeLimit,inputRequirement=:inputRequirement,outputRequirement=:outputRequirement,"
				+ "sampleInput=:sampleInput,sampleOuput=:sampleOuput,difficulty=:difficulty,sourceCode=:sourceCode,srcCodeLanguage=:srcCodeLanguage,scoreGrade=:scoreGrade,chapterCode=:chapterCode,"
				+ "checkSimilarity=:checkSimilarity,similarityThreshold=:similarityThreshold,source=:source,updateTime=:updateTime where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("title", title);
		q.setParameter("author", author);
		q.setParameter("description", description);
		q.setParameter("memoryLimit", memoryLimit);
		q.setParameter("timeLimit", timeLimit);
		q.setParameter("inputRequirement", inputRequirement);
		q.setParameter("outputRequirement", outputRequirement);
		q.setParameter("sampleInput", sampleInput);
		q.setParameter("sampleOuput", sampleOuput);
		q.setParameter("difficulty", difficulty);
		q.setParameter("sourceCode", sourceCode);
		q.setParameter("srcCodeLanguage", srcCodeLanguage);
		q.setParameter("scoreGrade", scoreGrade);
		q.setParameter("chapterCode", chapterCode);
		q.setParameter("checkSimilarity", checkSimilarity);
		q.setParameter("similarityThreshold", similarityThreshold);
		q.setParameter("source", source);
		q.setParameter("updateTime", updateTime);
		q.setParameter("id", id);
		q.executeUpdate();

		String examprohql = "update Examproblems set updateTime=:updateTime where problemId=:problemId";
		Query query = this.getCurrentSession().createQuery(examprohql);
		query.setParameter("updateTime", updateTime);
		query.setParameter("problemId", id);
		query.executeUpdate();

		String examhql = "update Exam set updateTime=:updateTime where id in (select ex.examId from Examproblems ex where problemId=:problemId)";
		query = this.getCurrentSession().createQuery(examhql);
		query.setParameter("updateTime", updateTime);
		query.setParameter("problemId", id);
		query.executeUpdate();
	}

	@Override
	public List<Problems> findExamProblems(int examId) {
		// TODO Auto-generated method stub
		String hql = "from Problems p where p.id in (select e.problemId from Examproblems e where e.examId=" + examId
				+ ")";
		List<Problems> problemList = this.find(hql);
		return problemList;
	}
	
	@Override
	public List<Problems> findTrainProblems(int examId) {
		// TODO Auto-generated method stub
		String hql = "from Problems p where p.id in (select e.problemId from Itrainproblems e where e.examId=" + examId
				+ ")";
		List<Problems> problemList = this.find(hql);
		return problemList;
	}

	@Override
	public void deleteProblem(int id) {
		// TODO Auto-generated method stub
		String hql = "delete from Problems where id=" + id;
		this.executeHql(hql);
	}

	@Override
	public List<Problems> findProblemByteacherId(int teacherId) {
		String hql = "from Problems where teacherId='" + teacherId + "'";
		List<Problems> problemList = this.find(hql);
		return problemList;
	}

	@Override
	public List<PMProblems> findProblemsByCategory(String category) {
		String hql_problem = "from Problems p WHERE p.category like'%," + category + ",%'";
		Query queryObject = getCurrentSession().createQuery(hql_problem);
		List<Problems> problemsList = queryObject.list();
		List<PMProblems> PMProblemsList = new ArrayList<PMProblems>();
		for (int i = 0; i < problemsList.size(); i++) {
			Problems problems = problemsList.get(i);
			PMProblems p = new PMProblems();
			p.setId(problems.getId());
			p.setTitle(problems.getTitle());
			p.setSource(problems.getSource());
			p.setDescription(problems.getDescription());
			p.setChapterCode(problems.getchapterCode());
			p.setDifficulty(problems.getDifficulty());
			p.setTeacherId(problems.getTeacherId());
			p.setCategory(problems.getCategory());
			p.setDuration(problems.getDuration());
			PMProblemsList.add(p);
		}

		return PMProblemsList;
	}

	@Override
	public String addProblemClassification(int id, String category,int duration) {
		try {
			String addCategory = category;
			// 获取该题目在数据库中已有的的题目分类
			String hql_category = "select p.category from Problems p WHERE p.id=" + id;
			Query queryObject = getCurrentSession().createQuery(hql_category);
			List<String> beforeCategryLsit = queryObject.list();
			String beforeCategory = beforeCategryLsit.get(0);
			//更新该题目的题目分类
			if (beforeCategory != null&&!beforeCategory.equals("")) {
				//如果原来数据库中存在该题目的分类，则追加
				addCategory = beforeCategory + addCategory+",";
			}else{
				//如果原来数据库中不存在该题目的分类
				addCategory = ","+addCategory+",";
			}
			//更新分类
			hql_category = "update Problems set category=:category where id=:id";
			Query q_catogoey = this.getCurrentSession().createQuery(hql_category);
			q_catogoey.setParameter("category", addCategory);
			q_catogoey.setParameter("id", id);
			q_catogoey.executeUpdate();
			//如果duration不为空。更新duration
			if(duration!=0){
				String hql_duration = "update Problems set duration=:duration where id=:id";
				Query q_duration = this.getCurrentSession().createQuery(hql_duration);
				q_duration.setParameter("duration", duration);
				q_duration.setParameter("id", id);
				q_duration.executeUpdate();
			}
			
			//二级分类题目数量+1
			String hql_update_number = "update ProblemCategory set problemNum=problemNum+1 where id=:category";
			Query q_number = this.getCurrentSession().createQuery(hql_update_number);
			q_number.setParameter("category", Integer.parseInt(category));
			q_number.executeUpdate();
			
			//获取该分类对应的父分类的id
			String hql_parent_id = "SELECT id from ProblemCategory where id =(select parentId FROM ProblemCategory where id=" + Integer.parseInt(category)+")";
			Query queryObject_parent_id = getCurrentSession().createQuery(hql_parent_id);
			List<Integer> parentIdLsit = queryObject_parent_id.list();
			int parentId = parentIdLsit.get(0);
			//父分类题目数量+1
			String hql_update_parent_number = "update ProblemCategory set problemNum=problemNum+1 where id=:parentId";
			Query q_parent_number = this.getCurrentSession().createQuery(hql_update_parent_number);
			q_parent_number.setParameter("parentId", parentId);
			q_parent_number.executeUpdate();
			
			return null; // 返回信息null,表示添加成功，没有错误信息
		} catch (Exception e) {
			return e.toString();
		}

	}

	@Override
	public String findProblemClassifications(int problemId) {
		try {
			// 获取该题目之前的题目分类
			String hql_category = "select p.category from Problems p WHERE p.id=" + problemId;
			Query queryObject = getCurrentSession().createQuery(hql_category);
			List<String> beforeCategryLsit = queryObject.list();
			String beforeCategory = beforeCategryLsit.get(0);
			return beforeCategory; // 返回信息
		} catch (Exception e) {
			return e.toString();
		}

	}
	
	@Override
	public int findProblemDuration(int problemId) {
		try {
			// 获取该题目之前的题目分类
			String hql = "select p.duration from Problems p WHERE p.id=" + problemId;
			Query queryObject = getCurrentSession().createQuery(hql);
			List<Integer> durationLsit = queryObject.list();
			int duration = durationLsit.get(0);
			return duration; // 返回信息
		} catch (Exception e) {
			return -1;
		}

	}
	

	@Override
	public String deleteProblemClassification(Integer id, String secondClassification) {
		try {
			// 获取该题目之前的题目分类
			String hql_category = "select p.category from Problems p WHERE p.id=" + id;
			Query queryObject = getCurrentSession().createQuery(hql_category);
			List<String> beforeCategryLsit = queryObject.list();
			String beforeCategory = beforeCategryLsit.get(0);
			// 转换成数组
			String[] valueArr = beforeCategory.split(",");
			// 生成新的题目分类String语句
			String AfterCategory = ",";
			//将之前的题目分类ID删掉
			for (int j = 0; j < valueArr.length; j++) {
				if (!valueArr[j].equals("")&&Integer.parseInt(valueArr[j]) != Integer.parseInt(secondClassification)) {
					AfterCategory = AfterCategory + valueArr[j] + ",";
				}
			}
			//如果原分类中只存在一个,直接设置 为null
			if(AfterCategory==","){
				AfterCategory=null;
			}
			//删掉指定分类后，开始存储新的分类
			String hql = "update Problems set category=:category where id=:id";
			Query q = this.getCurrentSession().createQuery(hql);
			q.setParameter("category", AfterCategory);
			q.setParameter("id", id);
			q.executeUpdate();
			
			
			//二级分类的题目数量-1
			String hql_update_number = "update ProblemCategory set problemNum=problemNum-1 where id=:category";
			Query q_number = this.getCurrentSession().createQuery(hql_update_number);
			q_number.setParameter("category", Integer.parseInt(secondClassification));
			q_number.executeUpdate();
			
			//删除该分类对应的父分类的题目数量
			//获取该分类对应的父分类的id
			String hql_parent_id = "SELECT id from ProblemCategory where id =(select parentId FROM ProblemCategory where id=" + Integer.parseInt(secondClassification)+")";
			Query queryObject_parent_id = getCurrentSession().createQuery(hql_parent_id);
			List<Integer> parentIdLsit = queryObject_parent_id.list();
			int parentId = parentIdLsit.get(0);
			//父分类题目数量-1
			String hql_update_parent_number = "update ProblemCategory set problemNum=problemNum-1 where id=:parentId";
			Query q_parent_number = this.getCurrentSession().createQuery(hql_update_parent_number);
			q_parent_number.setParameter("parentId", parentId);
			q_parent_number.executeUpdate();
			
			return null; // 返回信息null,表示删除成功，没有错误信息
		} catch (Exception e) {
			return e.toString();
		}
	}

}
