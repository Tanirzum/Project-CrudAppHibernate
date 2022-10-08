package spring.Dao;

import spring.Model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class PeopleDao {

    private JdbcTemplate jdbcTemplate;
    private SessionFactory sessionFactory;

    @Autowired
    public PeopleDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Person> list() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p from Person p").getResultList();
    }

    @Transactional
    public Person index(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(Person person, int id) {
        Session session = sessionFactory.getCurrentSession();
        Person update = session.get(Person.class, id);

        update.setName(person.getName());
        update.setEmail(person.getEmail());
        update.setLastName(person.getLastName());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Person.class, id));
    }

//    Для Валидации name
    public Optional<Person> getName(String name) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE name = ?",
                new Object[]{name}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
