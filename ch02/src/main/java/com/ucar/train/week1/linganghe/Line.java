package com.ucar.train.week1.linganghe;
public class Line {

    private int lineNumber;
    private String lineText;
    private Integer lineWordCount;

    public Line(String lineText, Integer lineWordCount) {
        this.lineText = lineText;
        this.lineWordCount = lineWordCount;
    }

    public Line(int lineNumber, String lineText, String createTime) {
        this.lineNumber = lineNumber;
        this.lineText = lineText;
        this.createTime = createTime;
    }

    public Line(int lineNumber, String lineText, int lineWordCount, String createTime) {
        this.lineNumber = lineNumber;
        this.lineText = lineText;
        this.lineWordCount = lineWordCount;
        this.createTime = createTime;
    }

    private String  createTime;
    private String  modifiedTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLineText() {
        return lineText;
    }

    public void setLineText(String lineText) {
        this.lineText = lineText;
    }

    public int getLineWordCount() {
        return lineWordCount;
    }

    public void setLineWordCount(int lineWordCount) {
        this.lineWordCount = lineWordCount;
    }




}
