package com.example.hw2;

import org.springframework.web.bind.annotation.*;

@RestController
public class PersonCtr {
    Person person;
    @GetMapping("/person")
    public Person personBasic(
            @RequestParam(value = "name", defaultValue = "wang jinghao")
            String name,
            @RequestParam(value = "age", defaultValue = "18")
            int age,
            @RequestParam(value = "gender", defaultValue = "1")
            boolean gender){
        person = new Person(name, age, gender);
        return person;
    }

    @GetMapping("/person/say_hello")
    public String sayHello() {
        return "Hello, this is " + person.getName() + "\n";
    }

    @RequestMapping(value = "/person/age/set", method = RequestMethod.POST)
    public boolean setAge(
            @RequestParam(value = "age")
            String age) {
        person.setAge(Integer.parseInt(age));
        return true;
    }


    @RequestMapping(value = "/person/gender/set", method = RequestMethod.POST)
    public boolean setGender(
            @RequestParam(value = "gender")
            String gender) {
        person.setGender(Boolean.parseBoolean(gender));
        return true;
    }

    @RequestMapping(value = "/person/name/set", method = RequestMethod.POST)
    public boolean setName(
            @RequestParam(value = "name")
            String name) {
        person.setName(name);
        return true;
    }
}
