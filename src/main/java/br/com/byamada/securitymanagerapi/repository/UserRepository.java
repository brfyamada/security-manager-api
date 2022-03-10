//[SPRING SECURITY] [STEP 3] Creating a Repository to manage users data
package br.com.byamada.securitymanagerapi.repository;

import br.com.byamada.securitymanagerapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
