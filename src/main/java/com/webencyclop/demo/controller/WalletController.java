package com.webencyclop.demo.controller;

import com.webencyclop.demo.model.Todo;
import com.webencyclop.demo.service.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pwallet")
public class WalletController {

    @Autowired
    private ITodoService todoService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getAll(ModelMap model) {

        String name = getLoggedInUserName(model);
        model.put("todos", todoService.getTodosByUser(name));

        model.addAttribute("page", "../templates/pwallet");
        model.addAttribute("title", "PWallet");

        return "../shared/_Layout";
    }

    private String getLoggedInUserName(ModelMap model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String showAddTodoPage(ModelMap model) {

        model.addAttribute("todo", new Todo());
        model.addAttribute("page", "../templates/todo");
        model.addAttribute("title", "PWallet");

        return "../shared/_Layout";
    }

    @RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
    public String deleteTodo(@RequestParam long id) {
        todoService.deleteTodo(id);

        return "redirect:/pwallet";
    }

    @RequestMapping(value = "/update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam long id, ModelMap model) {
        Todo todo = todoService.getTodoById(id).get();

        model.put("todo", todo);
        model.addAttribute("page", "../templates/todo");
        model.addAttribute("title", "PWallet");

        return "../shared/_Layout";
    }

    @RequestMapping(value = "/update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

        if (result.hasErrors()) {

            model.addAttribute("page", "../templates/todo");
            model.addAttribute("title", "PWallet");

            return "../shared/_Layout";
        }

        todo.setUserName(getLoggedInUserName(model));
        todoService.updateTodo(todo);

        model.addAttribute("page", "../templates/pwallet");
        model.addAttribute("title", "PWallet");

        return "redirect:/pwallet";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodo(ModelMap model, @ModelAttribute ("todo") Todo todo, BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("page", "../templates/todo");
            model.addAttribute("title", "PWallet");

            return "../shared/_Layout";
        }

        todo.setUserName(getLoggedInUserName(model));

        todoService.saveTodo(todo);

        model.addAttribute("page", "../templates/pwallet");
        model.addAttribute("title", "PWallet");

        return "redirect:/pwallet";
    }
}
