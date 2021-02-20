package cc.ubw.artemis.controller;

import cc.ubw.artemis.vo.ArticleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2019/12/23 0023
 * Time: 20:12
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Controller
public class SixiaController {
    @GetMapping("/sixia_00")
    public String getSixia00(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("最司长夏");
        articleVO.setSourceFile("/sixia/sixia_00_contents.md");
        articleVO.setPreSectionUrl("");
        articleVO.setNextSectionUrl("/sixia_01");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

    @GetMapping("/sixia_01")
    public String getSixia01(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("大王自述三篇");
        articleVO.setSourceFile("/sixia/sixia_01_zishu.md");
        articleVO.setPreSectionUrl("/sixia_00");
        articleVO.setNextSectionUrl("/sixia_02");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

    @GetMapping("/sixia_02")
    public String getSixia02(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("歌曲列表");
        articleVO.setSourceFile("/sixia/sixia_02_song_list.md");
        articleVO.setPreSectionUrl("/sixia_01");
        articleVO.setNextSectionUrl("/sixia_zagan_01");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

    @GetMapping("/sixia_zagan_01")
    public String getSixiaZagan01(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("无题");
        articleVO.setSourceFile("/sixia/sixia_zagan_01_wuti.md");
        articleVO.setPreSectionUrl("/sixia_02");
        articleVO.setNextSectionUrl("/sixia_zagan_02");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

    @GetMapping("/sixia_zagan_02")
    public String getSixiaZagan02(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("家与世界");
        articleVO.setSourceFile("/sixia/sixia_zagan_02_jysj.md");
        articleVO.setPreSectionUrl("/sixia_zagan_01");
        articleVO.setNextSectionUrl("/sixia_zagan_03");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

    @GetMapping("/sixia_zagan_03")
    public String getSixiaZagan03(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("我们都是猫");
        articleVO.setSourceFile("/sixia/sixia_zagan_03_wmdsm.md");
        articleVO.setPreSectionUrl("/sixia_zagan_02");
        articleVO.setNextSectionUrl("/sixia_zagan_04");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

    @GetMapping("/sixia_zagan_04")
    public String getSixiaZagan04(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("时间概念");
        articleVO.setSourceFile("/sixia/sixia_zagan_04_sjgn.md");
        articleVO.setPreSectionUrl("/sixia_zagan_03");
        articleVO.setNextSectionUrl("/sixia_note_01");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

    @GetMapping("/sixia_note_01")
    public String getSixiaNote01(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("时间概念");
        articleVO.setSourceFile("/sixia/sixia_note_01_smbnxtl.md");
        articleVO.setPreSectionUrl("/sixia_zagan_04");
        articleVO.setNextSectionUrl("/sixia_note_02");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

    @GetMapping("/sixia_note_02")
    public String getSixiaNote02(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("时间概念");
        articleVO.setSourceFile("/sixia/sixia_note_02_smssm.md");
        articleVO.setPreSectionUrl("/sixia_note_01");
        articleVO.setNextSectionUrl("/sixia_yingji_01");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

    @GetMapping("/sixia_yingji_01")
    public String getSixiaYingji01(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("最近很好");
        articleVO.setSourceFile("/sixia/sixia_yingji_01_zjhh.md");
        articleVO.setPreSectionUrl("/sixia_note_02");
        articleVO.setNextSectionUrl("/sixia_yingji_02");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

    @GetMapping("/sixia_yingji_02")
    public String getSixiaYingji02(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("受TI的感染，决定开始把一些平时的想法写在某个空间里");
        articleVO.setSourceFile("/sixia/sixia_yingji_02_stigr.md");
        articleVO.setPreSectionUrl("/sixia_yingji_01");
        articleVO.setNextSectionUrl("/sixia_yingji_03");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

    @GetMapping("/sixia_yingji_03")
    public String getSixiaYingji03(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("冒个泡……");
        articleVO.setSourceFile("/sixia/sixia_yingji_03_mgp.md");
        articleVO.setPreSectionUrl("/sixia_yingji_02");
        articleVO.setNextSectionUrl("/sixia_yingji_04");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

    @GetMapping("/sixia_yingji_04")
    public String getSixiaYingji04(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("想把这段对话记下来。希望很多年后，我依然是这么想的。");
        articleVO.setSourceFile("/sixia/sixia_yingji_04_xbzdh.md");
        articleVO.setPreSectionUrl("/sixia_yingji_03");
//        articleVO.setNextSectionUrl("/sixia_yingji_02");
        model.addAttribute("articleVO", articleVO);
        return "sixia";
    }

}
