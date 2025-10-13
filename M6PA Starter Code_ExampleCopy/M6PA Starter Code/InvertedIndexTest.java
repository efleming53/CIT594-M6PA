import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.*;

public class InvertedIndexTest {
	
	InvertedIndex index;
	
    @BeforeAll
    public static void beforeAll() throws Exception{
        // DO NOT remove this method
        // This method is run once before all the test methods in this class
        // You can use this method to set up any common data needed for the tests
    }

    @BeforeEach
    public void beforeEach() throws Exception{
        // DO NOT remove this method
        // This method is run once before all the test methods in this class
        // You can use this method to set up any common data needed for the tests
    	
    	index = new InvertedIndex(); // create index to use for each test method
    	
    }

    @Test
    public void testAddDocument(){
        // Add your test cases for the addDocument method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
        index.addDocument(1, "This is a test document.");
        index.addDocument(2, "This is a test document too.");
        Map<String, Set<Integer>> map = index.getIndex();
        
        assertTrue(map.get("this").contains(1), "Map does not contain documentID '1' for 'This'"); // test node for 'this' contains documentID 1
        assertTrue(map.get("this").contains(2), "Map does not contain documentID '2' for 'This'"); // test node for 'this' contains documentID 1
        assertTrue(map.containsKey("document"), "Map does not contain key 'document'"); // test map contains key (node) 'document'
        assertFalse(map.containsKey("too"), "Map should not contain stopword 'too'"); // test if stopword was not included
    }

    @Test
    public void testSearch(){
        // Add your test cases for the search method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
        index.addDocument(1, "dog cat");
        index.addDocument(2, "dog bird");
        
        assertEquals(Set.of(1), index.search("dog cat"), "Searching 'dog cat' does not return ID 1"); // test searching "dog cat" returns just ID '1'
        assertEquals(Set.of(1, 2), index.search("dog"), "Searching 'dog' does not return IDs 1 AND 2"); // test searching "dog returns IDs 1 and 2
        assertTrue(index.search("snake").isEmpty(), "Searching 'snake' does not return empty set"); // test searching non-existent word returns empty set
    }

    @Test
    public void testRemoveDocument(){
        // Add your test cases for the removeDocument method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
        index.addDocument(1, "dog cat");
        index.addDocument(2, "cat");
        index.removeDocument(1);
        
        Map<String, Set<Integer>> map = index.getIndex();    
        assertEquals(Set.of(2), map.get("cat"), "Map should only contain document ID '2' for 'cat'"); // test only returning documentID that contains cat after removeDocumnt
        assertFalse(map.get("cat").contains(1), "'cat' should not contain documentID '1' after removeDocument"); // test document properly removed
        assertTrue(map.get("dog").isEmpty(), "'dog' should return empty set after removing only document it is found in"); // test 'dog' no longer found after only document it is in removed
        }

    @Test
    public void testGetIndex(){
        // Add your test cases for the getIndex method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
        index.addDocument(1, "dog cat bird");
        index.addDocument(1, "cat");
        Map<String, Set<Integer>> map = index.getIndex();
        
        assertEquals(3, map.size(), "Index should contain 3 words"); // test size of index
        
        assertTrue(map.containsKey("cat"), "Map should contain key 'cat'"); // test map contains key 'cat'
        assertEquals(Set.of(1, 2), map.get("cat"), "'cat' should return documentIDs 1 and 2"); // test 'cat' returns IDs 1 and 2
        
        assertTrue(map.containsKey("dog"), "Map should contain key 'dog'"); // test map contains key 'dog'
        assertEquals(Set.of(1), map.get("dog"), "'dog' should return documentIDs 1"); // test 'dog' returns IDs 1
        
        assertTrue(map.containsKey("bird"), "Map should contain key 'bird'"); // test map contains key 'bird'
        assertEquals(Set.of(1), map.get("bird"), "'bird' should return documentIDs 1"); // test 'bird' returns IDs 1
        
    }

    /*
    For Extra Credit, is OPTIONAL
     */
    @Test
    public void testSearchWithTFIDF(){
        // Add your test cases for the searchWithTFIDF method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
        assertEquals(1, 1, "This is a placeholder test case. Replace with actual test cases for searchWithTFIDF.");
    }
}
