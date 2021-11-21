package com.vishnevskaia311.controller;

import com.vishnevskaia311.model.Role;
import com.vishnevskaia311.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vishnevskaia311.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) {

        List<User> users = userService.index();
        String username = principal.getName();
        User user = userService.getUserByName(username);
//        Set<Role> allRoles= user.getRoles();
        model.addAttribute("users", users);
        model.addAttribute("user", user);
//        model.addAttribute("roles", allRoles);
        return "admin";
    }

//    @GetMapping("/new")
//    public String addUser(Model model) {
//        model.addAttribute("user", new User());
//        return "admin/new";
//    }
//
//    @PostMapping()
//    public String create(@ModelAttribute("user") User user) {
//        userService.save(user);
//        return "redirect:/admin";
//    }

    @GetMapping("/admin")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "admin";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {

        model.addAttribute("user", userService.show(id));

        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {

        userService.update(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
