package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.LendingService;


@Controller //APIの入り口

public class RentBookController {
    
    final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);
    
    @Autowired
    private BooksService booksService;
    
    @Autowired
    private LendingService LendingService;

    //booksテーブルから書籍IDを取得
    //lendingテーブルに格納
    
    @RequestMapping(value = "/rentBook", method = RequestMethod.POST) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String rentBook(
            Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        logger.info("Welcome delete! The client locale is {}.", locale);

        LendingService.rentBook(bookId);

        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
        model.addAttribute("rentDisabled", "disabled");
        model.addAttribute("returnDisabled");
        model.addAttribute("lendingStatus", "貸出中");

        return "details";

    }
    

}
