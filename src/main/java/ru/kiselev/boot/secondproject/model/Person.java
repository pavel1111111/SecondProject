package ru.kiselev.boot.secondproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer person_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be from 2 to 100 characters long")
    private String full_name;


    @Min(value = 0)
    private int age;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(String full_name, int age) {
        this.full_name = full_name;
        this.age = age;
    }
}
