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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude = {"thread", "comment", "roles"})
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
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<Role> roles = new HashSet<>(0);
	
	@PrePersist
	protected void onCreate() {
		if (joinDate == null) joinDate = new Date();
		if (avatarPath == null) avatarPath = "default-avatar.jpg";
		if (banCount == null) banCount = 0;
	}

}
