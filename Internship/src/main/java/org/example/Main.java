package org.example;

import org.apache.commons.cli.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args)); 

        Options options = new Options();
        options.addOption("file", true, "File path");
        options.addOption("top", true, "Number of top phrases to display");
        options.addOption("phraseSize", true, "The size of phrases to analyze");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("file") && cmd.hasOption("top") && cmd.hasOption("phraseSize")) {
                String filePath = cmd.getOptionValue("file");
                int top, phraseSize;

                try {
                    top = Integer.parseInt(cmd.getOptionValue("top"));
                    phraseSize = Integer.parseInt(cmd.getOptionValue("phraseSize"));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("The 'top' and 'phraseSize' arguments must be integers.");
                }
                processFile(filePath, top, phraseSize);
            } else {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("Main", options);
               }
        } catch (Exception e) {
            System.out.println("Error processing command line arguments: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void processFile(String filePath, int top, int phraseSize) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            System.out.println("Fișierul " + filePath + " nu există. Te rog să verifici calea către fișier și să încerci din nou.");
            return;
        }
        try {
            String content = Files.readString(Paths.get(filePath));
            List<String> sentences = Arrays.asList(content.split("[.!?]\\s*"));
            List<String> words = Arrays.asList(content.split("\\s+"));
            Map<String, Long> phraseCounts = new HashMap<>();

            // Generate phrases and count them
            for (int i = 0; i < words.size() - phraseSize + 1; i++) {
                List<String> subList = words.subList(i, i + phraseSize);
                String phrase = String.join(" ", subList);
                phraseCounts.put(phrase, phraseCounts.getOrDefault(phrase, 0L) + 1);
            }

            // Sort phrases by frequency
            List<Map.Entry<String, Long>> sortedPhrases = phraseCounts.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(top)
                    .collect(Collectors.toList());

            // Output results
            System.out.println("\n+----------------------+-------+");
            System.out.println("| Number of words: "  + "    |  "+ words.size()+ "  |");
            System.out.println("+----------------------+-------+");
            System.out.println("| Number of sentences: " + "|  " + sentences.size() + "  |");
            System.out.println("+----------------------+-------+");

            System.out.println("\n-----------------------------");
            System.out.println("| Phrases\t| Count");
            System.out.println("-----------------------------");
            for (Map.Entry<String, Long> entry : sortedPhrases) {
                System.out.println("| " + entry.getKey() + "\t" + entry.getValue() + " |");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
