package app.Models;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "headline")
    @ToString.Include
    private String headline;

    @Column(name = "content")
    @ToString.Include
    private String content;

    @Column(name = "url")
    @ToString.Include
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_created")
    @ToString.Exclude // Исключаем из toString
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Исключаем из toString
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "post_tags_association",
            joinColumns = @JoinColumn(name = "id_post"),
            inverseJoinColumns = @JoinColumn(name = "id_tag")
    )
    @ToString.Exclude // Теги также могут быть исключены, если они ведут к рекурсии
    private List<Tag> tags;
}