package com.filesearch.records;

import java.util.List;
import java.util.Objects;

public class ResultRecord {
    private String fileName;
    private List<PositionRecord> positionRecords;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<PositionRecord> getPositionRecords() {
        return positionRecords;
    }

    public void setPositionRecords(List<PositionRecord> positionRecords) {
        this.positionRecords = positionRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ResultRecord)) {
            return false;
        }
        ResultRecord c = (ResultRecord) o;
        return fileName.equals(c.getFileName())
                && Objects.equals(positionRecords, c.getPositionRecords());
    }
}
