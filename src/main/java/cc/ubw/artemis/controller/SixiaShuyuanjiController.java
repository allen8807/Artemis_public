package cc.ubw.artemis.controller;

import cc.ubw.artemis.vo.ArticleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2019/12/11 0011
 * Time: 16:12
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Controller
public class SixiaShuyuanjiController {
    @GetMapping("/sixia_shuyuanji_00")
    public String getShuyuanji00(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("漱愿记");
        articleVO.setBannerImage("image/shuyuanji/shuyuanji.jpg");
        articleVO.setMusic("shuyuanji");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_00.md");
        articleVO.setPreSectionUrl("");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_01_01");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_01_01")
    public String getShuyuanji0101(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("犹记年华");
        articleVO.setBannerImage("image/shuyuanji/tidengzhaoshanhe.jpg");
        articleVO.setMusic("tidengzhaoshanhe");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_01_01.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_00");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_01_02");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_01_02")
    public String getShuyuanji0102(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("犹记年华");
        articleVO.setBannerImage("image/shuyuanji/renjianruhua.jpg");
        articleVO.setMusic("renjianruhua");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_01_02.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_01_01");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_01_03");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_01_03")
    public String getShuyuanji0103(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("犹记年华");
        articleVO.setBannerImage("image/shuyuanji/diyiyexiangsi.jpg");
        articleVO.setMusic("diyiyexiangsi");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_01_03.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_01_02");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_01_04");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_01_04")
    public String getShuyuanji0104(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("犹记年华");
        articleVO.setBannerImage("image/shuyuanji/youjinianhua.jpg");
        articleVO.setMusic("youjinianhua");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_01_04.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_01_03");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_01_05");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_01_05")
    public String getShuyuanji0105(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("犹记年华");
        articleVO.setBannerImage("image/shuyuanji/yuanshengyizhuan.jpg");
        articleVO.setMusic("yuanshengyizhuan");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_01_05.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_01_04");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_02_01");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_02_01")
    public String getShuyuanji0201(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("弈中夜光");
        articleVO.setBannerImage("image/shuyuanji/yizhongyeguang.jpg");
        articleVO.setMusic("jieju");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_02_01.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_01_05");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_02_02");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_02_02")
    public String getShuyuanji0202(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("弈中夜光");
        articleVO.setBannerImage("image/shuyuanji/jieju.jpg");
        articleVO.setMusic("jieju");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_02_02.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_02_01");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_02_03");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_02_03")
    public String getShuyuanji0203(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("弈中夜光");
        articleVO.setBannerImage("image/shuyuanji/nanke.jpg");
        articleVO.setMusic("nanke");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_02_03.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_02_02");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_03_01");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_03_01")
    public String getShuyuanji0301(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("惟留风月");
        articleVO.setBannerImage("image/shuyuanji/ziweifengyuemaqianzu.jpg");
        articleVO.setMusic("ziweifengyuemaqianzu");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_03_01.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_02_03");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_03_02");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_03_02")
    public String getShuyuanji0302(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("惟留风月");
        articleVO.setBannerImage("image/shuyuanji/yanyichunqiu.jpg");
        articleVO.setMusic("yanyichunqiu");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_03_02.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_03_01");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_03_03");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_03_03")
    public String getShuyuanji0303(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("惟留风月");
        articleVO.setBannerImage("image/shuyuanji/shishuqiannian.jpg");
        articleVO.setMusic("shishuqiannian");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_03_03.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_03_02");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_03_04");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_03_04")
    public String getShuyuanji0304(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("惟留风月");
        articleVO.setBannerImage("image/shuyuanji/nanke.jpg");
        articleVO.setMusic("nanke");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_03_04.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_03_03");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_03_05");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_03_05")
    public String getShuyuanji0305(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("惟留风月");
        articleVO.setBannerImage("image/shuyuanji/meiyinzhiquxiaonongxia.jpg");
        articleVO.setMusic("meiyinzhiquxiaonongxia");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_03_05.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_03_04");
        articleVO.setNextSectionUrl("/sixia_shuyuanji_04_01");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

    @GetMapping("/sixia_shuyuanji_04_01")
    public String getShuyuanji0401(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("掌中烛火");
        articleVO.setBannerImage("image/shuyuanji/tidengzhaoshanhe.jpg");
        articleVO.setMusic("tidengzhaoshanhe");
        articleVO.setSourceFile("/sixia_shuyuanji/shuyuanji_04_01.md");
        articleVO.setPreSectionUrl("/sixia_shuyuanji_03_04");
        model.addAttribute("articleVO", articleVO);
        return "sixia_shuyuanji";
    }

}
