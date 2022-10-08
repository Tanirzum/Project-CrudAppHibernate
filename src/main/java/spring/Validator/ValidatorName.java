package spring.Validator;

import spring.Dao.PeopleDao;
import spring.Model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ValidatorName implements Validator {

    private PeopleDao peopleDao;

    @Autowired
    public ValidatorName(PeopleDao peopleDao) {
        this.peopleDao = peopleDao;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (peopleDao.getName(person.getName()).isPresent())
            errors.rejectValue("name", "", "Таким именем существует человек");
    }
}
