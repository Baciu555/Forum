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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "thread")
	private Set<Comment> comments = new HashSet<Comment>(0);
	
	public Thread() {}

	public Thread(Long id, String subject, Date entryDate, Long viewsCount, String content) {
		super();
		this.id = id;
		this.subject = subject;
		this.entryDate = entryDate;
		this.viewsCount = viewsCount;
		this.content = content;
	}

	public Thread(Long id, String subject, Date entryDate, Long viewsCount, String content, User user,
			Section section, Set<Tag> tags, Set<Comment> comments) {
		super();
		this.id = id;
		this.subject = subject;
		this.entryDate = entryDate;
		this.viewsCount = viewsCount;
		this.content = content;
		this.user = user;
		this.section = section;
		this.tags = tags;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Long getViewsCount() {
		return viewsCount;
	}

	public void setViewsCount(Long viewsCount) {
		this.viewsCount = viewsCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

}
