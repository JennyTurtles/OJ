package edu.dhu.service;

import edu.dhu.model.Chapters;
import edu.dhu.pageModel.PMChapters;

import java.util.List;

public interface ChapterServiceI {

	List<PMChapters> findAllChapter();

	List<PMChapters> findChaptersBycourseCode(String code);

	List<PMChapters> findAllCourses();

	void deleteChapter(int id);

	boolean editChapter(int id, String name);

	public Chapters findChapterById(int id);

	public PMChapters findChaptersByCode(String code);
	

	public void addChapter(PMChapters pmchapters);

	public void addCourse(PMChapters pmchapters);

	public List<PMChapters> findChaptersByName(String name);
	
	public Chapters findChapterCodeByName(String courseName,String chapterName);
	
	
	
	

}
