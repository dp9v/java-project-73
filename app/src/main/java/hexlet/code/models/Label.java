package hexlet.code.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Set;

import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "labels")
public class Label {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "labels")
    @JsonIgnore
    private Set<Task> tasks;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    private Date createdAt;
}
