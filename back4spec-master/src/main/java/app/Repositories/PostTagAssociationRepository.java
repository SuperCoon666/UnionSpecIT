package app.Repositories;
import app.Models.PostTagAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagAssociationRepository extends JpaRepository<PostTagAssociation, Long> {
}
