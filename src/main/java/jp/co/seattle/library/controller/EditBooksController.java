package jp.co.seattle.library.controller;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

/**
 * 詳細表示コントローラー
 */
@Controller
public class EditBooksController {
    final static Logger logger = LoggerFactory.getLogger(EditBooksController.class);

    @Autowired
    private BooksService booksService;

    @Autowired
    private ThumbnailService thumbnailService;

    /**
     * 詳細画面に遷移する
     * @param locale
     * @param bookId
     * @param model
     * @return
     */
    @Transactional
    @RequestMapping(value = "/editBook", method = RequestMethod.POST)
    public String detailsBook(Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome EditBooksControler.java! The client locale is {}.", locale);

        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));

        return "editBook";
    }
    
    /**
     * 書籍情報を更新する
     * @param locale ロケール情報
     * @param title 書籍名
     * @param author 著者名
     * @param publisher 出版社
     * @param file サムネイルファイル
     * @param description 書籍説明
     * @param ISBN ISBN
     * @param bookId 書籍Id
     * @param model モデル
     * @return 遷移先画面
     */
    @Transactional
    @RequestMapping(value = "/edBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String insertBook(Locale locale,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,
            @RequestParam("publish_date") String publishDate,
            @RequestParam("thumbnail") MultipartFile file,
            @RequestParam("ISBN") String ISBN,
            @RequestParam("bookId") int bookId,
            Model model) {
        logger.info("Welcome insertBooks.java! The client locale is {}.", locale);

        // パラメータで受け取った書籍情報をDtoに格納する。
        BookDetailsInfo bookInfo = new BookDetailsInfo();
        bookInfo.setTitle(title);
        bookInfo.setDescription(description);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);
        bookInfo.setPublishDate(publishDate);
        bookInfo.setISBN(ISBN);
        bookInfo.setBookId(bookId);
        
        //バリデーションチェック
        //StringUtils.isEmpty(description);
        StringUtils.isEmpty(ISBN);

        /**
         * 日付文字列が正しい日付かチェック
         * @param str ymd
         * @return true:正しい日付　false:不正な日付
         */

        boolean hasNotError = false;

        try {
            // 日付チェック
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            sdf.parse(publishDate);
            bookInfo.setPublishDate(publishDate);
        } catch (Exception ex) {
            model.addAttribute("error", "出版日は半角数字のYYYYMMDD形式で入力してください。");
            hasNotError = true;
        }

        boolean ISBNcheck = ISBN.matches("^[0-9]+$");
        int ISBNnum = ISBN.length();

        if (!StringUtils.isEmpty(ISBN)) {
            if (!ISBNcheck || !(ISBNnum == 10 || ISBNnum == 13)) {
                model.addAttribute("error2", "ISBNの桁数または半角数字が正しくありません。");
                hasNotError = true;
            }
        }

        if (hasNotError) {
            return "addBook";
        }


        // クライアントのファイルシステムにある元のファイル名を設定する
        String thumbnail = file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {
                // サムネイル画像をアップロード
                String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
                // URLを取得
                String thumbnailUrl = thumbnailService.getURL(fileName);

                bookInfo.setThumbnailName(fileName);
                bookInfo.setThumbnailUrl(thumbnailUrl);

            } catch (Exception e) {

                // 異常終了時の処理
                logger.error("サムネイルアップロードでエラー発生", e);
                model.addAttribute("bookDetailsInfo", bookInfo);
                return "addBook";
            }
        }

        // 書籍情報を更新する
        booksService.editBook(bookInfo);

        model.addAttribute("editMessage", "更新完了");

        // TODO 更新した書籍の詳細情報を表示するように取得して実装
        model.addAttribute("bookDetailsInfo", bookInfo);


        //  詳細画面に遷移する
        return "details";
    }

}
