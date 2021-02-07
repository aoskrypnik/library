package com.ukma.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "copy")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Copy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private Boolean isAvailable;
	@NotNull
	@Enumerated(EnumType.STRING)
	private BookState state;
	private Timestamp estimatedReturnDate;
	@JsonIgnore
	@ManyToMany(mappedBy = "copies")
	private Set<Order> orders = new HashSet<>();
	@JsonIgnore
	@ManyToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "isbn")
	private Book book;
}
