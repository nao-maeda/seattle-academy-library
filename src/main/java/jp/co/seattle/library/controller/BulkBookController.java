package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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

@Controller //APIの入り口

public class BulkBookController {
    final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);

    @Autowired
    private BooksService booksService;

    @RequestMapping(value = "/bulkBook", method = RequestMethod.GET) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String login(Model model) {
        return "bulkBook";
    }

    @Transactional
    @RequestMapping(value = "/insertBulkBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String insertBook(Locale locale,
            @RequestParam("csvFile") MultipartFile csvFile,
            Model model) {
        logger.info("Welcome insertBooks.java! The client locale is {}.", locale);

        List<String[]> lines = new ArrayList<String[]>();
        String line = null;
        boolean hasError = false;
        List<String> errorMsg = new ArrayList<String>();
        try (InputStream stream = csvFile.getInputStream();
                Reader reader = new InputStreamReader(stream);
                BufferedReader buf = new BufferedReader(reader);) {
            int row = 0;
            while ((line = buf.readLine()) != null) {
                String[] bookData = new String[6];
                row++;
                // カンマでlineを分割し、配列に格納する
                int i = 0;
                for (String tmp : line.split(",", -1)) {
                    bookData[i++] = tmp;
                }
                lines.add(bookData);
                //バリデーションチェック
                try {
                    // 日付チェック
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    sdf.setLenient(false);
                    sdf.parse(bookData[3]);
                } catch (Exception ex) {
                    errorMsg.add(row + "行目の出版日は半角数字のYYYYMMDD形式で入力してください。");
                    hasError = true;
                }

                if (StringUtils.isEmpty(bookData[4])) {
                    boolean ISBNcheck = bookData[4].matches("^[0-9]+$");
                    int ISBNnum = bookData[4].length();
                    if (!ISBNcheck || !(ISBNnum == 10 || ISBNnum == 13)) {
                        errorMsg.add(row + "行目のISBNの桁数または半角数字が正しくありません。");
                        hasError = true;
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (hasError) {
            model.addAttribute("error", errorMsg);
            return "bulkBook";
        }

        // パラメータで受け取った書籍情報をDtoに格納する。
        for (int i = 0; i < lines.size(); i++) {
            BookDetailsInfo bookInfo = new BookDetailsInfo();
            bookInfo.setTitle(lines.get(i)[0]);
            bookInfo.setAuthor(lines.get(i)[1]);
            bookInfo.setPublisher(lines.get(i)[2]);
            bookInfo.setPublishDate(lines.get(i)[3]);
            bookInfo.setISBN(lines.get(i)[4]);
            bookInfo.setDescription(lines.get(i)[5]);
            //上書きされないように
            booksService.registBook(bookInfo);
        }

        model.addAttribute("complete", "登録完了");

        return "bulkBook";
    }

}
