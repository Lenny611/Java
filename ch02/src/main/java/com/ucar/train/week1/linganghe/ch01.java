package com.ucar.train.week1.linganghe;

import com.mysql.cj.util.StringUtils;
import com.ucar.dao.ILineDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class ch01 {
    public static void main(String[] args) throws IOException {

       // JdbcInsertData();
      //  Mybatis();
    }

    public static void JdbcInsertData() throws IOException {
        String Path = ch01.class.getResource("/").getPath() + "TheOldManAndTheSea.txt";
        new ch01().readAndGetLineThenPragraph(Path);
    }

    public static void Mybatis() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = factory.openSession();
        ILineDao lineDao=sqlSession.getMapper(ILineDao.class);


        int specificLineWordCount = lineDao.getSpecificLineWordCount(100);
        Integer specificParagraphWordCount = lineDao.getSpecificParagraphWordCount(3);
        List<Word> wordCountTopNByDesc = lineDao.getWordCountTopNByDesc(5);
        List <String>paragraphInfo = lineDao.getParagraphInfo(4);


        log.info("所有单词量为："+lineDao.getAllWordCount());
        log.info("需要的最少单词量为："+lineDao.getNeedLessWord());
        log.info("第"+100+"行的数据为："+specificLineWordCount);
        log.info("第3段的单词量为："+specificParagraphWordCount);
        StringBuilder info=new StringBuilder();
        for (String s : paragraphInfo) {
          info.append(s);
        }
        log.info("第4段的内容为"+info.toString());

        log.info("按单词频数降序排列");
        for (Word word : wordCountTopNByDesc) {
            log.info(word.getWord()+"  "+word.getWordCount());
        }
        sqlSession.close();
        inputStream.close();
    }

    public void insertLineData(List<Line> LineObjectList) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "password");
            connection.setAutoCommit(true);
            String SQL = "INSERT t_b_line (Line_number,Line_text,Line_word_count,Create_time) VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(SQL);
            for (Line line : LineObjectList) {
                preparedStatement.setInt(1, line.getLineNumber());
                preparedStatement.setString(2, line.getLineText());
                preparedStatement.setInt(3, line.getLineWordCount());
                preparedStatement.setString(4, line.getCreateTime());
                preparedStatement.addBatch();
        }
            preparedStatement.executeBatch();
            log.info("批量插入行数据成功");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
                connection.close();
                preparedStatement.close();
        }
    }
    public void insertParagraphData(List<Paragraph> ParagraphObjectList) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "password");
            connection.setAutoCommit(true);
            String SQL = "INSERT t_b_paragraph (Paragraph_number,Paragraph_start_line,Paragraph_end_line,Create_time) VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(SQL);
            for (Paragraph paragraph : ParagraphObjectList) {
                preparedStatement.setInt(1, paragraph.getParagraphNumber());
                preparedStatement.setInt(2, paragraph.getParagraphStartLine());
                preparedStatement.setInt(3, paragraph.getParagraphEndLine());
                preparedStatement.setString(4, paragraph.getCreateTime());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            log.info("批量段数据成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
                connection.close();
                preparedStatement.close();
        }
    }
    public void insertWordData(List<Word>wordList) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "password");
            String SQL = "INSERT result (Word,Word_count,Create_time) VALUES(?,?,?)";
            preparedStatement = connection.prepareStatement(SQL);
            for (Word word : wordList) {
                preparedStatement.setString(1, word.getWord());
                preparedStatement.setInt(2, word.getWordCount());
                preparedStatement.setString(3, word.getCreateTime());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            log.info("批量插入单词数据成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }
    public String getNowTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String nowTime = dateFormat.format(calendar.getTime());
        return nowTime;
    }
    public void readAndGetLineThenPragraph(String fileName) throws IOException {
        /*
    读到空行
    空行需要判断是段开始行还是段结束行，这里使用一个标志位paragraphFlag来判断
    标记号为false时，表示为段结束行，设置段结束行是当前行-1，段号+1，标志位改为true，记录当前时间
    标记号为true时，表示为段开始行，设置段开始行是当前行+1,标志位改为false
     行号+1，记录行内容,段号+1
    读到非空行，记录行内容，记录行单词量，行号+1，记录当前时间
* */
        List<Line> LineObjectList = new ArrayList<>();
        List<Paragraph> ParagraphObjectList = new ArrayList<Paragraph>();
        HashSet<String>hashSet= new HashSet<>();
        List<Word> wordList= new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String readLine;
        try {
            int pNumber = 0, pStartLine = 0, pEndLine = 0;
            boolean paragraphFlag = true;
            Integer lineNumber = 1;
            Integer paragraphNumber = 1;
            while ((readLine = br.readLine()) != null) {
                if (StringUtils.isNullOrEmpty(readLine)) {
                    if (paragraphFlag == false) {
                        pEndLine = lineNumber - 1;
                        paragraphFlag = true;
                        paragraphNumber++;
                        Paragraph paragraph = new Paragraph(pNumber, pStartLine, pEndLine, getNowTime());
                        ParagraphObjectList.add(paragraph);
                    }
                    if (paragraphFlag) {
                        pStartLine = lineNumber + 1;
                        paragraphFlag = false;
                        pNumber = paragraphNumber;
                    }
                    Line line = new Line(lineNumber, readLine, getNowTime());
                    LineObjectList.add(line);
                    lineNumber++;
                    continue;
                }
                getWordList(wordList, hashSet, replaceAllChar(readLine));
                int wordCount = getWordCount(readLine);
                Line line = new Line(lineNumber, readLine, wordCount, getNowTime());
                lineNumber++;
                LineObjectList.add(line);

            }
            insertWordData(wordList);
            insertLineData(LineObjectList);
            insertParagraphData(ParagraphObjectList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
    }
    public List<Word> getWordList(List<Word> wordList, Set<String> hashSet, String[]split)
    {
        for (int index=0,leng=split.length;index<leng;index++){
            if (hashSet.contains(split[index].trim())==false)
            {
                //word不存在的时候,创建一个word对象，times初始化为0
                //把word对象加入arraylist，把该单词加入hashset
                Word word=new Word(split[index],0,getNowTime());
                wordList.add(word);
                hashSet.add(split[index].trim());
            }
            //存在的时候，遍历wordList，找到split[index]=word.name相等的对象，times+1
            for (int subscript=0,length=wordList.size();subscript<length;subscript++)
            {
                Word word=wordList.get(subscript);
                if(word.getWord().equals(split[index])) {
                    word.setWordCount(word.getWordCount()+1);
                }
            }
        }
        return wordList;
    }
    public String[] replaceAllChar(String string)
    {
        /*
         * 替换所有特殊字符
         *小写 p 是 property 的意思，表示 Unicode 属性，用于 Unicode 正表达式的前缀
         *  P表示标点字符 S表示符号  C表示其他字符  N表示数字
         * */
        String cutting = string.replaceAll("\\pP|\\pS|\\pC|\\pN", "");
        String[] split = cutting.toLowerCase().trim().split("\\s+");
        return split;
    }
    public int getWordCount(String string) {
        /*
         * 替换所有特殊字符
         *小写 p 是 property 的意思，表示 Unicode 属性，用于 Unicode 正表达式的前缀
         *  P表示标点字符 S表示符号  C表示其他字符  N表示数字
         * */
        if (StringUtils.isEmptyOrWhitespaceOnly(string)) {
            return 0;
        } else {
            String cutting = string.replaceAll("\\pP|\\pS|\\pC|\\pN", "");
            String[] split = cutting.toLowerCase().trim().split("\\s+");
            return split.length;
        }
    }

}
