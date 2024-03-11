package ua.petproject;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.petproject.model.categories.Category;
import ua.petproject.controller.ElementController;
import ua.petproject.model.Element;

@SpringBootApplication
@AllArgsConstructor
public class PractProjectApplication implements CommandLineRunner {

	private final ElementController elementController;

	public static void main(String[] args) {
		SpringApplication.run(PractProjectApplication.class, args);
	}

	@Override
	public void run(String... args) {

		Element element1 = new Element(1L, "Element 1", Category.FIRST);
		Element element2 = new Element(2L, "Element 2", Category.SECOND);
		Element element3 = new Element(3L, "Element 3", Category.THIRD);

		elementController.addElement(element1);
		elementController.addElement(element2);
		elementController.addElement(element3);

		Element firstElement = elementController.getElement(1L);
		if (firstElement != null) {
			System.out.println("First Element: " + firstElement);
		}

		Element updatedElement = elementController.updateElement(3L, new Element(3L, "Updated Element 3", Category.SECOND));
		if (updatedElement != null) {
			System.out.println("Updated Element: " + updatedElement);
		}

		elementController.deleteElement(3L);
		System.out.println("Third element deleted.");
	}
}