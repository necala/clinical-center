package drools.spring.example.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.model.Illness;

public interface IllnessRepository extends JpaRepository<Illness, Long> {
	public Illness findById(Long id);
	public Illness findByName(String name);
	public ArrayList<Illness> findAll();
}
