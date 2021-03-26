package com.ukma.library.service.impl;

import com.ukma.library.dto.OrderSaveDto;
import com.ukma.library.exception.NoCopiesAvailableException;
import com.ukma.library.exception.ResourceNotFoundException;
import com.ukma.library.model.Book;
import com.ukma.library.model.Copy;
import com.ukma.library.model.Order;
import com.ukma.library.model.OrderStatus;
import com.ukma.library.repository.OrderRepository;
import com.ukma.library.service.BookService;
import com.ukma.library.service.CopyService;
import com.ukma.library.service.OrderService;
import com.ukma.library.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toSet;

@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private CopyService copyService;
	@Resource
	private UserService userService;
	@Resource
	private BookService bookService;
	@Resource
	private OrderRepository orderRepository;

	private static final String ORDER_NOT_FOUND_WITH_ID = "Order not found with id ";

	@Override
	public Order save(OrderSaveDto orderSaveDto) {
		Set<Copy> copies = orderSaveDto.getBookIsbns().stream()
				.map(isbn -> copyService.getFirstByBookIsbnAndIsAvailable(isbn)
						.orElseThrow(() -> new NoCopiesAvailableException(isbn)))
				.collect(toSet());

		copies.forEach(copy -> copy.setIsAvailable(false));

		Set<Book> books = orderSaveDto.getBookIsbns().stream()
				.map(isbn -> bookService.getById(isbn))
				.collect(toSet());

		LocalDateTime registeredDate = now();

		Order order = Order.builder()
				.status(OrderStatus.PENDING)
				.copies(copies)
				.books(books)
				.registeredDate(registeredDate)
				.estimatedReturnDate(registeredDate.plusMonths(1))
				.user(userService.getCurrentUser())
				.build();

		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	@Override
	public Order getById(Long orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException(ORDER_NOT_FOUND_WITH_ID + orderId));
	}

	@Override
	@Transactional
	public Order update(Order order, Long orderId) {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		if (optionalOrder.isEmpty())
			throw new ResourceNotFoundException(ORDER_NOT_FOUND_WITH_ID + orderId);

		Order orderToSave = optionalOrder.get();

		switch (order.getStatus()) {
			case TAKEN:
				orderToSave.setTakenDate(now());
				break;
			case RETURNED:
				orderToSave.setActualReturnDate(now());
				for (Copy copy : orderToSave.getCopies()) {
					copy.setIsAvailable(true);
				}
				break;
			case CANCELED:
				for (Copy copy : orderToSave.getCopies()) {
					copy.setIsAvailable(true);
				}
				break;
		}

		orderToSave.setStatus(order.getStatus());

		return orderToSave;
	}

	@Override
	public List<Order> search(Long userId, OrderStatus status) {
		List<Order> orders = orderRepository.findByUserIdAndStatus(userId, status);
		orders.sort((Comparator.comparing(Order::getRegisteredDate).reversed()));
		return orders;
	}
}
