package edu.dhu.problem.dao;

import edu.dhu.exam.dao.BaseDaoI;
import edu.dhu.problem.model.Chapters;
import edu.dhu.problem.model.PMChapters;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ChapterDaoI extends BaseDaoI<Chapters> {

	public List<PMChapters> findAllChapter();

	// 根据章节ID查找Chapters
	public Chapters getChaptersById(int chaptersId);

	public List<PMChapters> findChaptersBycourseCode(String code);

	public List<PMChapters> findAllCourses();

	public void deleteChapter(int id);

	public Chapters findChaptersById(int id);

	public PMChapters findChaptersByCode(String code);

	public void addChapter(PMChapters pmchapters);

	public void addCourse(PMChapters pmchapters);

	public List<PMChapters> findChaptersByName(String name);

}
