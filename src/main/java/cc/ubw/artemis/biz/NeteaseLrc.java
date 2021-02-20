package cc.ubw.artemis.biz;

import cc.ubw.artemis.commom.JSONUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2019/11/23 0023
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
 * Description:
 * 该文件参考以下两个项目修改
 * https://github.com/lyandyhk/NetEaseLyric
 * https://github.com/yuki-ryoko/Kazumi-Netease-Lyrics-Helper
 * Kazumi Netease Lyrics Helper v0.3.0
 * <p>
 * 根据网易云音乐分享链接，下载并保存歌词。
 * ====
 * yuki_ryoko@kotori.moe
 * github.com/yuki-ryoko/Kazumi-Netease-Lyrics-Helper
 */
public class NeteaseLrc {
    private static final Log LOG = LogFactory.getLog(NeteaseLrc.class);
    private LinkedList<String> originalLrcList = new LinkedList<>();
    private LinkedList<String> translationLrcList = new LinkedList<>();
    private String finalLrc = "";
    private String songId = "";

    public NeteaseLrc(String songId) {
        this.setSongId(songId);
        //    LOG.info("song id is "+ this.getSongId());
    }

    public void generateFinalLrc() {
        // 获取并处理原歌词
        lrcProcessor(getOriginalLrc(getSongId(), false), originalLrcList);

        // 获取并处理翻译歌词
        lrcProcessor(getOriginalLrc(getSongId(), true), translationLrcList);
        // 组合原歌词与翻译歌词
        if (translationLrcList.size() > 0) {

            for (int i = 0; i < originalLrcList.size(); i++) {

                boolean isFindTransLrc = false;

                for (int j = 0; j < translationLrcList.size(); j++) {

                    // 对比时间戳
                    String originalLrcTimestamp = originalLrcList.get(i).substring(0, originalLrcList.get(i).indexOf("]") + 1);
                    String translationLrcTimestamp = translationLrcList.get(j).substring(0, translationLrcList.get(j).indexOf("]") + 1);

                    //由于时间戳可能存在小数点后面有0的情况，这里做转换处理
                    String regEx = "[\\[\\]]";
                    String regExFirst0 = "^0*";
                    String regExLast0 = "0+?$";
                    //去掉括号和开头的0
                    String orgValue = originalLrcTimestamp.replaceAll(regEx, "").replaceFirst(regExFirst0, "");
                    //如果有小数点，去掉末尾的0
                    if (orgValue.indexOf(".") > 0) {
                        orgValue = orgValue.replaceAll("0+?$", "");
                    }
                    String traValue = translationLrcTimestamp.replaceAll(regEx, "").replaceFirst(regExFirst0, "");
                    ;
                    if (traValue.indexOf(".") > 0) {
                        traValue = traValue.replaceAll("0+?$", "");
                    }
                    if (orgValue.equals(traValue)) {
                        finalLrc += (originalLrcList.get(i) + " / " + translationLrcList.get(j).replace(translationLrcTimestamp, "") + "\r\n");
                        isFindTransLrc = true;
                    }
                }

                if (!isFindTransLrc) {
                    setFinalLrc(getFinalLrc() + originalLrcList.get(i) + "\r\n");
                }
            }
        } else {
            for (int i = 0; i < originalLrcList.size(); i++) {
                setFinalLrc(getFinalLrc() + originalLrcList.get(i) + "\r\n");
            }
        }
    }

    // 获取原歌词
    private String getOriginalLrc(String songId, boolean isTranslationLrc) {

        if (isTranslationLrc) {
            LOG.info("正在获取翻译歌词...");
        } else {
            LOG.info("正在获取原歌词...");
        }

        String temp = null;

        try {
            URL localURL = null;

            if (isTranslationLrc) {
                localURL = new URL("https://music.163.com/api/song/lyric?os=pc&id=" + songId + "&tv=-1");
            } else {
                localURL = new URL("http://music.163.com/api/song/media?id=" + songId);
            }

            InputStream localInputStream = localURL.openStream();
            InputStreamReader localInputStreamReader = new InputStreamReader(localInputStream, "utf-8");
            BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);

            String str = null;
            while ((str = localBufferedReader.readLine()) != null) {
                temp = str;
            }
            localBufferedReader.close();
            localInputStreamReader.close();
            localInputStream.close();
        } catch (Exception localException) {
            if (isTranslationLrc) {
                LOG.error("获取翻译歌词失败: ", localException);
            } else {
                LOG.error("获取原歌词失败: ", localException);
            }
        }

        return temp;
    }


    // 处理歌词
    private void lrcProcessor(String lrc, LinkedList<String> mLrcList) {
        String[] mLrc = null;

        LOG.debug(lrc);
        // 处理原歌词非歌词数据
        if (mLrcList == originalLrcList) {
            OriginalLrcBean originalLrcBean = JSONUtils.parseObj(lrc, OriginalLrcBean.class);
            if (null != originalLrcBean && null != originalLrcBean.lyric) {
                LOG.info("获取原歌词成功");
                mLrc = originalLrcBean.lyric.split("\n");
            } else {
                LOG.info("抱歉，没歌词");
                return;
            }
        }

        // 处理翻译歌词非歌词数据
        else {
            TranslationLrcBean translationLrcBean = JSONUtils.parseObj(lrc, TranslationLrcBean.class);
            if (null != translationLrcBean && null != translationLrcBean.tlyric.lyric) {
                LOG.info("获取翻译歌词成功");
                mLrc = translationLrcBean.tlyric.lyric.split("\n");
            } else {
                LOG.info("抱歉，没翻译歌词");
                return;
            }
        }

        // 处理歌词数据
        for (int i = 0; i < mLrc.length; i++) {

            if (mLrc[i].indexOf("][") >= 0) {
                String[] temp = mLrc[i].split("]");
                if (!mLrc[i].substring(mLrc[i].length() - 1, mLrc[i].length()).equals("]")) {
                    for (int j = 0; j < temp.length - 1; j++) {
                        mLrcList.add(temp[j] + "]" + temp[temp.length - 1]);
                    }
                } else {
                    for (int j = 0; j < temp.length - 1; j++) {
                        mLrcList.add(temp[j] + "] ");
                    }
                }
            } else {
                mLrcList.add(mLrc[i]);
            }
        }
    }


    public String getFinalLrc() {
        return finalLrc;
    }

    public void setFinalLrc(String finalLrc) {
        this.finalLrc = finalLrc;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }


    // 原歌词 JavaBean
    public static class OriginalLrcBean {
        public String lyric;
    }

    // 翻译歌词 JavaBean
    public static class TranslationLrcBean {
        public Tlyric tlyric;

        public class Tlyric {
            public String lyric;
        }
    }
}
