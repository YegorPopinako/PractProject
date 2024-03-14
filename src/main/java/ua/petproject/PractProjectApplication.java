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


		/*
		Element element1 = new Element("Element 1", Category.FIRST);
		Element element2 = new Element("Element 2", Category.SECOND);
		Element element3 = new Element("Element 3", Category.THIRD);

		elementController.add(element1);
		elementController.add(element2);
		elementController.add(element3);

		Element firstElement = elementController.get(1L);
		if (firstElement != null) {
			System.out.println("First Element: " + firstElement);
		}

		Element updatedElement = elementController.update(1L, new Element("Updated Element 3", Category.SECOND));
		if (updatedElement != null) {
			System.out.println("Updated Element: " + updatedElement);
		}

		elementController.delete(3L);
		System.out.println("Third element deleted.");
		*/
	}
}