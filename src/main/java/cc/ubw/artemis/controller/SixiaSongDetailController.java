package cc.ubw.artemis.controller;

import cc.ubw.artemis.vo.ArticleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2020/2/5 0005
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Controller
public class SixiaSongDetailController {
    @GetMapping("/sixia_song_00_content")
    public String getSixiaSong00Content(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("长夏闻浅歌");
        articleVO.setSourceFile("/sixia/sixia_02_song_list.md");
        articleVO.setPreSectionUrl("");
        articleVO.setNextSectionUrl("/sixia_song_20200606_c_hd");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200606_c_hd")
    public String getSixiaSongHd(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("海底");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200606_c_hd.md");
        articleVO.setPreSectionUrl("/sixia_song_00_content");
        articleVO.setNextSectionUrl("/sixia_song_20200523_oc_jhrm");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200523_oc_jhrm")
    public String getSixiaSongJhrm(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("江湖入梦");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200523_oc_jhrm.md");
        articleVO.setPreSectionUrl("/sixia_song_00_content");
        articleVO.setNextSectionUrl("/sixia_song_20200523_o_sczx");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200523_o_sczx")
    public String getSixiaSongSczx(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("山茶照雪");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200523_o_sczx.md");
        articleVO.setPreSectionUrl("/sixia_song_20200523_oc_jhrm");
        articleVO.setNextSectionUrl("/sixia_song_20200505_c_gswx");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200505_c_gswx")
    public String getSixiaSongGswx(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("歌虽无形（中日双版）");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200505_c_gswx.md");
        articleVO.setPreSectionUrl("/sixia_song_20200523_o_sczx");
        articleVO.setNextSectionUrl("/sixia_song_20200501_o_jxy");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200501_o_jxy")
    public String getSixiaSongJxy(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("江心月");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200501_o_jxy.md");
        articleVO.setPreSectionUrl("/sixia_song_20200505_c_gswx");
        articleVO.setNextSectionUrl("/sixia_song_20200417_o_ss");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200417_o_ss")
    public String getSixiaSongSs(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("双双");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200417_o_ss.md");
        articleVO.setPreSectionUrl("/sixia_song_00_content");
        articleVO.setNextSectionUrl("/sixia_song_20200409_c_fhcb");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200409_c_fhcb")
    public String getSixiaSongFhcb(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("繁华唱遍");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200409_c_fhcb.md");
        articleVO.setPreSectionUrl("/sixia_song_20200417_o_ss");
        articleVO.setNextSectionUrl("/sixia_song_20200329_c_as");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200329_c_as")
    public String getSixiaSongAs(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("爱殇");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200329_c_as.md");
        articleVO.setPreSectionUrl("/sixia_song_20200409_c_fhcb");
        articleVO.setNextSectionUrl("/sixia_song_20200314_c_gx");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200314_c_gx")
    public String getSixiaSongGx(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("归寻");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200314_c_gx.md");
        articleVO.setPreSectionUrl("/sixia_song_20200329_c_as");
        articleVO.setNextSectionUrl("/sixia_song_20200306_c_ssd");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200306_c_ssd")
    public String getSixiaSongSsd(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("水上灯");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200306_c_ssd.md");
        articleVO.setPreSectionUrl("/sixia_song_20200314_c_gx");
        articleVO.setNextSectionUrl("/sixia_song_20200228_c_cmxsh");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200228_c_cmxsh")
    public String getSixiaSongCmxsh(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("吹灭小山河");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200228_c_cmxsh.md");
        articleVO.setPreSectionUrl("/sixia_song_20200306_c_ssd");
        articleVO.setNextSectionUrl("/sixia_song_20200223_c_hspdz");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200223_c_hspdz")
    public String getSixiaSongHspdz(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("横竖撇点折");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200223_c_hspdz.md");
        articleVO.setPreSectionUrl("/sixia_song_20200228_c_cmxsh");
        articleVO.setNextSectionUrl("/sixia_song_20200215_oc_dgda");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200215_oc_dgda")
    public String getSixiaSongDgda(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("大国大爱");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200215_oc_dgda.md");
        articleVO.setPreSectionUrl("/sixia_song_20200223_c_hspdz");
        articleVO.setNextSectionUrl("/sixia_song_20200214_c_swzzdqz");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200214_c_swzzdqz")
    public String getSixiaSongSwzzdqz(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("是我在做多情种");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200214_c_swzzdqz.md");
        articleVO.setPreSectionUrl("/sixia_song_20200215_oc_dgda");
        articleVO.setNextSectionUrl("/sixia_song_20200130_c_wgsx");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200207_c_cl")
    public String getSixiaSongCl(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("赤伶");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200207_c_cl.md");
        articleVO.setPreSectionUrl("/sixia_song_00_content");
        articleVO.setNextSectionUrl("/sixia_song_20200130_c_wgsx");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200130_c_wgsx")
    public String getSixiaSongWgsx(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("万古生香");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200130_c_wgsx.md");
        articleVO.setPreSectionUrl("/sixia_song_20200207_c_cl");
        articleVO.setNextSectionUrl("/sixia_song_20200124_c_dfz");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200124_c_dfz")
    public String getSixiaSongDfz(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("东风志");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200124_c_dfz.md");
        articleVO.setPreSectionUrl("/sixia_song_20200130_c_wgsx");
        articleVO.setNextSectionUrl("/sixia_song_20200117_o_mhjp");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200117_o_mhjp")
    public String getSixiaSongMhjp(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("梦魂久抛");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200117_o_mhjp.md");
        articleVO.setPreSectionUrl("/sixia_song_20200124_c_dfz");
        articleVO.setNextSectionUrl("/sixia_song_20200110_c_grt");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200110_c_grt")
    public String getSixiaSongGrt(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("故人叹");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200110_c_grt.md");
        articleVO.setPreSectionUrl("/sixia_song_20200117_o_mhjp");
        articleVO.setNextSectionUrl("/sixia_song_20200105_c_yyxz");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20200105_c_yyxz")
    public String getSixiaSongYyxz(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("烟雨行舟");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20200105_c_yyxz.md");
        articleVO.setPreSectionUrl("/sixia_song_20200110_c_grt");
        articleVO.setNextSectionUrl("/sixia_song_20191229_c_symx");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20191229_c_symx")
    public String getSixiaSongSymx(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("山有木兮");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20191229_c_symx.md");
        articleVO.setPreSectionUrl("/sixia_song_20200105_c_yyxz");
        articleVO.setNextSectionUrl("/sixia_song_20191221_c_blm");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20191221_c_blm")
    public String getSixiaSongBlm(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("不老梦");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20191221_c_blm.md");
        articleVO.setPreSectionUrl("/sixia_song_20191229_c_symx");
        articleVO.setNextSectionUrl("/sixia_song_20191214_c_wj");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20191214_c_wj")
    public String getSixiaSongWj(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("无羁");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20191214_c_wj.md");
        articleVO.setPreSectionUrl("/sixia_song_20191221_c_blm");
        articleVO.setNextSectionUrl("/sixia_song_20191208_dfb");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20191208_dfb")
    public String getSixiaSongDfb(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("定风波");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20191208_dfb.md");
        articleVO.setPreSectionUrl("/sixia_song_20191214_c_wj");
        articleVO.setNextSectionUrl("/sixia_song_20191129_c_qfl");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20191129_c_qfl")
    public String getSixiaSongQfl(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("起风了");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20191129_c_qfl.md");
        articleVO.setPreSectionUrl("/sixia_song_20191208_dfb");
        articleVO.setNextSectionUrl("/sixia_song_20191122_c_jwz");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20191122_c_jwz")
    public String getSixiaSongJwz(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("九万字");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20191122_c_jwz.md");
        articleVO.setPreSectionUrl("/sixia_song_20191129_c_qfl");
        articleVO.setNextSectionUrl("/sixia_song_20191117_c_mwgq");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20191117_c_mwgq")
    public String getSixiaSongMwgq(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("莫问归期");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20191117_c_mwgq.md");
        articleVO.setPreSectionUrl("/sixia_song_20191122_c_jwz");
        articleVO.setNextSectionUrl("/sixia_song_20120914_c_qq");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }

    @GetMapping("/sixia_song_20120914_c_qq")
    public String getSixiaSongQQ(Model model) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle("芊芊");
        articleVO.setSourceFile("/sixia_songs/sixia_song_20120914_c_qq.md");
        articleVO.setPreSectionUrl("/sixia_song_20191117_c_mwgq");
//        articleVO.setNextSectionUrl("/sixia_01");
        model.addAttribute("articleVO", articleVO);
        return "sixia_song_detail";
    }
}
