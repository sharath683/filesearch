package com.filesearch.util.matcher;

import com.filesearch.util.index.BasicIndexImplementer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.filesearch.util.index.BasicIndexImplementerTest.getAllFiles;

public class WordCountTextMactherImplTest {
    @Test
    public void testTextMatching(){
        BasicIndexImplementer indeximpl = new BasicIndexImplementer();
        WordCountTextMatcherImpl matcherImpl = new WordCountTextMatcherImpl(indeximpl);
        indeximpl.initialize(getAllFiles("filesearch-input"));
        HashMap<String,Double> result = matcherImpl.getWordMatches("java language");
        result.size();
        Assertions.assertTrue(result.size() == 2);
        HashMap<String,Double> expectedResult = new HashMap();
        expectedResult.put("file2",100.0);
        expectedResult.put("file1",50.0);
        Assertions.assertTrue(result.equals(expectedResult));
    }
    @Test
    public void testTextMatchingLimit(){
        BasicIndexImplementer indeximpl = new BasicIndexImplementer();
        WordCountTextMatcherImpl matcherImpl = new WordCountTextMatcherImpl(indeximpl,1);
        indeximpl.initialize(getAllFiles("filesearch-input"));
        HashMap<String,Double> result = matcherImpl.getWordMatches("java language");
        result.size();
        Assertions.assertTrue(result.size() == 1);
        HashMap<String,Double> expectedResult = new HashMap();
        expectedResult.put("file2",100.0);
        Assertions.assertTrue(result.equals(expectedResult));
    }
}
