package ru.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.blog.repository.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ru.blog.entity.User userDetails = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found user"));

        return CustomPrincipal.builder()
                .id(userDetails.getId())
                .username(userDetails.getName())
                .email(email)
                .password(userDetails.getPassword())
                .authorities(userDetails.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet()))
                .build();

    }

    public ru.blog.entity.User getUserByName(String name) {
        return userRepository.getUserByName(name);
    }

    public Page<ru.blog.entity.User> getPaginatedAllUsers(int pageNumber, int pageSize, String sortedBy, String order) {
        Sort sorting = Sort.by(sortedBy);
        Pageable paging = PageRequest.of(--pageNumber, pageSize, order.equals("acs") ? sorting.ascending() : sorting.descending());
        return userRepository.findAll(paging);
    }

    public void save(ru.blog.entity.User user) {
        userRepository.save(user);
    }

    public Optional<ru.blog.entity.User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public String getEncryptedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Optional<ru.blog.entity.User> getUserNameAndPassword(Long id) {
        return userRepository.findUserById(id);
    }
}
