package edu.dhu.exam.dao;

import edu.dhu.exam.model.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.sql.Date;
import java.util.List;

@Mapper
public interface ExamDaoNew {
    @Select("select endtime from exam where id = #{examId}")
    Date getExamEndTime(Integer examId);

    @Select("select count(*) from exam")
    Long getAllExamsNum();

    @Select("select * from exam order by exam.endtime desc")
    List<Exam> getExamsByPage(RowBounds rowBounds);

    @Select("select count(distinct e.id ) from exam e,examclasses ec,classes c,classstudents cs where ec.examId = e.id and ec.classId = c.id and cs.classId = c.id and cs.userId = #{studentId} and UNIX_TIMESTAMP(starttime) < UNIX_TIMESTAMP(current_timestamp()) + 1 * 60 * 60 and (endtime>current_timestamp() or UNIX_TIMESTAMP(endtime) > UNIX_TIMESTAMP(current_timestamp())-180*24 * 60 * 60)")
    long getAllExamsNumByStuId(Integer studentId);

    @Select("select distinct * from exam e,examclasses ec,classes c,classstudents cs where ec.examId = e.id and ec.classId = c.id and cs.classId = c.id and cs.userId = #{studentId} and UNIX_TIMESTAMP(starttime) < UNIX_TIMESTAMP(current_timestamp()) + 1 * 60 * 60 and (endtime>current_timestamp() or UNIX_TIMESTAMP(endtime) > UNIX_TIMESTAMP(current_timestamp())-180*24 * 60 * 60) order by e.endtime desc")
    List<Exam> getExamsByPageAndStuId(Integer studentId, RowBounds rowBounds);
}
