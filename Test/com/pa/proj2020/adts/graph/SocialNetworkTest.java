package com.pa.proj2020.adts.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SocialNetworkTest {
    private final SocialNetwork socialNetwork;

    public SocialNetworkTest() {
        socialNetwork = new SocialNetwork();
    }

    @BeforeEach
    void setUp() {
        socialNetwork.initializeData();
    }

    /**
     * Test of initializeData method, of class SocialNetwork.
     */
    @Test
    void initializeData_relationships() {
        int expResult = 365;
        int result = 0;
        for (ArrayList<String> relationshipList : socialNetwork.getRelationShips().values()) {
            result = result + relationshipList.size();
        }
        assertEquals(expResult, result);
    }

    /**
     * Test of initializeData method, of class SocialNetwork.
     */
    @Test
    void initializeData_users() {
        int expResult = 50;
        int result = socialNetwork.getUsers().size();
        assertEquals(expResult, result);
    }


    /**
     * Test of constructModel method, of class SocialNetwork.
     */
    @Test
    void constructModelTotal_numEdges() {
        DirectGraph<User, Relationship> graph = socialNetwork.constructModelTotal();
        int expResult = 365;
        int result = graph.numEdges();
        assertEquals(expResult, result);
    }

    /**
     * Test of constructModel method, of class SocialNetwork.
     */
    @Test
    void constructModelTotal_numVerices() {
        DirectGraph<User, Relationship> graph = socialNetwork.constructModelTotal();
        int expResult = 50;
        int result = graph.numVertices();
        assertEquals(expResult, result);
    }
}