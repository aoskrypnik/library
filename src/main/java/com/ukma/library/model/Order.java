package com.ukma.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime takenDate;
	@NotNull
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	@NotNull
	private LocalDateTime estimatedReturnDate;
	private LocalDateTime actualReturnDate;
	private LocalDateTime registeredDate;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "order_copy",
			joinColumns = {@JoinColumn(name = "order_num")},
			inverseJoinColumns = {@JoinColumn(name = "copy_id")})
	private Set<Copy> copies = new HashSet<>();
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
