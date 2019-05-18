package com.marco.casadocodigo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="\"USER\"")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Length(min = 2, max = 300, message = "Name size must be between {min} and {max} characters")
	private String name;

	@NotNull
	@Length(min = 2, max = 300, message = "Address size must be between {min} and {max} characters")
	private String address;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private List<Order> orders;

	public User() {
	}

	public User(Long id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void newOrder(Order order) {

		if (this.orders == null)
			orders = new ArrayList<Order>();

		orders.add(order);

	}

}
