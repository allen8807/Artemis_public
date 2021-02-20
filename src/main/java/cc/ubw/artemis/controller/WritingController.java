package cc.ubw.artemis.controller;

import cc.ubw.artemis.vo.ArticleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2019/12/23 0023
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Controller
public class WritingController {
    @GetMapping("/writing_00")
    public String getWriting00(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("目录");
        articleVO.setSourceFile("/writing/essay_00_contents.md");
        articleVO.setPreSectionUrl("");
        articleVO.setNextSectionUrl("/writing_202004_01_sskj");
        model.addAttribute("articleVO", articleVO);
        return "writing";
    }

    @GetMapping("/writing_202004_01_sskj")
    public String getWriting01(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("算术口诀");
        articleVO.setSourceFile("/writing/essay_202004_01_sskj.md");
        articleVO.setPreSectionUrl("/writing_00");
//        articleVO.setNextSectionUrl("/reading_201912_01");
        model.addAttribute("articleVO", articleVO);
        return "writing";
    }
}
