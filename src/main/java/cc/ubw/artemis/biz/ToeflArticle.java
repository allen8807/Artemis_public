package cc.ubw.artemis.biz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;


/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2020/9/11 0011
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class ToeflArticle implements Comparable<ToeflArticle> {
    private String id;
    private String title;
    private String txtBody;
    @JsonIgnore
    private int tpoNum;
    @JsonIgnore
    private ToeflTypeEnum artType;
    @JsonIgnore
    private int artNum;
    @JsonIgnore
    private ToeflTypeEnum readOrListenType;
    @JsonIgnore
    private ToeflArtAnalysisResult taar;


    public ToeflArticle() {

    }

    public ToeflArticle(String id, String title, String txtBody) {
        this.setId(id);
        this.setTitle(title);
        this.setTxtBody(txtBody);
        this.setTaar(null);
        AnalyseToeflArticle.analyseOneArticle(this);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        String t;
        if (StringUtils.isBlank(id)) {
            t = "";
        } else {
            t = id.trim();
        }
        this.id = t;
        if (StringUtils.isBlank(t)) {
            return;
        }

        String tokens[] = t.split("_");
        if (tokens != null && tokens.length == 5 && tokens[0].equals("O")) {
            this.tpoNum = NumberUtils.toInt(tokens[1]);
            this.artType = ToeflTypeEnum.getEnumById(tokens[2]);
            this.artNum = NumberUtils.toInt(tokens[3]);
            this.readOrListenType = ToeflTypeEnum.getEnumById(tokens[4]);
        } else {
            this.tpoNum = 0;
            this.artType = ToeflTypeEnum.OTHER;
            this.artNum = 0;
            this.readOrListenType = ToeflTypeEnum.OTHER;
        }
    }

    public int getTpoNum() {
        return tpoNum;
    }

    public void setTpoNum(int tpoNum) {
        this.tpoNum = tpoNum;
    }

    public ToeflTypeEnum getArtType() {
        return artType;
    }

    public void setArtType(ToeflTypeEnum artType) {
        this.artType = artType;
    }

    public int getArtNum() {
        return artNum;
    }

    public void setArtNum(int artNum) {
        this.artNum = artNum;
    }

    public ToeflTypeEnum getReadOrListenType() {
        return readOrListenType;
    }

    public void setReadOrListenType(ToeflTypeEnum readOrListenType) {
        this.readOrListenType = readOrListenType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String t;
        if (StringUtils.isBlank(title)) {
            t = "";
        } else {
            t = title.trim();
        }
        this.title = t;
    }

    public String getTxtBody() {
        return txtBody;
    }

    public void setTxtBody(String txtBody) {
        this.txtBody = txtBody;
    }


    public String allToString() {
        return "ToeflArticle{" +
                "id='" + id + '\'' +
                ", tpoNum=" + tpoNum +
                ", artType=" + artType +
                ", artNum=" + artNum +
                ", readOrListenType=" + readOrListenType +
                ", title='" + title + '\'' +
                ", txtBody='" + txtBody + '\'' +
                ", taar=" + taar +
                '}';
    }

    public String showArticle() {
        String res =
                "id=\r\n\t\t" + id +
                        "\r\ntpoNum=\r\n\t\t" + tpoNum +
                        "\r\nartType=\r\n\t\t" + artType +
                        "\r\nartNum=\r\n\t\t" + artNum +
                        "\r\nreadOrListenType=\r\n\t\t" + readOrListenType +
                        "\r\ntitle=\r\n\t\t\t\t" + title +
                        "\r\ntxtBody=\r\n\r\n" + txtBody +
                        "\r\ntaar=\r\n\r\n";
        if (taar != null) {
            res = res + taar.toFormatString();
        } else {
            res = res + "null";
        }
        return res;
    }

    public ToeflArtAnalysisResult getTaar() {
        return taar;
    }

    public void setTaar(ToeflArtAnalysisResult taar) {
        this.taar = taar;
    }

    @Override
    public int compareTo(ToeflArticle o) {
        int res = 1;
        if (o != null) {
            res = this.getId().compareTo(o.getId());
        }
        return res;
    }
}
