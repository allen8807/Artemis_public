package cc.ubw.artemis.biz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2020/2/6 0006
 * Time: 0:52
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class SixiaSongs {
    //翻唱歌曲
    private final static List<String> coverSongs;
    //原创歌曲
    private final static List<String> originalSongs;

    static {
        coverSongs = new ArrayList<>();
        coverSongs.add("/sixia_song_20200130_c_wgsx");
        coverSongs.add("/sixia_song_20200124_c_dfz");
        coverSongs.add("/sixia_song_20120914_c_qq");


        originalSongs = new ArrayList<>();
    }

    public static List<String> getCoverSongs() {
        return coverSongs;
    }


    public static List<String> getOriginalSongs() {
        return originalSongs;
    }


}
