package com.ucar.dao;


import com.ucar.train.week1.linganghe.Line;
import com.ucar.train.week1.linganghe.Word;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ILineDao {

    Integer getAllWordCount();
    Integer getNeedLessWord();
    int getSpecificLineWordCount(@Param("lineNumber")int lineNumber);
    Integer getSpecificParagraphWordCount(@Param("paragraphNumber")int paragraphNumber);
    List<Word> getWordCountTopNByDesc(@Param("paragraphNumber") int paragraphNumber);
    List<String>getParagraphInfo(@Param("paragraphNumber")int paragraphNumber);

}
