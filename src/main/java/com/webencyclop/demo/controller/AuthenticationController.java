package com.webencyclop.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.webencyclop.demo.model.User;
import com.webencyclop.demo.service.UserService;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	UserService userService;

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView login(Model model) {

		ModelAndView modelAndView = new ModelAndView();

		model.addAttribute("page", "../templates/login");
		model.addAttribute("title", "Login");

		modelAndView.setViewName("../shared/_Layout");

		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(Model model) {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);

		model.addAttribute("page", "../templates/register");
		model.addAttribute("title", "Register");

		modelAndView.setViewName("../shared/_Layout");

		return modelAndView;
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(Model model, Locale locale) {
		ModelAndView modelAndView = new ModelAndView();
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		model.addAttribute("page", "../templates/home");
		model.addAttribute("title", "home");

		modelAndView.setViewName("../shared/_Layout");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminHome(Model model) {
		ModelAndView modelAndView = new ModelAndView();
		model.addAttribute("page", "../templates/admin");
		model.addAttribute("title", "Admin");

		modelAndView.setViewName("../shared/_Layout");
		return modelAndView;
	}

	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		// Check for the validations
		if(bindingResult.hasErrors()) {
			modelAndView.addObject("successMessage", "Please correct the errors in form!");
			modelMap.addAttribute("bindingResult", bindingResult);
		}
		else if(userService.isUserAlreadyPresent(user)){
			modelAndView.addObject("successMessage", "user already exists!");			
		}
		// we will save the user if, no binding errors
		else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User is registered successfully!");
		}
		modelAndView.addObject("user", new User());

		model.addAttribute("page", "../templates/register");
		model.addAttribute("title", "Register");

		modelAndView.setViewName("../shared/_Layout");
		return modelAndView;
	}
}










