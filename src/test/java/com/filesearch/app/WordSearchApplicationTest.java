package com.filesearch.app;

import com.filesearch.util.index.BasicIndexImplementerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class WordSearchApplicationTest {
    @Test
    public void testFetchFiles(){
        File dir = new File("src/test/resources/filesearch-input").getAbsoluteFile();
        List<String> result = WordSearchApplication.getAllFiles(dir.getAbsolutePath());
        List<String> expectedResult = BasicIndexImplementerTest.getAllFiles("filesearch-input");
        Assertions.assertTrue(
                result.size() == expectedResult.size()
                        && result.containsAll(expectedResult)
                        && expectedResult.containsAll(result)
        );
    }
}
