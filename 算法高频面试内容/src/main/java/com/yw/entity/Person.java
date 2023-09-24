package com.yw.entity;

import java.util.Objects;
import java.util.UUID;

/**
 * @author yangwei
 */
public class Person implements Comparable<Person> {
    public String id;
    public String name;
    public int age;

    public Person(String name, int age) {
        this(UUID.randomUUID().toString(), name, age);
    }
    public Person(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }


    @Override
    public int compareTo(Person o) {
        return this.compareTo(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}