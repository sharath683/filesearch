package com.filesearch.util.matcher;

import java.util.HashMap;

public interface TextMatcher {
    HashMap<String, Double> getWordMatches(String line);
}
