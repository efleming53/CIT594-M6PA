/*
 * I attest that the code in this file is entirely my own except for the starter
 * code provided with the assignment and the following exceptions:
 * <Enter all external resources and collaborations here. Note external code may
 * reduce your score but appropriate citation is required to avoid academic
 * integrity violations. Please see the Course Syllabus as well as the
 * university code of academic integrity:
 *  https://catalog.upenn.edu/pennbook/code-of-academic-integrity/ >
 * Signed,
 * Author: YOUR NAME HERE
 * Penn email: <YOUR-EMAIL-HERE@seas.upenn.edu>
 * Date: YYYY-MM-DD
 */

//import any classes you will need
import java.util.*;

public class InvertedIndex {

    // Root of the BST
    private BSTNode root;

    // define a private static inner class that represents a node in the BST
    private static class BSTNode{
        // keyWord that is indexed
        String keyWord;
        // set of IDs where the keyWord appears
        Set<Integer> documentIDs;
        // the left node stores keywords less than this node's keyword
        // the right node stores keywords greater than this node's keyword
        BSTNode left, right;

        // constructor to initialize each node
        BSTNode(String keyWord, int docID){
            this.keyWord = keyWord;
            this.documentIDs = new HashSet<>();
            this.documentIDs.add(docID);
        }
    }

    // DO NOT CHANGE THE FOLLOWING SET OF STOP_WORDS
    private static final Set<String> STOP_WORDS = Set.of(
            "in", "out", "on", "off", "over", "under", "again", "further", "then", "once", "here", "there", "when",
            "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such",
            "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just",
            "don", "should", "now", "said", "announced", "company", "industry", "technology", "system", "application",
            "software", "update", "service"
    );

    /*
     This method adds a document
     @param int docID, String text
     @return no return
     */
    // addDocument
    public void addDocument(int docID, String text) {
        return;
    }


    /*
    This method returns a set of document IDs based on the query
    @param String query
    @return Set<Integer>
    */
    //search
    public Set<Integer> search(String query) {
        return null;
    }


    /*
     This method removes a document based on the docID
     @param int docID
     @return void
     */
    // to remove a document traverse the entire tree and remove the given docID from the node's set
    // remove the document ID
    public void removeDocument(int docID){
        return;
    }

    /*
     This method get the map of inverted index
     can be used for testing purposes
     @param none
     @return Map<String, Set<Integer>>
     */
    // returns the map of the inverted index
    public Map<String, Set<Integer>> getIndex() {
       return null;
    }

    /*
     * TODO: Implement helper methods below
     */
}

