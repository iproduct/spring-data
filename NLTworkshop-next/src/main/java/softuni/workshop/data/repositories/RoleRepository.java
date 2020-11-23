package softuni.workshop.data.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.workshop.data.entities.Role;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Integer> {
    Role findByAuthority(String role);
}
