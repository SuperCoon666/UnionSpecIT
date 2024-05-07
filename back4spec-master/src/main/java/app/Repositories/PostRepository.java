package app.Repositories;
import app.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Методы для запросов, специфичных для постов, например:
    // List<Post> findByUserId(Long userId);
    // List<Post> findByHeadlineContaining(String keyword);
}
