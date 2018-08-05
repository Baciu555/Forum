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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude = "threads")
@Table(name = "section")
public class Section implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "name")
	@NotBlank(message = "pole nie moze zostac puste")
	@Size(min=0, max=255, message = "Długość nazwy sekcji może maksymalnie wynosić 255 znaków")
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "section")
	private Set<Thread> threads = new HashSet<Thread>(0);

}
