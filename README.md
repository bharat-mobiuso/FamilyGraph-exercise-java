# FamilyGraph-exercise-java
 Building a graph of people, and their relationships with each other

For Detail Explanation of Problem please refer pdf attached [click Here](https://github.com/bharat-mobiuso/FamilyGraph-exercise-java/blob/main/README.pdf)

> Exercise is completed in `Java` only, other language are used in `Gradle` in `IntelliJ`

> [![N|Solid](https://github.com/bharat-mobiuso/FamilyGraph-exercise-java/blob/main/intelliJ.svg)](https://www.jetbrains.com/idea/download/#section=windows)
## Exercise 1
>
>*Please implement code and data structures that read the files:*
>- *`src/test/java/resources/people.csv`*
>- *`src/test/java/resources/relationships.csv`*
>
>*and use them to build an in-memory data structure that represents the people in the file and their relationships with each other.*

Created necessary Java classes as followed
>
>*`Person.java` for storing Person's Details*
>
>*`Relations.java` for Mapping Person's relations with others*
>
>*`ReadData.java` for Reading Data and Process on it*

You can find these files at `src/main/java/com/mo/exercise/graph/`

```sh
public List<Person> getAllPerson() {
    List<Person> peopleList = new ArrayList<>();
    try (FileReader fr = new FileReader("src/test/resources/people.csv");
         BufferedReader br = new BufferedReader(fr);) {
         
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
```
```sh
public void getAllPeopleRelationShip(List<Person> listPeople) {
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
}
```
## Exercise 2 
>*Validate correct people loaded*
*Write a test to validate that you have loaded the expected number of people.*

>-  `Note` *if you don't know how to generate test cases please refer [https://www.jetbrains.com/help/idea/create-tests.html](https://www.jetbrains.com/help/idea/create-tests.html)*

You will find Test files at `src/test/java/com/mo/exercise/graph/ReadDataTest.java`

```sh
public void testGetAllPerson() {
    ReadData r = new ReadData();

    List<Person> allPeople = r.getAllPerson();

    assertEquals(12, allPeople.size());
}
```

## Exercise 3 - Validate correct relationships loaded
>*Write a test to validate that the following people have the correct expected number of connections to other people*
>
>- *Bob (4 relationships)*
>- *Jenny (3 relationships)*
>- *Nigel (2 relationships)*
>- *Alan (0 relationships)*

```sh
public void testGetRelationShipCount() {
    ReadData r = new ReadData();

    int bobRelationshipsCount = r.getRelationShipCount("Bob");
    int jennyRelationshipsCount = r.getRelationShipCount("Jenny");
    int nigelRelationshipsCount = r.getRelationShipCount("Nigel");
    int alanRelationshipsCount = r.getRelationShipCount("Alan");

    assertEquals(4, bobRelationshipsCount);
    assertEquals(3, jennyRelationshipsCount);
    assertEquals(2, nigelRelationshipsCount);
    assertEquals(0, alanRelationshipsCount);
}
```

## Exercise 4 - Write a method that calculates the size of the extended family
>*Write a method which, when passed the object representing a particular person, returns an int representing the size of their extended family including themselves. Their extended family includes anyone connected to them by a chain of family relationships of any length, so your solution will need to work for arbitrarily deep extended families. It **_should not count their friends_**. Write tests that validate this returns the correct result for the families of:*
>
>- *Jenny (4 family members)*
>- *Bob (4 family members)*

```sh
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
```

>*for testing the size of the extended family we can create test case `testGetExtendedFamilyCount()`*

```sh
public void testGetExtendedFamilyCount() {
    ReadData r = new ReadData();

    int bobExtendedFamilyCount = r.getExtendedFamilyCount("Bob");
    int jennyExtendedFamilyCount = r.getExtendedFamilyCount("Jenny");

    assertEquals(4, bobExtendedFamilyCount);
    assertEquals(4, jennyExtendedFamilyCount);
}
```
