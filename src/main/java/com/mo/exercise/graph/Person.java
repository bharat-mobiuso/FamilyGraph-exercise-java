package com.mo.exercise.graph;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private String email;
    private int age;
    boolean visited = false;
    private List<Relatives> relations = new ArrayList<>();

    public Person(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public int getRelationShipCount() {
        return this.relations.size();
    }

    public void addRelationShip(Person nextPerson, String relationShip) {
        if (this.getRelationShipCount() > 0) {
            this.getRelations().forEach(rel -> {
                // if user already in the relationship list then don't add the user in the relations
                if (rel.getPerson().getEmail().equals(nextPerson.getEmail())) {
                    return;
                }
            });
        }
        Relatives newRelationShip = new Relatives(relationShip, nextPerson);
        this.relations.add(newRelationShip);
        // Relation is bi-directional so add this to the relation of person
        newRelationShip = new Relatives(relationShip, this);
        nextPerson.getRelations().add(newRelationShip);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public List<Relatives> getRelations() {
        return relations;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return "name: " + this.getName() + " email: " + this.getEmail() + " age: " + this.getAge();
    }
}

