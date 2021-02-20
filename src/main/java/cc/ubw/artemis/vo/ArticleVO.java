package cc.ubw.artemis.vo;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2019/12/12 0012
 * Time: 13:33
 * To change this template use File | Settings | File Templates.
 * Description: be used to show the article
 */
public class ArticleVO {
    private String title;
    private String music;
    private String bannerImage;
    private String sourceFile;
    private String preSectionUrl;
    private String nextSectionUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getPreSectionUrl() {
        return preSectionUrl;
    }

    public void setPreSectionUrl(String preSectionUrl) {
        this.preSectionUrl = preSectionUrl;
    }

    public String getNextSectionUrl() {
        return nextSectionUrl;
    }

    public void setNextSectionUrl(String nextSectionUrl) {
        this.nextSectionUrl = nextSectionUrl;
    }
}
