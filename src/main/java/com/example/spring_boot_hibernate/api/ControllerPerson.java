package com.example.spring_boot_hibernate.api;

import com.example.spring_boot_hibernate.dao.Persondao;
import com.example.spring_boot_hibernate.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spring-orm")
public class ControllerPerson {
    @Autowired
    private Persondao dao;
    @PostMapping("/savePerson")
    public String save(@RequestBody Person person){
        dao.savePerson(person);
        return "add succeed";
    }

    @GetMapping("/getPersons")

    public List<Person> getPersons(){
        return dao.getPersons();
    }
    @PutMapping(value="updating/{id}")
    public String udpatePerson(@RequestBody Person p,@PathVariable("id") int id){
        dao.udpatePerson(p,id);
        return "update succeed";
    }
    @DeleteMapping(value="/deleting/{id}")
    public String deletePerson(@PathVariable("id") int id){
        dao.deletePerson(id);
        return "remove succeed";
    }
}
