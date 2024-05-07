package app.Models;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@Table(name = "user") // Указываем имя таблицы, если оно отличается от имени класса
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nickname")
    @ToString.Include
    private String nickname;

    @Column(name = "pswrd")
    private String pswrd;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Исключаем из toString
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Исключаем из toString
    private List<Comment> comments;

//    @ManyToMany
//    @JoinTable(
//            name = "user_role_associations", // Имя таблицы связи
//            joinColumns = @JoinColumn(name = "user_id"), // Столбец в этой таблице
//            inverseJoinColumns = @JoinColumn(name = "role_id") // Столбец в таблице role
//    )
//    private List<Role> roles; // Роли пользователя
}


//package app.Models;
//        import jakarta.persistence.Entity;
//        import jakarta.persistence.GeneratedValue;
//        import jakarta.persistence.GenerationType;
//        import jakarta.persistence.Id;
//        import lombok.Data;
//@Entity
//@Data
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String nickname;
//    private String pswrd;
//}
