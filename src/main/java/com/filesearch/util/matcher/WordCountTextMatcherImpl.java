package com.filesearch.util.matcher;

import com.filesearch.records.ResultRecord;
import com.filesearch.util.index.FileIndex;

import java.util.*;
import java.util.stream.Collectors;

public class WordCountTextMatcherImpl implements TextMatcher {
    private final FileIndex index;
    private final Integer searchResultLimit;

    // initialize the matcher with corresponding indexed search data structure and default results limit of 10
    public WordCountTextMatcherImpl(FileIndex idx) {
        this.index = idx;
        this.searchResultLimit = 10;
    }

    // initialize the matcher with corresponding indexed search data structure and limit from constructor
    public WordCountTextMatcherImpl(FileIndex idx, int limit) {
        this.index = idx;
        this.searchResultLimit = limit;
    }

    /**
     * Return the result of matching and ranking of a query line of words against the indexed data structure
     *
     * @param line line of word(s) to be searched for
     * @return HashMap with key as filename and value as percentage of words matched against the file
     */
    public HashMap<String, Double> getWordMatches(String line) {
        String[] words = line.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\W+"); //convert to lowercase,
        // remove special characters and split by space to get all the words in the search query
        HashMap<String, Integer> resMap = new HashMap<>();
        for (String word : words) {
            List<ResultRecord> results = index.find(word);// query the indexed data structure for each of the word
            for (ResultRecord res : results) {
                if (!resMap.containsKey(res.getFileName()))
                    resMap.put(res.getFileName(), 1); // update the result map to keep track of the filename in which the word is found
                else
                    resMap.put(res.getFileName(), resMap.get(res.getFileName()) + 1); // increment the count against the filename whenever a word is found
            }
        }

        List sortedMap = resMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList()); // sort the result map based on the total word count matched within each file
        int count = 0;
        LinkedHashMap<String, Double> finalRes = new LinkedHashMap<>();
        for (int i = 0; i < sortedMap.size(); i++) {
            Map.Entry entry = (Map.Entry) sortedMap.get(i);
            if (count == searchResultLimit) // limit the search results to the top n files
                break;
            finalRes.put((String) entry.getKey(), ((((Integer) entry.getValue()).doubleValue()) / words.length) * 100); // compute the percentage of words matched against the toatl number of words in the query line
            count++;
        }
        return finalRes;
    }
}
