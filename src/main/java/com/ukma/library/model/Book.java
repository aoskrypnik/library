package com.ukma.library.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	@Id
	private String isbn;
	@NotNull
	private String title;
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
			name = "book_author",
			joinColumns = {@JoinColumn(name = "isbn")},
			inverseJoinColumns = {@JoinColumn(name = "author_id")})
	private Set<Author> authors = new HashSet<>();
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
			name = "book_genre",
			joinColumns = {@JoinColumn(name = "isbn")},
			inverseJoinColumns = {@JoinColumn(name = "genre_id", nullable = false)})
	private Set<Genre> genres = new HashSet<>();
	@NotNull
	private Integer copiesNum;
	@NotNull
	private Integer publishYear;
	@NotNull
	private Integer pagesNum;
	@NotNull
	private String publishCountry;
	@NotNull
	private String language;
	@NotNull
	private String imageLink;
	@OneToMany(mappedBy = "book")
	private Set<Copy> copies;
}
