package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 *  booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 書籍リストを取得する
     *
     * @return 書籍リスト
     */
    public List<BookInfo> getBookList() {

        // TODO 取得したい情報を取得するようにSQLを修正
        List<BookInfo> getedBookList = jdbcTemplate.query(
                "select id,title,description,author,publisher,publish_date,thumbnail_url,ISBN from books ORDER BY title asc",
                new BookInfoRowMapper());

        return getedBookList;
    }

    /**
     * 書籍IDに紐づく書籍詳細情報を取得する
     *
     * @param bookId 書籍ID
     * @return 書籍情報
     */
    public BookDetailsInfo getBookInfo(int bookId) {

        // JSPに渡すデータを設定する
        String sql = "SELECT * FROM books where id ="
                + bookId;

        BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());

        return bookDetailsInfo;
    }



    /**
     * 書籍を登録する
     *
     * @param bookInfo 書籍情報
     */
    public void registBook(BookDetailsInfo bookInfo) {

        String sql = "INSERT INTO books (title, author,publisher,publish_date,thumbnail_url,thumbnail_name,reg_date,upd_date,isbn,description"
                + ") VALUES ('"
                + bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
                + bookInfo.getPublishDate() + "','"
                + bookInfo.getThumbnailUrl() + "','"
                + bookInfo.getThumbnailName() + "',"
                + "sysdate(),"
                + "sysdate(),'"
                + bookInfo.getISBN() + "','"
                + bookInfo.getDescription() + "')";

        jdbcTemplate.update(sql);
    }

    /**
     * 書籍を更新する
     *
     * @param bookInfo 書籍情報
     */
    public void editBook(BookDetailsInfo bookInfo) {

        String sql = "UPDATE books SET "
                + "TITLE='" + bookInfo.getTitle() + "',"
                + "AUTHOR='" + bookInfo.getAuthor() + "',"
                + "PUBLISHER='" + bookInfo.getPublisher() + "',"
                + "PUBLISH_DATE='" + bookInfo.getPublishDate() + "',"
                + "THUMBNAIL_URL='" + bookInfo.getThumbnailUrl() + "',"
                + "THUMBNAIL_NAME='" + bookInfo.getThumbnailName() + "',"
                + "UPD_DATE=sysdate(),"
                + "ISBN='" + bookInfo.getISBN() + "',"
                + "DESCRIPTION='" + bookInfo.getDescription() + "'"
                + "WHERE ID = " + bookInfo.getBookId();


        jdbcTemplate.update(sql);
    }

    /**
     * 書籍を削除する
     *
     * @param bookId 書籍ID
     */
    public void deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE id=" + bookId;
        jdbcTemplate.update(sql);
    }

    /**
     * 書籍登録時に書籍IDを取得する
     * 
     * @return　newId 書籍ID
     */
    public int getNewId() {

        String sql = "SELECT MAX(ID) FROM books";

        int newId = jdbcTemplate.queryForObject(sql, Integer.class);

        return newId;
    }

    /**
     * 
     * 検索した書籍を取得する
     * 完全一致
     * 
     * @param searchTitle 検索した書籍名
     * @return 書籍リスト
     */
    public List<BookInfo> perfSearchBookList(String searchTitle) {

        // TODO 取得したい情報を取得するようにSQLを修正
        List<BookInfo> searchedBookList = jdbcTemplate.query(
                "select id,title,author,publisher,publish_date,thumbnail_url from books WHERE title like'"
                        + searchTitle + "'ORDER BY title asc",
                new BookInfoRowMapper());

        return searchedBookList;
    }

    /**
     * 
     * 検索した書籍を取得する
     * 部分一致
     * 
     * @param searchTitle 検索した書籍名
     * @return 書籍リスト
     */
    public List<BookInfo> partSearchBookList(String searchTitle) {

        // TODO 取得したい情報を取得するようにSQLを修正
        List<BookInfo> searchedBookList = jdbcTemplate.query(
                "select id,title,author,publisher,publish_date,thumbnail_url from books WHERE title like '%"
                        + searchTitle + "%' ORDER BY title asc",
                new BookInfoRowMapper());

        return searchedBookList;
    }

}
