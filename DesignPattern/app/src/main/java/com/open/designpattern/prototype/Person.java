package com.open.designpattern.prototype;

import java.util.ArrayList;

/**
 * Created by vivian on 2017/7/3.
 */

public class Person implements Cloneable {
    private String name;
    private int age;
    private double height;
    private double weight;

    public Person() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    private ArrayList<String> hobbies = new ArrayList<String>();

    public ArrayList<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(ArrayList<String> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }

    @Override
    public Object clone() {
        Person person = null;
        try {
            person = (Person) super.clone();
            person.name = this.name;
            person.weight = this.weight;
            person.height = this.height;
            person.age = this.age;

            person.hobbies = (ArrayList<String>) this.hobbies.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return person;
    }
}
