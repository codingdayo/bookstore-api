package codingdayo.bookstoreapi.repository;

import codingdayo.bookstoreapi.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<OurUsers, Long> {

    Optional<OurUsers> findByEmail(String email);
}
