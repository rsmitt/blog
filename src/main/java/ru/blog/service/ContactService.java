package ru.blog.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.blog.entity.Contact;
import ru.blog.repository.ContactRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public Page<Contact> getPaginatedAllContacts(int pageNumber, int pageSize, String sortedBy, String order) {
        if (sortedBy.equalsIgnoreCase("status")) {
            sortedBy = "active";
        }

        Sort sorting = Sort.by(sortedBy);
        Pageable paging = PageRequest.of(--pageNumber, pageSize, order.equals("acs") ? sorting.ascending() : sorting.descending());
        return contactRepository.findAll(paging);
    }

    public void save(Contact contact) {
        contactRepository.save(contact);
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

}
