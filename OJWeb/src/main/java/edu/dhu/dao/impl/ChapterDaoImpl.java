package edu.dhu.dao.impl;

import edu.dhu.dao.ChapterDaoI;
import edu.dhu.model.Chapters;
import edu.dhu.pageModel.PMChapters;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("chapterDao")
public class ChapterDaoImpl extends BaseDaoImpl<Chapters> implements
		ChapterDaoI {

	@Override
	public List<PMChapters> findAllChapter() {
		// TODO Auto-generated method stub
		String hql = "from Chapters where level = 2 order by code";
		List<Chapters> chaptersList = this.find(hql);
		List<PMChapters> pmchaptersList = new ArrayList<PMChapters>();
		for (int i = 0; i < chaptersList.size(); i++) {
			Chapters chapter = chaptersList.get(i);
			PMChapters p = new PMChapters();
			p.setId(chapter.getId());
			p.setChapterName(chapter.getName());
			p.setCode(chapter.getCode());
			String chapterCode = chapter.getCode().substring(0, 3);
			String hql2 = "from Chapters where level = 1 and code = '"
					+ chapterCode + "'";
			Chapters chapters2 = this.get(hql2);
			p.setName(chapters2.getName());
			pmchaptersList.add(p);
		}

		return pmchaptersList;
	}

	@Override
	public List<PMChapters> findAllCourses() {
		// TODO Auto-generated method stub
		String hql = "from Chapters where level = 1  order by code";
		List<Chapters> chaptersList = this.find(hql);
		List<PMChapters> pmchaptersList = new ArrayList<PMChapters>();
		for (int i = 0; i < chaptersList.size(); i++) {
			Chapters chapter = chaptersList.get(i);
			PMChapters p = new PMChapters();
			p.setId(chapter.getId());
			p.setName(chapter.getName());
			p.setCode(chapter.getCode());
			pmchaptersList.add(p);
		}

		return pmchaptersList;
	}

	@Override
	public Chapters getChaptersById(int chaptersId) {
		return this.get(Chapters.class, chaptersId);
	}

	@Override
	public List<PMChapters> findChaptersBycourseCode(String code) {
		// TODO Auto-generated method stub
		String hql = "from Chapters where level=2 and code like'" + code
				+ "%' order by code";
		List<Chapters> chaptersList = this.find(hql);
		List<PMChapters> pmchaptersList = new ArrayList<PMChapters>();
		for (int i = 0; i < chaptersList.size(); i++) {
			Chapters chapter = chaptersList.get(i);
			PMChapters p = new PMChapters();
			p.setId(chapter.getId());
			p.setChapterName(chapter.getName());
			p.setCode(chapter.getCode());
			String chapterCode = chapter.getCode().substring(0, 3);
			String hql2 = "from Chapters where level = 1 and code = '"
					+ chapterCode + "'";
			Chapters chapters2 = this.get(hql2);
			p.setName(chapters2.getName());
			pmchaptersList.add(p);
		}

		return pmchaptersList;
	}

	@Override
	public void deleteChapter(int id) {
		// TODO Auto-generated method stub
		String hql = "delete from Chapters where id=" + id;
		this.executeHql(hql);
	}

	@Override
	public Chapters findChaptersById(int id) {
		// TODO Auto-generated method stub
		return this.get(Chapters.class, id);
	}

	@Override
	public PMChapters findChaptersByCode(String code) {
		// TODO Auto-generated method stub
		String hql = "from Chapters where code='" + code+"'";
		Chapters chapter = this.get(hql);
		PMChapters p = new PMChapters();
		p.setId(chapter.getId());
		p.setName(chapter.getName());
		p.setCode(chapter.getCode());
		return p;
	}

	@Override
	public void addChapter(PMChapters pmchapters) {
		// TODO Auto-generated method stub
		Chapters chapters = new Chapters();
		chapters.setCode(pmchapters.getCode());
		chapters.setLevel(2);
		chapters.setName(pmchapters.getName());
		this.save(chapters);
	}

	@Override
	public void addCourse(PMChapters pmchapters) {
		// TODO Auto-generated method stub
		Chapters chapters = new Chapters();
		chapters.setCode(pmchapters.getCode());
		chapters.setLevel(1);
		chapters.setName(pmchapters.getName());
		this.save(chapters);
	}

	@Override
	public List<PMChapters> findChaptersByName(String name) {
		// TODO Auto-generated method stub
		String hql = "from Chapters where level=1 and name like'%" + name
				+ "%' order by code";
		List<Chapters> chaptersList = this.find(hql);
		List<PMChapters> pmchaptersList = new ArrayList<PMChapters>();
		for (int i = 0; i < chaptersList.size(); i++) {
			Chapters chapter = chaptersList.get(i);
			PMChapters p = new PMChapters();
			p.setId(chapter.getId());
			p.setCode(chapter.getCode());
			p.setName(chapter.getName());
			pmchaptersList.add(p);
		}

		return pmchaptersList;
	}

}
