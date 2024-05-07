package app.Repositories;
import app.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByNicknameAndPswrd(String nickname, String pswrd);
        Optional<User> findByNickname(String nickname);
}
