package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LendingService {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //登録
    /**
     * 書籍を登録する
     *
     * @param bookInfo 書籍情報
     */
    public void rentBook(int bookId) {

        String sql = "INSERT INTO lending(BOOKID) values (" + bookId + ")";

        jdbcTemplate.update(sql);
    }

    //貸出状況の確認
    //書籍IDでデータをカウント
    /**
     * 書籍の貸出状況の確認
     * 
     * @param bookid　書籍ID
     * @return
     */
    public int rentBookCnt(int bookid) {

        String sql = "select count(ID) from lending where BOOKID =" + bookid;
        //取ってきたデータを変数に入れる
        int IdCnt = jdbcTemplate.queryForObject(sql, Integer.class);
        return IdCnt;
    }

    /**
     * 書籍返却実行
     * 
     * @param bookId　書籍ID
     */
    public void returnBook(int bookId) {

        String sql = "DELETE FROM lending WHERE bookid = " + bookId;
        //sqlを実行するよって言ってる↓
        jdbcTemplate.update(sql);
    }

}
