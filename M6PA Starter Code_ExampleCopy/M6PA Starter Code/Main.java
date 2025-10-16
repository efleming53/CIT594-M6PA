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

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    // helper functions

    // THIS FUNCTION EXTRACTS TITLE
    // IT HAS ALREADY BEEN IMPLEMENTED
    // DO NOT CHANGE THIS FUNCTION
    private static String extractTitle(String combined)
    {
        if (combined == null)
        {
            return "untitled";
        }
        String[] parts = combined.split("\\R", 2);
        String title = parts[0].trim();
        if (title.isEmpty()){
            return "untitled";
        }
        else{
            return title;
        }
    }

    // THIS FUNCTION EXTRACTS THE FIRST SENTENCE
    // IT HAS ALREADY BEEN IMPLEMENTED
    // DO NOT CHANGE THIS FUNCTION
    private static String extractFirstSentence(String combined)
    {
        if (combined == null)
        {
            return "";
        }

        String[] parts = combined.split("\\R", 2);
        String body = (parts.length > 1) ? parts[1].trim() : "";
        if (body.isEmpty())
        {
            return "";
        }

        //split body into sentences that end at a ., !, or ?
        //extract the first sentence with a limit of 200 characters and ...
        String[] sentences = body.split("(?<=[.!?])\\s+");
        String first = sentences[0].replaceAll("\\s+", " ").trim();
        return first.length() > 200 ? first.substring(0, 200) + "..." : first;
    }

    public static void main(String[] args) {

        // create instance of invertedIndex
        InvertedIndex index = new InvertedIndex();

        // create a map to store document ID and combined title + body text
        Map<Integer, String> sampleArticles = new HashMap<>();

        // file path to CSV file
        String csvFilePath = "all_articles_filtered_sample_10.csv";

        //TODO:
        // Option A: OpenCSV
        // Requires: opencsv-5.12.jar
        // To use:
        // 1. Put JAR in lib/opencsv-5.12.jar
        // 2. Uncomment the two imports at the top of the file

        //TODO:
        //Option B: Reuse your CSV Parser from HW5
        // Replace CSVParser.parse() with your HW5 parser method

        // === MAIN PRINTOUT TO SCREEN ===
        // This section is pre-implemented for clarity.
        // Please do not modify.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your query to find tech articles related to your interests: ");




        // start a loop for User input
        while (true) {
            System.out.println("Enter input (and enter exit to exit the program): ");
            String userInput = scanner.nextLine().trim();

            // if user types exit leave the system
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            // variable result for regular search
            Set<Integer> result = index.search(userInput);

            // print the results (not ranked)
            if (result.isEmpty()) {
                System.out.println("No matches found!");
            } else {
                for (int documentID : result) {
                    String combined = sampleArticles.get(documentID);
                    System.out.println("[" +documentID + "] " + extractTitle(combined));
                    String first = extractFirstSentence(combined);
                    if (!first.isEmpty())
                    {
                        System.out.println("    " + first);
                    }
                    //System.out.println("[" + documentID + "] " + sampleArticles.get(documentID));
                    System.out.println();
                }

            }

            System.out.println();



        }
        scanner.close();
    }
}
