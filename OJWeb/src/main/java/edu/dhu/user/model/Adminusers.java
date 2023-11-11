package edu.dhu.user.model;

import javax.persistence.*;

/**
 * Adminusers entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "adminusers")
public class Adminusers implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8223565460184619883L;

	// Fields
	private Integer id;
	private Integer schoolId;
	private String account;
	private String password;
	private String name;
	private String role;
	private String email;
	private Integer active;
	private String uuid;
	private String question;
	private String answer;

	// Constructors

	/** default constructor */
	public Adminusers() {
	}

	/** minimal constructor */
	public Adminusers(String account, String password, String name,
                      String role, String email, Integer schoolId) {
		this.account = account;
		this.password = password;
		this.name = name;
		this.role = role;
		this.email = email;
		this.schoolId = schoolId;
	}

	/** full constructor */
	public Adminusers(String account, String password, String name,
                      String role, String email, Integer schoolId, Integer active,
                      String uuid, String question, String answer) {
		this.account = account;
		this.password = password;
		this.name = name;
		this.role = role;
		this.email = email;
		this.schoolId = schoolId;
		this.active = active;
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

	@Column(name = "account", nullable = false, length = 50)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "password", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "role", nullable = false, length = 10)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "Email", length = 30)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "schoolId", nullable = false, length = 11)
	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	@Column(name = "active", nullable = false, length = 1)
	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	@Column(name = "uuid", length = 100)
	public String getUuid() {
		return uuid;
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