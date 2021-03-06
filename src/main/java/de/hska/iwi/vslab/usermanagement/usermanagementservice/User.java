package de.hska.iwi.vslab.usermanagement.usermanagementservice;
public class User {

	private Long id;
	private String username;
	private String peter;
	private String firstname;
	private String lastname;
	private Integer roleId;

	public User(){}

	public User(Long id, String username, String peter, String firstname, String lastname, Integer roleId) {
		this.id = id;
		this.username = username;
		this.peter = peter;
		this.firstname = firstname;
		this.lastname = lastname;
		this.roleId = roleId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getPeter() {
		return peter;
	}

	public void setPeter(String peter) {
		this.peter = peter;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", peter=" + peter + "]";
	}

}
