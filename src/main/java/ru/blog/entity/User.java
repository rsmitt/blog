package ru.blog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class User {

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Please fill out this field")
    @Column(length = 25)
    private String name;
    @NotEmpty(message = "Please fill out this field", groups = UserPasswordCheck.class)
    @Size(min = 6, groups = UserPasswordCheck.class)
    @Column(updatable = false)
    private String password;
    @NotEmpty(message = "Please fill out this field")
    @Email
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @NotNull(message = "Please, select a role")
    private Set<Role> roles = new HashSet<>();

    private boolean active;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date modifiedAt;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        posts.forEach(post -> post.setUser(this));

    }

}
