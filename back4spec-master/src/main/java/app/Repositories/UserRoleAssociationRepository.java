package app.Repositories;
import app.Models.UserRoleAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleAssociationRepository extends JpaRepository<UserRoleAssociation, Long> {
}
