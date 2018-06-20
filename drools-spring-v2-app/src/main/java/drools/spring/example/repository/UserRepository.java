package drools.spring.example.repository;

import drools.spring.example.model.User;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findById(Long id);
	public User findByUsername(String username);
	public ArrayList<User> findByCategory(User.Category category);
}
