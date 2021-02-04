package com.ukma.library.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
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
	@ManyToMany(mappedBy = "copies")
	private Set<Order> orders = new HashSet<>();
	@ManyToOne(optional = false)
	@JoinColumn(name = "isbn", nullable = false)
	private Book book;
}
