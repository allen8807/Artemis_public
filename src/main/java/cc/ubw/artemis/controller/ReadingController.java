package cc.ubw.artemis.controller;

import cc.ubw.artemis.vo.ArticleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2019/12/23 0023
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Controller
public class ReadingController {
    @GetMapping("/reading_00")
    public String getReading00(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("目录");
        articleVO.setSourceFile("/reading/notes_00_contents.md");
        articleVO.setPreSectionUrl("");
        articleVO.setNextSectionUrl("/reading_201912_01_zgzxjs");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }

    @GetMapping("/reading_201912_01_zgzxjs")
    public String getReading01(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("冯友兰-中国哲学简史");
        articleVO.setSourceFile("/reading/notes_201912_01_zgzxjs.md");
        articleVO.setPreSectionUrl("/reading_00");
        articleVO.setNextSectionUrl("/reading_201912_02_jjxyl7wg");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }

    @GetMapping("/reading_201912_02_jjxyl7wg")
    public String getReading02(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("曼昆-经济学原理:微观经济学分册");
        articleVO.setSourceFile("/reading/notes_201912_02_jjxyl7wg.md");
        articleVO.setPreSectionUrl("/reading_201912_01_zgzxjs");
        articleVO.setNextSectionUrl("/reading_202001_01_jjxyl7hg");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }

    @GetMapping("/reading_202001_01_jjxyl7hg")
    public String getReading03(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("曼昆-经济学原理:宏观经济学分册");
        articleVO.setSourceFile("/reading/notes_202001_01_jjxyl7hg.md");
        articleVO.setPreSectionUrl("/reading_201912_02_jjxyl7wg");
        articleVO.setNextSectionUrl("/reading_202001_02_rldgs");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }

    @GetMapping("/reading_202001_02_rldgs")
    public String getReading04(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("房龙-人类的故事");
        articleVO.setSourceFile("/reading/notes_202001_02_rldgs.md");
        articleVO.setPreSectionUrl("/reading_202001_01_jjxyl7hg");
        articleVO.setNextSectionUrl("/reading_202001_04_tl_xfzxs");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }

    @GetMapping("/reading_202001_04_tl_xfzxs")
    public String getReading05(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("梯利-西方哲学史-上册");
        articleVO.setSourceFile("/reading/notes_202001_03_tl_xfzxs_s.md");
        articleVO.setPreSectionUrl("/reading_202001_02_rldgs");
        articleVO.setNextSectionUrl("/reading_202001_04_1984");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }

    @GetMapping("/reading_202001_04_1984")
    public String getReading06(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("奥威尔-1984");
        articleVO.setSourceFile("/reading/notes_202001_04_1984.md");
        articleVO.setPreSectionUrl("/reading_202001_04_tl_xfzxs");
        articleVO.setNextSectionUrl("/reading_202002_01_hlm");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }

    @GetMapping("/reading_202002_01_hlm")
    public String getReading07(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("曹雪芹-红楼梦");
        articleVO.setSourceFile("/reading/notes_202002_01_hlm.md");
        articleVO.setPreSectionUrl("/reading_202001_04_1984");
        articleVO.setNextSectionUrl("/reading_202002_02_wedh");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }

    @GetMapping("/reading_202002_02_wedh")
    public String getReading08(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("梭罗-瓦尔登湖");
        articleVO.setSourceFile("/reading/notes_202002_02_wedh.md");
        articleVO.setPreSectionUrl("/reading_202002_01_hlm");
        articleVO.setNextSectionUrl("/reading_202003_01_mlxsj");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }

    @GetMapping("/reading_202003_01_mlxsj")
    public String getReading09(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("赫胥黎-美丽新世界");
        articleVO.setSourceFile("/reading/notes_202003_01_mlxsj.md");
        articleVO.setPreSectionUrl("/reading_202002_02_wedh");
        articleVO.setNextSectionUrl("/reading_202003_02_whzz");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }

    @GetMapping("/reading_202003_02_whzz")
    public String getReading10(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("勒庞-乌合之众");
        articleVO.setSourceFile("/reading/notes_202003_02_whzz.md");
        articleVO.setPreSectionUrl("/reading_202003_01_mlxsj");
        articleVO.setNextSectionUrl("/reading_202003_03_wm");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }

    @GetMapping("/reading_202003_03_wm")
    public String getReading11(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("扎米亚金-我们");
        articleVO.setSourceFile("/reading/notes_202003_03_wm.md");
        articleVO.setPreSectionUrl("/reading_202003_02_whzz");
        articleVO.setNextSectionUrl("/reading_202003_04_rjsg");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }

    @GetMapping("/reading_202003_04_rjsg")
    public String getReading12(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("太宰治-人间失格");
        articleVO.setSourceFile("/reading/notes_202003_04_rjsg.md");
        articleVO.setPreSectionUrl("/reading_202003_03_wm");
//        articleVO.setNextSectionUrl("/reading_201912_01");
        model.addAttribute("articleVO", articleVO);
        return "reading";
    }
}
