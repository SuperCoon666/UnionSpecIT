package app.Models;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "post_tags_association")
public class PostTagAssociation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_post")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "id_tag")
    private Tag tag;
}
