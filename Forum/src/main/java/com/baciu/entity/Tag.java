package com.baciu.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tags")
public class Tag implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "name")
	@NotBlank(message = "pole nie moze zostac puste")
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
	private Set<Thread> threads =  new HashSet<Thread>(0);

	public Tag() {}

	public Tag(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Tag(Long id, String name, Set<Thread> threads) {
		super();
		this.id = id;
		this.name = name;
		this.threads = threads;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Thread> getThreads() {
		return threads;
	}

	public void setThreads(Set<Thread> threads) {
		this.threads = threads;
	}
	
}
