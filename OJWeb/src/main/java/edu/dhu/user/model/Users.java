package edu.dhu.user.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "users", catalog = "gdoj")
public class Users implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1908545072948146720L;
	// Fields
	private Integer id;
	private String username;
	private String password;
	private String studentNo;
	private String chineseName;
	private String banji;
	private Date createDate;
	private String email;
	private Integer solved;
	private Integer submit;
	private String messageCondition;
	private Integer schoolId;
	private String uuid;
	private String question;
	private String answer;

	// Constructors

	/** default constructor */
	public Users() {
	}

	/** minimal constructor */
	public Users(String username, String password, Date createDate) {
		this.username = username;
		this.password = password;
		this.createDate = createDate;
	}

	/** full constructor */
	public Users(String username, String password, String studentNo,
                 String chineseName, String banji, Date createDate, String email,
                 Integer solved, Integer submit, String messageCondition,
                 Integer schoolId, String uuid, String question, String answer) {
		this.username = username;
		this.password = password;
		this.studentNo = studentNo;
		this.chineseName = chineseName;
		this.banji = banji;
		this.createDate = createDate;
		this.email = email;
		this.solved = solved;
		this.submit = submit;
		this.messageCondition = messageCondition;
		this.schoolId = schoolId;
		this.uuid = uuid;
		this.question = question;
		this.answer = answer;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "username", unique = true, length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "schoolId", unique = true, nullable = false)
	public Integer getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	// 这里根据学号来索引用户，
	@Column(name = "studentNo", unique = true, nullable = false, length = 20)
	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	@Column(name = "chineseName", length = 30)
	public String getChineseName() {
		return this.chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	@Column(name = "banji", length = 50)
	public String getBanji() {
		return this.banji;
	}

	public void setBanji(String banji) {
		this.banji = banji;
	}

	@Column(name = "createDate", nullable = false, length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "Email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "solved")
	public Integer getSolved() {
		return this.solved;
	}

	public void setSolved(Integer solved) {
		this.solved = solved;
	}

	@Column(name = "submit")
	public Integer getSubmit() {
		return this.submit;
	}

	public void setSubmit(Integer submit) {
		this.submit = submit;
	}

	@Column(name = "messageCondition")
	public String getMessageCondition() {
		return messageCondition;
	}

	public void setMessageCondition(String messageCondition) {
		this.messageCondition = messageCondition;
	}

	@Column(name = "uuid", length = 100)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "question", length = 50)
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Column(name = "answer", length = 20)
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}