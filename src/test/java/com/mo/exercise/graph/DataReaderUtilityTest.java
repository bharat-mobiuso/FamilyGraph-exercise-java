package com.mo.exercise.graph;

import junit.framework.TestCase;

import java.util.List;

public class DataReaderUtilityTest extends TestCase {

    // validate correct No. of People are Loaded
    public void testGetAllPerson() {
        DataReaderUtility r = new DataReaderUtility();

        List<Person> allPeople = r.getAllPerson();

        assertEquals(12, allPeople.size());
    }

    // validate correct relationships loaded
    public void testGetRelationShipCount() {
        DataReaderUtility r = new DataReaderUtility();

        int bobRelationshipsCount = r.getRelationShipCount("Bob");
        int jennyRelationshipsCount = r.getRelationShipCount("Jenny");
        int nigelRelationshipsCount = r.getRelationShipCount("Nigel");
        int alanRelationshipsCount = r.getRelationShipCount("Alan");

        assertEquals(4, bobRelationshipsCount);
        assertEquals(3, jennyRelationshipsCount);
        assertEquals(2, nigelRelationshipsCount);
        assertEquals(0, alanRelationshipsCount);
    }

    // validate size of extended family count
    public void testGetExtendedFamilyCount() {
        DataReaderUtility r = new DataReaderUtility();

        int bobExtendedFamilyCount = r.getExtendedFamilyCount("Bob");
        int jennyExtendedFamilyCount = r.getExtendedFamilyCount("Jenny");
        
        assertEquals(4, bobExtendedFamilyCount);
        assertEquals(4,jennyExtendedFamilyCount);
    }
}