package spring.Controller;

import spring.Dao.PeopleDao;
import spring.Model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {


    private PeopleDao peopleDao;

    @Autowired
    public PeopleController(PeopleDao peopleDao) {
        this.peopleDao = peopleDao;
    }


    @GetMapping
    public String listPeople(Model model) {
        model.addAttribute("listPeople", peopleDao.list());
        return "ListPeople";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model) {
        model.addAttribute("indexPeople", peopleDao.index(id));
        return "index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "new";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "/people/new";

        peopleDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String updatePerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("edit", peopleDao.index(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String editPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "/people/{id}/edit";

        peopleDao.update(person, id);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleDao.delete(id);
        return "redirect:/people";
    }
}
