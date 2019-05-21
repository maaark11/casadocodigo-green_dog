package com.marco.casadocodigo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marco.casadocodigo.domain.Order;
import com.marco.casadocodigo.domain.User;
import com.marco.casadocodigo.repository.ItemRepository;
import com.marco.casadocodigo.repository.OrderRepository;
import com.marco.casadocodigo.repository.UserRepository;

@Controller
@RequestMapping("orders/")
public class OrderController {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ItemRepository itemRepository;
	private final String ORDER_URI = "orders/";

	public OrderController(OrderRepository orderRepository, UserRepository userRepository,
			ItemRepository itemRepository) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.itemRepository = itemRepository;
	}

	@GetMapping("/list")
	public ModelAndView list() {
		Iterable<Order> orders = orderRepository.findAll();
		return new ModelAndView(ORDER_URI + "list", "orders", orders);
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") Order order) {
		return new ModelAndView(ORDER_URI + "view", "order", order);
	}

	@GetMapping("/new")
	public ModelAndView newOrder(@ModelAttribute Order order) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("allItems", itemRepository.findAll());
		model.put("allUsers", userRepository.findAll());
		
		return new ModelAndView(ORDER_URI + "form", model);
	}

	@GetMapping(value = "update/{id}")
	public ModelAndView update(@PathVariable("id") Order order) {
		return new ModelAndView(ORDER_URI + "form", "order", order);
	}

	@GetMapping(value = "remove/{id}")
	public ModelAndView remove(@PathVariable("id") Long id, RedirectAttributes redirect) {
		this.orderRepository.deleteById(id);
		return list();
	}

	@PostMapping(params = "form")
	public ModelAndView create(@Valid Order order, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) { return new ModelAndView(ORDER_URI + "new", "formErrors", result.getAllErrors());}
		
		if(order.getId() != null) {
			Optional<Order> changeOrder = orderRepository.findById(order.getId());
			Optional<User> user = userRepository.findById(order.getUser().getId());
			
			changeOrder.get().setItens(order.getItens());
			changeOrder.get().setDate(order.getDate());

			user.get().getOrders().remove(changeOrder.get().getId());
			user.get().getOrders().add(changeOrder.get());
			
			this.userRepository.save(user.get());
		}
		else {
			this.orderRepository.save(order);
			this.userRepository.save(order.getUser());
		}
		redirect.addFlashAttribute("globalMessage", "Pedido gravado	com	sucesso");
		return new ModelAndView("redirect:/" + ORDER_URI + "{item.id}", "item.id", order.getId());
	}

}
