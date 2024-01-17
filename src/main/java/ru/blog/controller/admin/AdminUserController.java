package ru.blog.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.blog.entity.Role;
import ru.blog.entity.User;
import ru.blog.entity.UserPasswordCheck;
import ru.blog.exception.NotFoundItem;
import ru.blog.service.RoleService;
import ru.blog.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/users")
    public String pagingUsersPage(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "size", defaultValue = "3") int pageSize, @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String order, Model model) {
        Page<User> pages = userService.getPaginatedAllUsers(pageNumber, pageSize, sortBy, order);

        model.addAttribute("title", "Users");
        model.addAttribute("users", pages);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);

        return "dashboard/users/users";
    }

    @GetMapping("/user/add")
    public String addNewPage(Model model) {
        model.addAttribute("title", "New user");
        model.addAttribute("userForm",new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "dashboard/users/new-user";
    }

    @PostMapping("/user/add")
    public String addNewContact(@Valid @ModelAttribute("userForm") User user, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "New user");
            model.addAttribute("roles", roleService.getAllRoles());
            return "dashboard/users/new-user";
        }

        user.setPassword(userService.getEncryptedPassword(user.getPassword()));
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/{id}/edit")
    public String editPage(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id).orElseThrow(NotFoundItem::new);

        model.addAttribute("title", "Edit user");
        model.addAttribute("userForm", user);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("userActiveRoles", user.getRoles().stream().map(Role::getId).toArray());
        return "dashboard/users/edit-user";
    }

    @PostMapping("/user/update")
    @Validated({UserPasswordCheck.class})
    public String updatePost(@Valid @ModelAttribute("userForm") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Update user");
            model.addAttribute("roles", roleService.getAllRoles());
            model.addAttribute("userActiveRoles", user.getRoles().stream().map(Role::getId).toArray());
            return "dashboard/users/edit-user";
        }

        userService.save(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.getUserById(id).orElseThrow(NotFoundItem::new);

        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/{id}/change/password")
    public String changePasswordPage(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Change password");
        model.addAttribute("userForm", userService.getUserNameAndPassword(id).orElseThrow(NotFoundItem::new));
        return "dashboard/users/change-password";
    }

}
