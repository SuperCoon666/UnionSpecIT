package app.Models;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "id_subscriber")
    private User subscriber;
}
