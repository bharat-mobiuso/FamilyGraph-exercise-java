package com.mo.exercise.graph;


public class Relatives {
    private String relationShip;
    private Person person;

    public Relatives(String relationShip, Person person) {
        this.relationShip = relationShip;
        this.person = person;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public Person getPerson() {
        return person;
    }

}

