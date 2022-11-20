package com.buaa;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.context.ServiceGroupContext;

public class PersonService {
    String name;
    int age;
    boolean gender;

    public void setName(String name) {
        this.name = name;
        MessageContext mc = MessageContext.getCurrentMessageContext();
        ServiceGroupContext sgc = mc.getServiceGroupContext();
        sgc.setProperty("name", name);
    }

    public PersonService(String name, int age, boolean gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public PersonService() {
        this.name = "wang jinghao";
        this.age = 0;
        this.gender = true;
    }

    public String getName() {
        MessageContext mc = MessageContext.getCurrentMessageContext();
        ServiceGroupContext sgc =  mc.getServiceGroupContext();
        String name = (String) sgc.getProperty("name");
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

    public boolean getGender(boolean gender) {
        return this.gender;
    }

    public String sayHello(){
        //  获得key-value对中的value
        MessageContext mc = MessageContext.getCurrentMessageContext();
        ServiceGroupContext sgc =  mc.getServiceGroupContext();
        String name = (String) sgc.getProperty("name");
        String rs = String.format("web services: hello world ! %s", name);
        return rs;
    }
}
