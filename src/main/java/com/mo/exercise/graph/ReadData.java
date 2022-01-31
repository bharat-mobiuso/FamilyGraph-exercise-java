package com.mo.exercise.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ReadData {

    public List<Person> getAllPerson() {
        List<Person> peopleList = new ArrayList<>();
        try (FileReader fr = new FileReader("src/test/resources/people.csv");
             BufferedReader br = new BufferedReader(fr);) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                String[] peoples = line.split(",");
                Person p = new Person(peoples[0], peoples[1], Integer.valueOf(peoples[2]));
                peopleList.add(p);
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return peopleList;
    }
    public List<Relatives> getAllPeopleRelationShip(List<Person> listPeople) {
        List<Relatives> relationshipsList = new ArrayList<>();
        try (FileReader fr = new FileReader("src/test/resources/relationships.csv");
             BufferedReader br = new BufferedReader(fr);) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue; // Skip blank lines
                }
                String[] rr = line.split(",");
                Person currentPerson = getPersonByEmail(listPeople, rr[0]);
                Person nextPerson = getPersonByEmail(listPeople, rr[2]);
                if (null != nextPerson && null != currentPerson) {
                    currentPerson.addRelationShip(nextPerson, rr[1]);
                }
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return relationshipsList;
    }
    private Person getPersonByEmail(List<Person> peopleList, String email) {
        List<Person> listPeople = peopleList.stream().filter(people -> people.getEmail().equals(email))
                .collect(Collectors.toList());
        if (null != listPeople && !listPeople.isEmpty()) {
            return listPeople.get(0);
        }
        return null;
    }
    public List<Relatives> getRelativesList(String name) {
        List<Person> personList = getAllPerson();

        getAllPeopleRelationShip(personList);
        // should be based on the unique key as email
        List<Person> peoples = personList.stream().filter(people -> people.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
        if (null == peoples || peoples.size() > 1 || peoples.isEmpty()) {
            return null;
        }
        return peoples.get(0).getRelations();
    }

    public List<String> getRelatives(Person person){
        List<Relatives> relativesList = this.getRelativesList(person.getName());
        List<String> relatives = new ArrayList<>();
        relativesList.forEach(rel -> {
            relatives.add(rel.getPerson().getName() + " " + rel.getRelationShip());
        });
        return relatives;
    }

    public int getRelationShipCount(String name) {
        List<Person> peopleList = getAllPerson();

        getAllPeopleRelationShip(peopleList);
        // should be based on the unique key as email
        List<Person> peoples = peopleList.stream().filter(people -> people.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
        if (null == peoples || peoples.size() > 1 || peoples.isEmpty()) {
            return 0;
        }
        return peoples.get(0).getRelationShipCount();
    }

    public int getExtendedFamilyCount(String name) {
        List<Person> peopleList = getAllPerson();
        getAllPeopleRelationShip(peopleList);

        Stack<Person> stack = new Stack<>();
        Person start = peopleList.stream().filter(people -> people.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList()).get(0);
        if (null != start) {
            stack.push(start);
            start.setVisited(true);
        }
        int extendedFamilyCount = 0;
        while (!stack.isEmpty()) {
            Person popped = stack.pop();
            extendedFamilyCount++;
            popped.getRelations().forEach(rel -> {
                if (!rel.getPerson().isVisited() && rel.getRelationShip().equals("FAMILY")) {
                    stack.push(rel.getPerson());
                    rel.getPerson().setVisited(true);
                }
            });
        }
        return extendedFamilyCount;
    }


    public static void main(String[] args) {

        ReadData r = new ReadData();
        List<Person> personList = r.getAllPerson();

        // print person and it's relations
        System.out.println("\nPerson and it's Relations");
        personList.forEach(person -> {
            List<String> relatives = r.getRelatives(person);
            System.out.println(person.getName() + " : " + r.getRelationShipCount(person.getName()) + " " + relatives);
        });

        // print extended family counts
        System.out.println("\nExtended Family Counts");
        personList.forEach(person ->{
            System.out.println(person.getName() + " : " + r.getExtendedFamilyCount(person.getName()));
        });
    }
}