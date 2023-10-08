package hexlet.code.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static hexlet.code.config.SecurityConfig.DEFAULT_AUTHORITIES;
import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 100)
    @JsonIgnore
    private String password;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    private Date createdAt;

    public User(final Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return DEFAULT_AUTHORITIES;
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
