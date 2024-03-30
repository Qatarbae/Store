package org.mix.mixer.repository.userrepository;

import org.mix.mixer.entity.appuser.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByFirstname(String username);

    @Modifying
    @Query(value = "DELETE FROM users u WHERE u.email = :email AND u.password = :password", nativeQuery = true)
    void deleteByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT * FROM users u ORDER BY u.firstname ASC LIMIT :count", nativeQuery = true)
    List<User> findFirstNOrderByUsernameAsc(@Param("count") int count);
}