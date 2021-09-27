package com.filesearch.app;

import com.filesearch.util.index.BasicIndexImplementer;
import com.filesearch.util.index.FileIndex;
import com.filesearch.util.matcher.TextMatcher;
import com.filesearch.util.matcher.WordCountTextMatcherImpl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

public class WordSearchApplication {
    private final FileIndex index;
    private final TextMatcher matcher;

    public WordSearchApplication() {
        index = new BasicIndexImplementer(); // index which contains the words and occurrences
        matcher = new WordCountTextMatcherImpl(index); // matcher object which contains the matching and ranking logic
    }

    /**
     * Returns all the list of files within the directory
     *
     * @param path input path where search files exist
     * @return List of string with all paths of all the files
     */
    public static List<String> getAllFiles(String path) {
        String separator = System.getProperty("file.separator");// get the file path separator based on the operating system
        File dir = new File(path);
        List<String> filesToIndex = new ArrayList<>();
        if ((!dir.exists() && !dir.isDirectory()) || (dir.listFiles() == null) || dir.listFiles().length == 0) {
            throw new IllegalArgumentException("please enter a valid path");
        } else {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isFile())
                    filesToIndex.add(path + separator + file.getName());
            }
        }
        return filesToIndex;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("please enter a valid path"); // expecting an input path to a directory
        }
        WordSearchApplication app = new WordSearchApplication(); // initialize the application
        List<String> filesToIndex = getAllFiles(args[0]);
        app.index.initialize(filesToIndex);
        DecimalFormat df = new DecimalFormat("0.00");// initialize the index data structure with all the contents of the files within the directory
        try (Scanner keyboard = new Scanner(System.in)) {
            while (true) {
                System.out.print("search> ");
                final String line = keyboard.nextLine();
                if (line.equals(":quit")) {
                    return;
                }
                HashMap<String, Double> resMap = app.matcher.getWordMatches(line); // Find the matches of the query line against the in memory search data structure
                if (resMap.size() == 0)
                    System.out.println("no matches found");
                for (Map.Entry<String, Double> entry : resMap.entrySet()) {
                    System.out.println(entry.getKey() + ": " + df.format(entry.getValue()) + "%");
                }
            }
        }

    }
}