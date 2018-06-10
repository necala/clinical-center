package drools.spring.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Medicament implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum Category {
        ANTIBIOTICS, ANALGESICS, OTHER
    };
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    private String name;
	private Category category;
	@OneToMany(mappedBy="medicament", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Ingridient> ingridients = new ArrayList<Ingridient>();
	
	public Medicament() {
		super();
	}

	public Medicament(Category category, List<Ingridient> ingridients) {
		super();
		this.category = category;
		this.ingridients = ingridients;
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Ingridient> getIngridients() {
		return ingridients;
	}

	public void setIngridients(List<Ingridient> ingridients) {
		this.ingridients = ingridients;
	}
	
}
