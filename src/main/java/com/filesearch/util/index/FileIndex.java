package com.filesearch.util.index;

import com.filesearch.records.ResultRecord;

import java.util.List;

public interface FileIndex {
    void initialize(List<String> filePaths);
    List<ResultRecord> find(String word);
}
