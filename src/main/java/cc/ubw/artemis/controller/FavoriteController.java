package cc.ubw.artemis.controller;

import cc.ubw.artemis.vo.ArticleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2019/12/25 0025
 * Time: 1:04
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Controller
public class FavoriteController {
    @GetMapping("/favorite_00")
    public String getFavorite00(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("目录");
        articleVO.setSourceFile("/favorite/favo_00_contents.md");
        articleVO.setPreSectionUrl("");
        articleVO.setNextSectionUrl("/favo_01_jhzzb");
        model.addAttribute("articleVO", articleVO);
        return "favorite";
    }

    @GetMapping("/favo_01_jhzzb")
    public String getFavorite01Jhzzb(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("简化字总表");
        articleVO.setSourceFile("/favorite/favo_01_jhzzb.md");
        articleVO.setPreSectionUrl("favorite_00");
        articleVO.setNextSectionUrl("/favo_02_zhxy");
        model.addAttribute("articleVO", articleVO);
        return "favorite";
    }

    @GetMapping("/favo_02_zhxy")
    public String getFavorite02Zhxy(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("中华新韵");
        articleVO.setSourceFile("/favorite/favo_02_zhxy.md");
        articleVO.setPreSectionUrl("/favo_01_jhzzb");
        articleVO.setNextSectionUrl("/favo_03_xnsd");
        model.addAttribute("articleVO", articleVO);
        return "favorite";
    }

    @GetMapping("/favo_03_xnsd")
    public String getFavorite03Xnsd(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("一些虚拟设定");
        articleVO.setSourceFile("/favorite/favo_03_xnsd.md");
        articleVO.setPreSectionUrl("favo_02_zhxy");
        articleVO.setNextSectionUrl("/favo_04_sbtj");
        model.addAttribute("articleVO", articleVO);
        return "favorite";
    }

    @GetMapping("/favo_04_sbtj")
    public String getFavorite04Sbtj(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("书本统计");
        articleVO.setSourceFile("/favorite/favo_04_sbtj.md");
        articleVO.setPreSectionUrl("favo_03_xnsd");
//        articleVO.setNextSectionUrl("/reading_201912_01_zgzxjs");
        model.addAttribute("articleVO", articleVO);
        return "favorite";
    }
}
