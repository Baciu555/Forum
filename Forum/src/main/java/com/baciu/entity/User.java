package com.baciu.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id", unique = true, nullable = false)
	private Long id;
	
	@Size(min=1, max=30, message = "Długość loginu powinna mieścic się w granicach 1 - 30 znaków")
	@Column(name = "username")
	private String username;
	
	@Size(min=1, max=250, message = "Długość hasła powinna mieścic się w granicach 1 - 250 znaków")
	@Column(name = "password")
	@JsonIgnore
	private String password;
	
	@NotEmpty(message = "Niepoprawny adres email")
	@Email(message = "Niepoprawny adres email")
	@Column(name = "email")
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "join_date")
	private Date joinDate;
	
	@Column(name = "permission")
	private String permission;
	
	@Column(name = "avatar_path")
	private String avatarPath;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ban_expire")
	private Date banExpire;
	
	@Column(name = "ban_count")
	private Integer banCount;
	
	@Formula("(SELECT COUNT(*) FROM Comments c WHERE c.user_id = id)")
	private Integer commentsCount;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Thread> thread = new HashSet<Thread>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Comment> comment = new HashSet<Comment>(0);

	public User() {}

	public User(Long id, String username, String password, String email, Date joinDate, String permission,
			String avatarPath, Date banExpire, Integer banCount) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.joinDate = joinDate;
		this.permission = permission;
		this.avatarPath = avatarPath;
		this.banExpire = banExpire;
		this.banCount = banCount;
	}

	public User(Long id, String username, String password, String email, Date joinDate, String permission,
			String avatarPath, Date banExpire, Integer banCount,
			Set<Thread> thread, Set<Comment> comment) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.joinDate = joinDate;
		this.permission = permission;
		this.avatarPath = avatarPath;
		this.banExpire = banExpire;
		this.banCount = banCount;
		this.thread = thread;
		this.comment = comment;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public Date getBanExpire() {
		return banExpire;
	}

	public void setBanExpire(Date banExpire) {
		this.banExpire = banExpire;
	}

	public Integer getBanCount() {
		return banCount;
	}

	public void setBanCount(Integer banCount) {
		this.banCount = banCount;
	}

	public Set<Thread> getThread() {
		return thread;
	}

	public void setThread(Set<Thread> thread) {
		this.thread = thread;
	}

	public Set<Comment> getComment() {
		return comment;
	}

	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}

	public Integer getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Integer commentsCount) {
		this.commentsCount = commentsCount;
	}
	
	

}
