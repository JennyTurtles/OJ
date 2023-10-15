package edu.dhu.dao;

import edu.dhu.model.AssistantClass;

import java.util.List;

public interface AssistantClassDaoI extends BaseDaoI<AssistantClass> {

	public boolean addAssistantClass(int assistantId, int classId); // 为班级添加助教

	public List<AssistantClass> getAssistantClassByAssistantId(int assistantId); // 查询助教所属的班级

	public List<AssistantClass> getAssistantClassAndClassNotEnd(int teacherId,
			int assistantId); // 获取教师没有结束的班级,时间为一年以内

	public boolean delAssistantClass(int assistantId, int classId); // 删除一行

}
