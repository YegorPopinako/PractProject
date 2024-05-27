package ua.petproject.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.petproject.models.enums.BookCategory;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    private String authorName;

    @NotBlank
    @NotNull
    private String publishingHouseName;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String name;

    @Column(nullable = false)
    @Enumerated(value = jakarta.persistence.EnumType.STRING)
    private BookCategory bookCategory;

    @ManyToOne
    @JoinColumn(name = "publishing_house_id")
    private PublishingHouse publishingHouse;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @NotNull
    @NotBlank
    @Column(name = "photourl")
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity user;

    public Book(String name, BookCategory bookCategory, String authorName, String publishingHouseName, String photoUrl) {
        this.name = name;
        this.bookCategory = bookCategory;
        this.authorName = authorName;
        this.publishingHouseName = publishingHouseName;
        this.photoUrl = photoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(name, book.name) && bookCategory == book.bookCategory &&
                Objects.equals(publishingHouse, book.publishingHouse) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, bookCategory, publishingHouse, author);
    }
}
