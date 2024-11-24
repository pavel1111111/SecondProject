package ru.kiselev.boot.secondproject.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 100, message = "Book's title should be 2 to 100 char long")
    private String title;

    @Size(min = 2, max = 100, message = "Author's name should be 2 to 100 char long")
    private String author;

    @Min(value=1500, message = "Year must be over 1500")
    private int year;

    @ManyToOne
    @JoinColumn(name="person_id", referencedColumnName = "person_id")
    private Person owner;

    private Date taken_at;

    @Transient
    private boolean expired;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }
}
