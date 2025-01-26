package com.example.javaparser;

import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.token.*;

import java.util.List;

public class JavaTokenExample {
    public static void main(String[] args) {
        // The Java code to tokenize
        String javaCode = """
            /**
             * This is a sample Java class demonstrating tokenization.
             */
            
            public class TokenizerExample {
                private int number = 42;
                private String text = "Hello, World!";
                
                public TokenizerExample() {
                    // Constructor
                }
                
                public void greet() {
                    System.out.println(text);
                }
                
                public static void main(String[] args) {
                    TokenizerExample example = new TokenizerExample();
                    example.greet();
                }
            }
            """;

        // Initialize JavaParser
        ParserConfiguration config = new ParserConfiguration();
        JavaParser parser = new JavaParser(config);

        // Parse the code
        ParseResult<CompilationUnit> result = parser.parse(javaCode);

        if (result.isSuccessful() && result.getResult().isPresent()) {
            CompilationUnit cu = result.getResult().get();

            // Retrieve tokens
            List<JavaToken> tokens = cu.getTokens();

            // Print token details
            System.out.println("=== TOKEN LIST ===");
            for (JavaToken token : tokens) {
                // Skip whitespace and comments if desired
                if (token.isWhitespace() || token.isComment()) {
                    continue;
                }

                String type = token.getKind().name();
                String value = token.getText();

                System.out.printf("Type: %-15s Value: '%s'%n", type, value);
            }

            // Optionally, print comments separately
            List<Comment> comments = cu.getAllContainedComments();
            System.out.println("\n=== COMMENTS ===");
            for (Comment comment : comments) {
                System.out.println(comment.getContent().trim());
            }

        } else {
            System.out.println("Failed to parse the Java code.");
            result.getProblems().forEach(problem -> System.out.println(problem.getMessage()));
        }
    }
}
