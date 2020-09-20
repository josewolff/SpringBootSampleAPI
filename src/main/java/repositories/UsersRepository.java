package repositories;
import entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface UsersRepository extends CrudRepository<Users, Integer> {
    Iterable<Users> findByEmail(String email);
}
