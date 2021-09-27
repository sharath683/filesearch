package com.filesearch.util.index;

import com.filesearch.records.PositionRecord;
import com.filesearch.records.ResultRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BasicIndexImplementer implements FileIndex {
    protected HashMap<String, HashMap<String, List<PositionRecord>>> index;

    public BasicIndexImplementer() {

        index = new HashMap<String, HashMap<String, List<PositionRecord>>>(); // initialize the HashMap data structure to hold the word as key and the map of filename, positions as value
    }

    /**
     * initialize the indexing data structure to keep track of all the words and their positions in the files
     *
     * @param fileNames search files
     */
    @Override
    public void initialize(List<String> fileNames) {
        for (String fileName : fileNames) {
            String separator = System.getProperty("file.separator");// get the file path separator based on the operating system
            String realFileName = fileName.substring(fileName.lastIndexOf(separator) + 1);// get only the filename from the absolute path

            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;
                Integer lineNo = 1;

                while ((line = reader.readLine()) != null) {
                    /*
                    read the file line by line, remove the special characters and get the words by splitting on space
                    */
                    String[] words = line.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\W+");
                    for (int i = 0; i < words.length; i++) {
                        String word = words[i].toLowerCase();// convert the word to lowercase
                        PositionRecord pos = new PositionRecord(lineNo, i + 1);// construct the position record to keep track of line number and position in line
                        /*
                        if already the index has the word, get the file,position map and update the filename and position
                        */
                        if (index.containsKey(word)) {
                            HashMap<String, List<PositionRecord>> fileRec = index.get(word);
                            List<PositionRecord> posRecs = fileRec.containsKey(realFileName) ? fileRec.get(realFileName) : new ArrayList<PositionRecord>();
                            posRecs.add(pos);
                            fileRec.put(realFileName, posRecs);
                        }
                        /*
                        if the index doesn't have the word, create the filename,position map and insert into the index data structure
                         */
                        else {
                            HashMap<String, List<PositionRecord>> fileRec = new HashMap<>();
                            List<PositionRecord> posRecs = new ArrayList<PositionRecord>();
                            posRecs.add(pos);
                            fileRec.put(realFileName, posRecs);
                            index.put(word, fileRec);
                        }
                    }
                    lineNo++;
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Error in processing file: " + fileName);
            }
        }
    }

    /**
     * search for a word against the in memory data structure
     *
     * @param word word to be searched for
     * @return list of result records containing the filename and the position in which the word is present in that file
     */
    @Override
    public List<ResultRecord> find(String word) {
        List<ResultRecord> results = new ArrayList<>();
        HashMap<String, List<PositionRecord>> result = index.get(word); // query the hash map for the word
        if (result != null)
            /*
            for each of the results of the query construct the ResultRecord object and pass as the list
            */
            for (Map.Entry<String, List<PositionRecord>> entry : result.entrySet()) {
                ResultRecord rec = new ResultRecord();
                rec.setFileName(entry.getKey());
                rec.setPositionRecords(entry.getValue());
                results.add(rec);
            }
        return results;
    }
}
