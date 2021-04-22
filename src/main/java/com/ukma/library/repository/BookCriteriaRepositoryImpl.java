package com.ukma.library.repository;

import com.ukma.library.dto.BookFilterDto;
import com.ukma.library.model.Author;
import com.ukma.library.model.Book;
import com.ukma.library.model.Copy;
import com.ukma.library.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Repository
public class BookCriteriaRepositoryImpl implements BookCriteriaRepository {

	@Resource
	private EntityManager entityManager;

	@Override
	public Page<Book> search(BookFilterDto filter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
		Root<Book> root = query.from(Book.class);
		List<Predicate> predicates = getPredicates(filter, criteriaBuilder, root);

		query.distinct(true).select(root).where(predicates.toArray(Predicate[]::new));

		TypedQuery<Book> typedQuery = entityManager.createQuery(query);

		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int offset = pageNumber * pageSize;

		typedQuery.setFirstResult(offset);
		typedQuery.setMaxResults(pageSize);

		List<Book> books = typedQuery.getResultList();
		return new PageImpl<>(
				books,
				pageable,
				getTotalCount(criteriaBuilder, filter)
		);
	}

	private Long getTotalCount(CriteriaBuilder criteriaBuilder, BookFilterDto filter) {
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Book> root = criteriaQuery.from(Book.class);

		criteriaQuery.distinct(true).select(criteriaBuilder.count(root));
		criteriaQuery.where(getPredicates(filter, criteriaBuilder, root).toArray(Predicate[]::new));

		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

	private List<Predicate> getPredicates(BookFilterDto filter, CriteriaBuilder criteriaBuilder, Root<Book> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!isBlank(filter.getTitle())) {
			predicates.add(criteriaBuilder.like(
					criteriaBuilder.upper(root.get("title")),
					"%" + filter.getTitle().toUpperCase() + "%")
			);
		}
		if (!isBlank(filter.getAuthor())) {
			Join<Book, Author> bookAuthorJoin = root.join("authors");
			predicates.add(criteriaBuilder.equal(bookAuthorJoin.get("authorName"), filter.getAuthor()));
		}
		if (!isBlank(filter.getGenre())) {
			Join<Book, Genre> bookGenreJoin = root.join("genres");
			predicates.add(criteriaBuilder.equal(bookGenreJoin.get("genreName"), filter.getGenre()));
		}
		if (filter.isAvailableOnly()) {
			Join<Book, Copy> bookCopyJoin = root.join("copies");
			predicates.add(criteriaBuilder.equal(bookCopyJoin.get("isAvailable"), true));
		}
		return predicates;
	}
}
