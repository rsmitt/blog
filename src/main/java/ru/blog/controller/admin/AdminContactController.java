package ru.blog.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.blog.entity.Contact;
import ru.blog.exception.NotFoundItem;
import ru.blog.service.ContactService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminContactController {

    private final ContactService contactService;

    @GetMapping("/contacts")
    public String pagingContactsPage(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "size", defaultValue = "3") int pageSize, @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String order, Model model) {
        Page<Contact> pages = contactService.getPaginatedAllContacts(pageNumber, pageSize, sortBy, order);

        model.addAttribute("title", "Contacts");
        model.addAttribute("contacts", pages);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);

        return "dashboard/admin/contacts";
    }


    @GetMapping("/contact/add")
    public String addNewPage(Model model) {
        model.addAttribute("title", "New contact");
        model.addAttribute("contactForm",new Contact());
        return "dashboard/admin/new-contact";
    }

    @PostMapping("/contact/add")
    public String addNewContact(@Valid @ModelAttribute("contactForm") Contact contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "dashboard/admin/new-contact";
        }

        contactService.save(contact);
        return "redirect:/admin/contacts";
    }

    @GetMapping("/contact/{id}/edit")
    public String editPage(@PathVariable String id, Model model) {
        model.addAttribute("title", "Edit contact");
        model.addAttribute("contactForm", contactService.getContactById(Long.parseLong(id)).orElseThrow(NotFoundItem::new));
        return "dashboard/admin/edit-contact";
    }

    @PostMapping("/contact/update")
    public String updateContact(@Valid @ModelAttribute("contactForm") Contact contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "dashboard/admin/edit-contact";
        }

        contactService.save(contact);
        return "redirect:/admin/contacts";
    }

    @PostMapping("/contact/delete")
    public String deleteContact(@RequestParam String id) {
        contactService.getContactById(Long.parseLong(id)).orElseThrow(NotFoundItem::new);

        contactService.delete(Long.parseLong(id));
        return "redirect:/admin/contacts";
    }
}
