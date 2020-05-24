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

    @DeleteMapping("/delete")
    public Map<String, Boolean> delete(@RequestParam Long id) {

        todoService.deleteTodo(id);

        Map<String, Boolean> assoc = new HashMap<String, Boolean>();

        assoc.put("success", true);

        return assoc;
    }


//    @RequestMapping(value = "/delete-todo", method = RequestMethod.DELETE)
//    public String deleteTodo(@RequestParam long id) {
//        todoService.deleteTodo(id);
//
//        return "redirect:/pwallet";
//    }

//
//
//    // GET: Wallet/Delete/5
//        [HttpDelete]
//    public async Task<IActionResult> Delete(int id)
//    {
//        var PasswdModelFromDb = await _db.PWallet.FirstOrDefaultAsync(u => u.ID == id);
//        if (PasswdModelFromDb == null)
//        {
//            return Json(new { success = false, message = "Błąd w trakcie usuwania" });
//        }
//        _db.PWallet.Remove(PasswdModelFromDb);
//        await _db.SaveChangesAsync();
//        return Json(new { success = true, message = "Usunieto z Powodzeniem" });
//    }

}
