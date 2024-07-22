///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           Exam Prep Flashcards
// Course:          CS 200, Summer 2024
//
// Author:          Teresa Campbell
// Email:           tjcampbe@wisc.edu
// Lecturer's Name: Jim Williams
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
//
//None
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////
//package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This program allows users to create and review flashcards for exam
 * preparation.
 * Users can add questions and answers, which are saved to separate files,
 * and review the existing questions and answers.
 *
 * @version 1.0
 * @since 2024-07-22
 */
public class ExamPrepFlashcards {

    /**
     * Prompts the user to add questions and answers. The questions and
     * answers
     * are saved to the specified files and also stored in the provided
     * ArrayLists.
     *
     * @param scanner       the Scanner object for user input
     * @param questionsFile the file where questions are saved
     * @param answersFile   the file where answers are saved
     * @param questions     the ArrayList to store questions
     * @param answers       the ArrayList to store answers
     * @throws IOException if an I/O error occurs
     */
    public static void addQuestion(Scanner scanner, File questionsFile,
                                   File answersFile,
                                   ArrayList<String> questions,
                                   ArrayList<String> answers) throws
            IOException {
        boolean keepAdding = true;

        while (keepAdding) {
            scanner.nextLine();  // Consume any leftover newline
            System.out.println("Enter question:");
            String question = scanner.nextLine();
            System.out.println("Enter answer:");
            String answer = scanner.nextLine();

            questions.add(question);
            answers.add(answer);

            // Append the question and answer to their respective files
            try (PrintWriter questionWriter = new PrintWriter
                    (new FileWriter(questionsFile, true))) {
                questionWriter.println(question);
            }
            try (PrintWriter answerWriter = new PrintWriter
                    (new FileWriter(answersFile, true))) {
                answerWriter.println(answer);
            }

            System.out.println("Select an option: ");
            System.out.println("1. Add more questions");
            System.out.println("2. Review existing questions");
            System.out.println("3. End");
            int choice = 1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 2) {
                    keepAdding = false;
                    printQuestion(questions, answers);
                }
            } else if (choice == 3) {
                keepAdding = false;
                System.out.print("Ending.");
                break;
            }
        }
    }

    /**
     * Prints all the questions and answers stored in the provided ArrayLists.
     *
     * @param questions the ArrayList containing the questions
     * @param answers   the ArrayList containing the answers
     */
    public static void printQuestion(ArrayList<String> questions,
                                     ArrayList<String> answers) {
        if (questions.isEmpty()) {
            System.out.println("No questions to review.");
        } else {
            for (int i = 0; i < questions.size(); i++) {
                System.out.println("Question " + i + ": " + questions.get(i));
                System.out.println("Answer " + i + ": " + answers.get(i));
            }
        }
    }

    /**
     * Reads the contents of a file line by line and stores them in an
     * ArrayList.
     *
     * @param file the file to read from
     * @return an ArrayList containing the lines of the file
     * @throws IOException if an I/O error occurs
     */
    public static ArrayList<String> readFile(File file) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine());
            }
        }
        return lines;
    }

    /**
     * Clears the contents of a file.
     *
     * @param file the file to clear
     * @throws IOException if an I/O error occurs
     */
    public static void clearFile(File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.print("");
        }
    }

    /**
     * The main method of the program. It provides a menu for the user to add
     * questions, review existing questions, or end the program.
     *
     * @param args command-line arguments
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int choice = 1;

        File questionsFile = new File("questions.txt");
        File answersFile = new File("answers.txt");
        ArrayList<String> questions = new ArrayList<>();
        ArrayList<String> answers = new ArrayList<>();

        // Clear the files before starting
        clearFile(questionsFile);
        clearFile(answersFile);

        // Load questions and answers from files
        questions.addAll(readFile(questionsFile));
        answers.addAll(readFile(answersFile));

        while (choice != 3) {
            System.out.println("Select an option: ");
            System.out.println("1. Add more questions");
            System.out.println("2. Review existing questions");
            System.out.println("3. End");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next(); // Clear the invalid input
                System.out.println("Invalid input. Please enter a number " +
                        "between 1 and 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    addQuestion(scanner, questionsFile, answersFile,
                            questions, answers);
                    break;
                case 2:
                    printQuestion(questions, answers);
                    break;
                case 3:
                    System.out.print("Ending.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a " +
                            "number " +
                            "between 1 and 3.");
                    break;
            }
        }

        scanner.close();
    }
}
