package com.filesearch.records;

public class PositionRecord {
    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public PositionRecord(Integer line, Integer pos) {
        this.position = pos;
        this.line = line;
    }

    private Integer line;
    private Integer position;


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PositionRecord)) {
            return false;
        }
        PositionRecord c = (PositionRecord) o;
        return Integer.compare(position, c.position) == 0
                && Integer.compare(line, c.line) == 0;
    }
}
