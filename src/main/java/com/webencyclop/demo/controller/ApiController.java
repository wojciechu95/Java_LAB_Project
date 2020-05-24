package com.webencyclop.demo.controller;

import com.webencyclop.demo.model.Todo;
import com.webencyclop.demo.service.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pwallet")
public class ApiController {

    @Autowired
    private ITodoService todoService;

    @RequestMapping(path="/getall", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Map<String, List<Todo>> getAllTodo(ModelMap model){
        String name = getLoggedInUserName(model);

        Map<String, List<Todo>> assoc = new HashMap<String, List<Todo>>();

        assoc.put("data", todoService.getTodosByUser(name));

        return assoc;
    }

    private String getLoggedInUserName(ModelMap model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }

    @RequestMapping(value = "/getid", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Todo getById(ModelMap model, @RequestParam Long id) {

        Todo todo = todoService.getTodoById(id).get();
        model.put("todo", todo);

        return todo;
    }





    @DeleteMapping("/delete")
    public Map<String, Boolean> delete(@RequestParam Long id) {

        todoService.deleteTodo(id);

        Map<String, Boolean> assoc = new HashMap<String, Boolean>();

        assoc.put("success", true);

        return assoc;
    }
}
