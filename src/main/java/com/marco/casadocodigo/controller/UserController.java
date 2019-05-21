package com.marco.casadocodigo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marco.casadocodigo.domain.User;
import com.marco.casadocodigo.repository.UserRepository;

@Controller
@RequestMapping("users/")
public class UserController {

	private final UserRepository repository;
	private final String USER_URI = "users/";

	public UserController(UserRepository userRepository) {
		this.repository = userRepository;
	}

	@GetMapping("/list")
	public ModelAndView list() {
		Iterable<User> users = this.repository.findAll();
		return new ModelAndView(USER_URI + "list", "users", users);
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") User user) {
		return new ModelAndView(USER_URI + "view", "user", user);
	}

	@GetMapping("/new")
	public String newUser(@ModelAttribute User user) {
		return USER_URI + "new";
	}

	@GetMapping(value = "update/{id}")
	public ModelAndView update(@PathVariable("id") User user) {
		return new ModelAndView(USER_URI + "form", "user", user);
	}

	@GetMapping(value = "remove/{id}")
	public ModelAndView remove(@PathVariable("id") Long id, RedirectAttributes redirect) {
		this.repository.deleteById(id);
		return list();
	}

	@PostMapping(params = "form")
	public ModelAndView create(@Valid User user, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView(USER_URI + "new", "formErrors", result.getAllErrors());
		}
		user = this.repository.save(user);
		redirect.addFlashAttribute("globalMessage", "Cliente gravado com sucesso");
		return new ModelAndView("redirect:/" + USER_URI + "{cliente.id}", "cliente.id", user.getId());
	}

}
