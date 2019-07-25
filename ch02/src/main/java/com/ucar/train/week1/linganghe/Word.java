package com.ucar.train.week1.linganghe;
import java.util.Objects;

public class Word {
    private String word;
    private int wordCount;

    public Word(String word, Integer wordCount) {
        this.word = word;
        this.wordCount = wordCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    private String createTime;

    public Word(String word, int wordCount, String createTime) {
        this.word = word;
        this.wordCount = wordCount;
        this.createTime = createTime;
    }

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

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }




}
