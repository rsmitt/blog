package ru.blog.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.blog.entity.Post;
import ru.blog.repository.PostRepository;

import java.util.Optional;


@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> getPaginatedAllPosts(int pageNumber, int pageSize, String sortedBy, String order, CustomPrincipal principal) {
        if (sortedBy.equalsIgnoreCase("status")) {
            sortedBy = "active";
        }

        Sort sorting = Sort.by(sortedBy);
        Pageable paging = PageRequest.of(--pageNumber, pageSize, order.equals("acs") ? sorting.ascending() : sorting.descending());

        return principal.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))
                ? postRepository.findAll(paging) : postRepository.findAllByUserId(principal.getId(), paging);
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public Optional<Post> getContactById(Long id) {
        return postRepository.findById(id);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
