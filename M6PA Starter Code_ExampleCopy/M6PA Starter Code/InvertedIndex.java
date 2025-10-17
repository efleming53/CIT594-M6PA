/*
 * I attest that the code in this file is entirely my own except for the starter
 * code provided with the assignment and the following exceptions:
 * <Enter all external resources and collaborations here. Note external code may
 * reduce your score but appropriate citation is required to avoid academic
 * integrity violations. Please see the Course Syllabus as well as the
 * university code of academic integrity:
 *  https://catalog.upenn.edu/pennbook/code-of-academic-integrity/ >
 * Signed,
 * Author: Eric Fleming
 * Penn email: eflem53@seas.upenn.edu
 * Date: 2025-10-13
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
    	
    	// null input check
    	if (text == null) {
    		return;
    	}
    	
    	String[] tokens = tokenize(text); // tokenize document text
    	
    	int firstIndex = 0; // index of first valid token to make root if root not set yet
    	
    	// if no root yet, traverse through tokens until a valid one is found (not empty), then make the root a new node with that word and docID
    	
    	// TODO: handle instance where root could be a STOP_WORD?
    	
    	if (root == null) {
        	while (tokens[firstIndex].isEmpty()) {
        		firstIndex++;
        	}
        	root = new BSTNode(tokens[firstIndex], docID);
    	}
    	
    	// set firstIndex to -1 if it was not incremented above, this will make value for i below not start at 1 in that case
    	if (firstIndex == 0) {
    		firstIndex = -1;
    	}
    	
    	// loop over all tokens in document
    	for (int i = firstIndex + 1; i < tokens.length; i++) {
    		
    		String word = tokens[i]; // get current word from list of tokens
    		
    		// skip processing if word is empty string or is a stop_word
    		if (word.isEmpty() || STOP_WORDS.contains(word)) {
    			continue;
    		}
    		
    		ArrayList<BSTNode> nodes = findNode(null, root, word);
    		
    		/*Node returned from findNode is null
    		 *We must create a node for this word*/
    		if (nodes.get(1) == null) {
    			BSTNode newNode = new BSTNode(word, docID); // create new node for word
    			
    			int parentComparison = word.compareTo(nodes.get(0).keyWord); // find if word is larger or smaller than parent node so we can set parents child correctly
    			
    			// make new node parents left child if smaller
    			if (parentComparison < 0) {
    				nodes.get(0).left = newNode;
    			}
    			
    			// make new node parents right if larger
    			if (parentComparison > 0) {
    				nodes.get(0).right = newNode;
    			}
    			continue;
    		}
    		
    		nodes.get(1).documentIDs.add(docID);	
    	}
    	
        return;
    }


    /*
    This method returns a set of document IDs based on the query
    @param String query
    @return Set<Integer>
    */
    //search
    public Set<Integer> search(String query) {
    	
    	// null/empty input check
    	if (query == null || query.isEmpty()) {
    		return new HashSet<Integer>();
    	}
    	
    	String[] tokens = tokenize(query); // tokenize query
    	
		Set<Integer> docIDs = new HashSet<Integer>(); // init Set to store intersection of docIDs
    	
    	// loop over tokens
    	for (int i = 0; i < tokens.length; i++) {
    		
    		int firstValidToken = 0; // track where first valid token is so we can copy its docIDs into Set
    		
    		String word = tokens[i]; // get current token
    		
    		// skip empty tokens and stop words, increment firstValidToken
    		if (word.isEmpty() || STOP_WORDS.contains(word)) {
    			firstValidToken++;
    			continue;
    		}
    		
    		ArrayList<BSTNode> nodes = findNode(null, root, word); // search for current word
    		
    		// if word not found, return empty set
    		if (nodes.get(1) == null) {
    			return new HashSet<Integer>();
    		}
    		
    		// if word is first valid token, add all of its docIDs to start running Set
    		if (i == firstValidToken) {
    			docIDs.addAll(nodes.get(1).documentIDs);
    			continue;
    		}    		
    		docIDs.retainAll(nodes.get(1).documentIDs); // keep all docIDs in running set that are in current words Set	  		
    	}
    	
        return docIDs;
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
    	
    	// return empty HashMap if root is null
    	if (root == null) {
    		return new HashMap<>();
    	}
    	
    	HashMap<String, Set<Integer>> nodes = new HashMap<>(); // init HashMap to populate with nodes
    	
    	getIndexTraversal(root, nodes); // call recursive function to populate HashMap
    	
       return nodes;
    }

    /*
     * TODO: Implement helper methods below
     */
    
    /*
    This method recursively finds the node with the given word
    @param 
    	BSTNode parent: the parent of the root node
    	BSTNode root: the node we are inspecting
    	String word: the word we are searching for
    @return this method returns an ArrayList of size 4:
    	index 0: the parent node
    	index 1: the node itself
    	index 2: the nodes left child
    	index 3: the nodes right child
    */
   // findNode
    public ArrayList<BSTNode> findNode(BSTNode parent, BSTNode root, String word) {
    	// base case: root is null, means word was not found
    	if (root == null) {
    		ArrayList<BSTNode> nodeReferences = new ArrayList<BSTNode>(4);
    		nodeReferences.add(parent); // parent node is parent
    		nodeReferences.add(null); // node is null
    		nodeReferences.add(null); // left child is null
    		nodeReferences.add(null); // right child is null
    		return nodeReferences;
    	}
    	
    	int comparison = word.compareTo(root.keyWord);
    	
		// found node for given word, return array with references   	
    	if (comparison == 0) {
        	ArrayList<BSTNode> nodeReferences = new ArrayList<BSTNode>(4);
        	nodeReferences.add(parent); // parent node is parent
        	nodeReferences.add(root); // node is root
        	nodeReferences.add(root.left); // left child
        	nodeReferences.add(root.right); // right child
        	return nodeReferences;  	
        
        // word smaller than current node, recursively call left
    	} else if (comparison < 0) {
        	return findNode(root, root.left, word);  
        	
        // word larger than current node, recursively call right
    	} else {
        	return findNode(root, root.right, word);  
    	}
 		
    }

    	
    
    
    /*
    This method returns an array of tokens from a given string
    @param String text
    @return returns an array of tokens
    */
   // tokenize
    public String[] tokenize(String text) {
    		
    	text = text.toLowerCase().replaceAll("[^a-z0-9\\s-]", " ").replaceAll("\\b-+", "").replaceAll("-+\\b",""); // converts text to lowercase, replaces punctuation with spaces
    	
    	String[] tokens = text.split("\\s+"); // split text into tokens along one or more instances of whitespace
    	
    	return tokens;
    }
    
    /*
    This method recursively traverses the BST and populates a HashMap
    with each nodes information as it goes along
    @param root: current node, nodes: HashMap to populate
    @return this method returns nothing
    */
    public void getIndexTraversal(BSTNode root, HashMap<String, Set<Integer>> nodes) {
    	// base case: root is null
    	if (root == null) {
    		return;
    	}
    	
    	nodes.put(root.keyWord, root.documentIDs); // key = nodes keyWord, value = set of docIDs
    	
    	getIndexTraversal(root.left, nodes); // recursively traverse to left child  	
    	getIndexTraversal(root.right, nodes); // recursively traverse to right child
    }
}

