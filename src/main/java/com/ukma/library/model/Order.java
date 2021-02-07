package com.ukma.library.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private Timestamp takenDate;
	@NotNull
	private Timestamp estimatedReturnDate;
	private Timestamp actualReturnDate;
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
			name = "order_copy",
			joinColumns = {@JoinColumn(name = "order_num")},
			inverseJoinColumns = {@JoinColumn(name = "copy_id", nullable = false)})
	private Set<Copy> copies = new HashSet<>();
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
