package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;

@Controller
public class SearchBookController {
    final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private BooksService booksService;

    @Transactional
    @RequestMapping(value = "/searchBook", method = RequestMethod.GET)
    public String transitionHome(Locale locale,
            @RequestParam("searchTitle") String searchTitle,
            @RequestParam("checkBox") String checkBox,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome SearchBooksControler.java! The client locale is {}.", locale);

        //検索結果を表示
        if (checkBox.equals("perfectMatching")) {
            if (booksService.perfSearchBookList(searchTitle).isEmpty()) {
                model.addAttribute("error", "検索結果が一致していません。");
            } else {
                model.addAttribute("bookList", booksService.perfSearchBookList(searchTitle));
            }
        } else if (checkBox.equals("partialMatching")) {
            if (booksService.partSearchBookList(searchTitle).isEmpty()) {
                model.addAttribute("error", "検索結果が一致していません。");
            } else {
                model.addAttribute("bookList", booksService.partSearchBookList(searchTitle));
            }
        }
        
        return "home";
    }

}
