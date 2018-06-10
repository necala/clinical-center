package drools.spring.example.repository;

import drools.spring.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	public drools.spring.example.model.User findById(Long id);
	public User findByUsername(String username);
}
