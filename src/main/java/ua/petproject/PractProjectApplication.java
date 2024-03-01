package ua.petproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.petproject.categories.Category;
import ua.petproject.model.Element;
import ua.petproject.repository.CategoryDAO;

@SpringBootApplication
public class PractProjectApplication implements CommandLineRunner {

	@Autowired
	private CategoryDAO categoryDAO;

	public static void main(String[] args) {
		SpringApplication.run(PractProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Element element1 = new Element(1L, "Element 1", Category.FIRST);
		Element element2 = new Element(2L, "Element 2", Category.SECOND);
		Element element3 = new Element(3L, "Element 3", Category.THIRD);

		categoryDAO.save(element1);
		categoryDAO.save(element2);
		categoryDAO.save(element3);

		Element firstElement = categoryDAO.findById(1L).orElse(null);
		if (firstElement != null) {
			System.out.println("First Element: " + firstElement);
		}


		Element updatedElement = categoryDAO.findById(2L).orElse(null);
		if (updatedElement != null) {
			updatedElement.setCategory(Category.THIRD);
			categoryDAO.save(updatedElement);
			System.out.println("Updated Element: " + updatedElement);
		}

		categoryDAO.deleteById(3L);
		System.out.println("Third element deleted.");

		System.out.println("All elements:");
		categoryDAO.findAll().forEach(System.out::println);
	}
}
