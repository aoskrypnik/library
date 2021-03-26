package com.ukma.library.repository;

import com.ukma.library.dto.OrderFilterDto;
import com.ukma.library.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Repository
public class OrderCriteriaRepositoryImpl implements OrderCriteriaRepository {

	@Resource
	private EntityManager entityManager;

	@Override
	public Page<Order> search(OrderFilterDto orderFilter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		List<Predicate> predicates = getPredicates(orderFilter, criteriaBuilder, root);

		query.select(root).where(predicates.toArray(Predicate[]::new));

		TypedQuery<Order> typedQuery = entityManager.createQuery(query);

		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int offset = pageNumber * pageSize;

		typedQuery.setFirstResult(offset);
		typedQuery.setMaxResults(pageSize);

		List<Order> orders = typedQuery.getResultList();
		return new PageImpl<>(
				orders,
				pageable,
				getTotalCount(criteriaBuilder, orderFilter)
		);
	}

	private Long getTotalCount(CriteriaBuilder criteriaBuilder, OrderFilterDto orderFilter) {
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Order> root = criteriaQuery.from(Order.class);

		criteriaQuery.select(criteriaBuilder.count(root));
		criteriaQuery.where(getPredicates(orderFilter, criteriaBuilder, root).toArray(Predicate[]::new));

		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

	private List<Predicate> getPredicates(OrderFilterDto orderFilter, CriteriaBuilder criteriaBuilder,
										  Root<Order> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (nonNull(orderFilter.getUserId())) {
			predicates.add(criteriaBuilder.equal(root.get("user"), orderFilter.getUserId()));
		}
		if (nonNull(orderFilter.getStatus())) {
			predicates.add(criteriaBuilder.equal(root.get("status"), orderFilter.getStatus()));
		}

		return predicates;
	}
}
