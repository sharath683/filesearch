package com.filesearch.util.index;

import com.filesearch.records.PositionRecord;
import com.filesearch.records.ResultRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasicIndexImplementerTest {
    @DisplayName("test indexing")
    @Test
    void testInitialize() {
        BasicIndexImplementer impl = new BasicIndexImplementer();
        impl.initialize(getAllFiles("filesearch-input"));
        Assertions.assertEquals(impl.index.size(),62);
        Assertions.assertEquals(impl.index.get("imposes").size(),1);
        Map<String,List<PositionRecord>> testRes = impl.index.get("java");
        PositionRecord porec = new PositionRecord(2,7);
        PositionRecord porec1 = new PositionRecord(3,4);
        PositionRecord porec2 = new PositionRecord(3,6);
        List<PositionRecord> recList = new ArrayList<PositionRecord>();
        recList.add(porec);
        recList.add(porec1);
        recList.add(porec2);
        Assertions.assertEquals(testRes.get("file2").size(),3);
        Assertions.assertTrue(recList.size()==testRes.get("file2").size() && recList.containsAll(testRes.get("file2")) && testRes.get("file2").containsAll(recList));
    }
    @Test
    void testFind(){
        BasicIndexImplementer impl = new BasicIndexImplementer();
        impl.initialize(getAllFiles("filesearch-input"));
        List<ResultRecord> res = impl.find("of");
        ResultRecord rec = new ResultRecord();
        rec.setFileName("file1");
        ResultRecord rec1 = new ResultRecord();
        rec1.setFileName("file2");
        List<PositionRecord> posrecs1 = new ArrayList<>();
        PositionRecord porec = new PositionRecord(2,3);
        PositionRecord porec1 = new PositionRecord(3,3);
        posrecs1.add(porec);
        posrecs1.add(porec1);
        List<PositionRecord> posrecs2 = new ArrayList<>();
        PositionRecord porec2 = new PositionRecord(2,5);
        PositionRecord porec3 = new PositionRecord(4,3);
        posrecs2.add(porec2);
        posrecs2.add(porec3);
        rec.setPositionRecords(posrecs2);
        rec1.setPositionRecords(posrecs1);
        List<ResultRecord> resultRecords = new ArrayList<ResultRecord>();
        resultRecords.add(rec);
        resultRecords.add(rec1);
        Assertions.assertTrue(res.size()==resultRecords.size() && res.containsAll(resultRecords) && resultRecords.containsAll(res));
        Assertions.assertIterableEquals(new ArrayList<ResultRecord>(),impl.find("apple"));

    }
    public static List<String> getAllFiles(String path){
        File dir = new File("src/test/resources/"+path).getAbsoluteFile();
        List<String> filesToIndex = new ArrayList<>();
        File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile())
                    filesToIndex.add(dir.getPath() + "/" + files[i].getName());
            }
        return filesToIndex;
    }
}
