package com.ucar.train.week1.linganghe;

public class Paragraph {

    private int paragraphNumber;
    private int paragraphStartLine;

    public Paragraph(int paragraphNumber, int paragraphStartLine, int paragraphEndLine, String createTime) {
        this.paragraphNumber = paragraphNumber;
        this.paragraphStartLine = paragraphStartLine;
        this.paragraphEndLine = paragraphEndLine;
        this.createTime = createTime;
    }

    private int paragraphEndLine;
    private String  createTime;
    private String modifiedTime;

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




    public int getParagraphNumber() {
        return paragraphNumber;
    }

    public void setParagraphNumber(int paragraphNumber) {
        this.paragraphNumber = paragraphNumber;
    }

    public int getParagraphStartLine() {
        return paragraphStartLine;
    }

    public void setParagraphStartLine(int paragraphStartLine) {
        this.paragraphStartLine = paragraphStartLine;
    }

    public int getParagraphEndLine() {
        return paragraphEndLine;
    }

    public void setParagraphEndLine(int paragraphEndLine) {
        this.paragraphEndLine = paragraphEndLine;
    }




}
