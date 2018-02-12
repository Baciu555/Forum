package com.baciu.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude = {"user", "section", "tags", "comments"})
@Table(name = "thread")
public class Thread implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "subject")
	@NotEmpty(message = "pole nie może zostac puste")
	@NotNull
	private String subject;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entry_date")
	private Date entryDate;
	
	@Column(name = "views_count", nullable = false)
	private Long viewsCount;
	
	@Column(name = "content")
	@NotEmpty(message = "pole nie może zostac puste")
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "section_id", nullable = false)
	private Section section;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "thread_tag", joinColumns = {
			@JoinColumn(name = "thread_id", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "tag_id",
			nullable = false, updatable = false)})
	private Set<Tag> tags = new HashSet<Tag>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "thread", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<Comment>(0);

}
