package org.example.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.api.Person;

@DubboService
public class PersonImpl implements Person {
    String name;
    int age;
    boolean gender;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean getGender() {
        return gender;
    }

    @Override
    public String sayHello() {
        return (" RPC Service: Hello world!" + this.name);
    }
}
