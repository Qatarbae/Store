package org.mix.mixer.entity.appuser;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.mix.mixer.course.entity.Course;
import org.mix.mixer.entity.course.CourseCreator;
import org.mix.mixer.entity.token.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private  Long id;

    private String firstname;

    private String lastname;

    @Nonnull
    @Column(name = "email", unique = true)
    @EqualsAndHashCode.Include
    private String email;


    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany
    @JoinTable(
            name = "user_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profiles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CourseCreator courseCreator;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
