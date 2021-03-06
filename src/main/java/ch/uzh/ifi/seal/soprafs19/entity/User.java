package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class User implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String forename;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, unique = true) 
	private String username;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true) 
	private String token;

	@Column(nullable = false)
	private UserStatus status;

	@Column(nullable = false)
	private String birthday;

	@Column(nullable = false)
	private LocalDate registrationDate;

	public Long getId() {	return id;	}

	public void setId(Long id) { this.id = id;	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {	this.forename = forename;	}

	public String getName() {
		return name;
	}

	public void setName(String name) {	this.name = name;	}

	public String getEmail() {	return email;	}

	public void setEmail(String email) { this.email = email;	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getToken() {	return token;	}

	public void setToken(String token) { this.token = token;	}

	public UserStatus getStatus() { return status;	}

	public void setStatus(UserStatus status) {	this.status = status;	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof User)) {
			return false;
		}
		User user = (User) o;
		return this.getId().equals(user.getId());
	}
}
