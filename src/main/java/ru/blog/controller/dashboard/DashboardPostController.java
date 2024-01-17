package ru.blog.controller.dashboard;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.blog.service.CustomPrincipal;
import ru.blog.service.UserService;
import ru.blog.entity.Post;
import ru.blog.exception.NotFoundItem;
import ru.blog.service.CategoryService;
import ru.blog.service.PostService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
@AllArgsConstructor
public class DashboardPostController {

    private final PostService postService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/posts")
    public String pagingContactsPage(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "size", defaultValue = "3") int pageSize, @RequestParam(defaultValue = "createdAt") String sortBy, @RequestParam(defaultValue = "asc") String order, Model model, @AuthenticationPrincipal CustomPrincipal principal) {
        Page<Post> pages = postService.getPaginatedAllPosts(pageNumber, pageSize, sortBy, order, principal);

        model.addAttribute("title", "Posts");
        model.addAttribute("posts", pages);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);

        return "dashboard/posts/posts";
    }

    @GetMapping("/post/add")
    public String addNewPage(Model model) {
        model.addAttribute("title", "New post");
        model.addAttribute("postForm",new Post());
        model.addAttribute("categories", categoryService.getActivatedCategories());
        return "dashboard/posts/new-post";
    }

    @PostMapping("/post/add")
    public String addNewContact(@Valid @ModelAttribute("postForm") Post post, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "New post");
            model.addAttribute("categories", categoryService.getActivatedCategories());
            return "dashboard/posts/new-post";
        }

        post.setUser(userService.getUserByName(principal.getName()));
        postService.save(post);
        return "redirect:/dashboard/posts";
    }

    @GetMapping("/post/{id}/edit")
    public String editPage(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Edit post");
        model.addAttribute("postForm", postService.getContactById(id).orElseThrow(NotFoundItem::new));
        model.addAttribute("categories", categoryService.getActivatedCategories());
        return "dashboard/posts/edit-post";
    }

    @PostMapping("/post/update")
    public String updatePost(@Valid @ModelAttribute("postForm") Post post, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Edit post");
            return "dashboard/posts/edit-post";
        }

        postService.save(post);
        return "redirect:/dashboard/posts";
    }

    @PostMapping("/post/delete")
    public String deletePost(@RequestParam String id) {
        postService.getContactById(Long.parseLong(id)).orElseThrow(NotFoundItem::new);

        postService.delete(Long.parseLong(id));
        return "redirect:/dashboard/posts";
    }
}
