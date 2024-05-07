package app.Models;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_role_associations")
public class UserRoleAssociation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;
}
