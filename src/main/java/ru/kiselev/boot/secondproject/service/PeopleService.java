package ru.kiselev.boot.secondproject.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kiselev.boot.secondproject.model.Book;
import ru.kiselev.boot.secondproject.model.Person;
import ru.kiselev.boot.secondproject.repository.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setPerson_id(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> getPersonByName(String name) {
        return peopleRepository.findByFull_name(name);
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            person.get().getBooks().forEach(book -> {
                long diffInMillies = Math.abs(book.getTaken_at().getTime() - new Date().getTime());
                //864000000 - 10 суток
                if (diffInMillies > 864000000)
                    book.setExpired(true);
            });
            return person.get().getBooks();
        } else
            return Collections.emptyList();
    }
}