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
import jp.co.seattle.library.service.LendingService;

/**
 * 詳細表示コントローラー
 */
@Controller
public class DetailsController {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Autowired
    private BooksService bookdService;
    
    @Autowired
    private LendingService lendingService;

    /**
     * 詳細画面に遷移する
     * @param locale
     * @param bookId
     * @param model
     * @return
     */
    @Transactional
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public String detailsBook(Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome detailsControler.java! The client locale is {}.", locale);

        //lendingテーブルにデータがあるか確認
        int IdCnt = lendingService.rentBookCnt(bookId);
        //借りるボタンを活性化
        if (IdCnt == 1) {
            //lendingテーブルにレコードが入っている時
            //借りるボタンは非活性、返すボタンは活性
            model.addAttribute("rentDisabled", "disabled");
            model.addAttribute("returnDisabled");
            model.addAttribute("lendingStatus", "貸出中");

        } else {
            //lendingテーブルにレコードが入っていない時
            //借りるボタンは活性、返すボタンは非活性
            model.addAttribute("rentDisabled");
            model.addAttribute("returnDisabled", "disabled");
            model.addAttribute("lendingStatus", "貸出可");
        }

        model.addAttribute("bookDetailsInfo", bookdService.getBookInfo(bookId));

        return "details";
    }
}
