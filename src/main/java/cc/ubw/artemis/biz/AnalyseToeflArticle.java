package cc.ubw.artemis.biz;

import cc.ubw.artemis.commom.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2020/9/11 0011
 * Time: 18:36
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class AnalyseToeflArticle {
    private static final Log LOG = LogFactory.getLog(AnalyseToeflArticle.class);
    private static Map<String, String> artJsonFileMap = new HashMap<>();
    private static Map<String, ToeflArticle> toeflArtMap = new HashMap<>();
    private static List<ToeflArticle> toeflArtList = new ArrayList<>();

    static {
        refreshArticles();
    }

    public static void refreshArticles() {
        readArticles();
        constructToeflArtCollections();
    }

    public static String showTableDesc() {
        return "将下列数据保存成csv文件,导入Excel，方便处理数据。windows下直接打开csv文件会有中文乱码。\n\n\n";
    }

    public static String showAllListByTable() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-14s", "ID"))
                .append(String.format("%-12s", ",TPO编号"))
                .append(String.format("%-12s", ",题型"))
                .append(String.format("%-10s", ",题型编号"))
                .append(String.format("%-8s", ",阅读听力类型"))
                .append(String.format("%-11s", ",总词数"))
                .append(String.format("%-9s", ",去重后词数"))
                .append(String.format("%-10s", ",不重复率"))
                .append(",标题\n");
        for (ToeflArticle ta :
                getToeflArtList()) {
            Long totalWordNum = 0L;
            Integer uniqueWordNum = 0;
            Double rate = 0d;
            if (ta.getTaar() != null) {
                totalWordNum = ta.getTaar().getTotalWordNum();
                uniqueWordNum = ta.getTaar().getUniqueWordNum();
                rate = ta.getTaar().getRate();
            }
            sb.append(String.format("%-14s", ta.getId()))
                    .append(String.format("%-14s", "," + ta.getTpoNum()))
                    .append(String.format("%-14s", "," + ta.getArtType().getId()))
                    .append(String.format("%-14s", "," + ta.getArtNum()))
                    .append(String.format("%-14s", "," + ta.getReadOrListenType().getId()))
                    .append(String.format("%-14s", "," + totalWordNum))
                    .append(String.format("%-14s", "," + uniqueWordNum))
                    .append(String.format("%-14s", "," + String.format("%.6f", rate)))
                    .append(String.format("%-14s", "," + ta.getTitle()))
                    .append("\n");
        }
        return sb.toString();
    }

    public static ToeflArtAnalysisResult analyseArticleListByIdList(List<String> idList) {
        List<ToeflArticle> taList = new ArrayList<>();
        for (String id :
                idList) {
            ToeflArticle ta = getToeflArtMap().get(id);
            if (ta != null) {
                taList.add(ta);
            }
        }
        return analyseArticleList(taList);
    }

    public static void readArticles() {

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Map<String, String> artJsonFileMapTmp = new HashMap<>();
        try {
            Resource[] resources = resolver.getResources("classpath:static/toefl/*.json");
            for (Resource resource : resources) {
                String string = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
                String filename = resource.getFilename();
                artJsonFileMapTmp.put(filename, string);
            }
        } catch (IOException e) {
            LOG.error("[readArticles] error:", e);
            e.printStackTrace();
        }
        artJsonFileMap.clear();
        artJsonFileMap = artJsonFileMapTmp;

    }

    public static void constructToeflArtCollections() {
        Map<String, ToeflArticle> toeflArtMapTmp = new HashMap<>();
        List<ToeflArticle> toeflArtListTmp = new ArrayList<>();
        for (Map.Entry<String, String> entry : artJsonFileMap.entrySet()) {
//            ObjectMapper om = new ObjectMapper();
//            ToeflArticle ta = om.readValue(entry.getValue(), ToeflArticle.class);
            ToeflArticle ta = JSONUtils.parseObj(entry.getValue(), ToeflArticle.class);
            if (ta != null) {
                analyseOneArticle(ta);
                toeflArtMapTmp.put(ta.getId(), ta);
                toeflArtListTmp.add(ta);
            }
        }
//        toeflArtListTmp.sort
        Collections.sort(toeflArtListTmp);
        getToeflArtMap().clear();
        toeflArtMap = toeflArtMapTmp;
        getToeflArtList().clear();
        toeflArtList = toeflArtListTmp;
    }

    //合并分析结果
    public static ToeflArtAnalysisResult analyseArticleList(List<ToeflArticle> taList) {
        ToeflArtAnalysisResult taarRes = new ToeflArtAnalysisResult();
        if (CollectionUtils.isEmpty(taList)) {
            return taarRes;
        }
        if (taList.size() == 1) {
            return analyseOneArticle(taList.get(0));
        }
        Map<String, Long> resWordMap = taarRes.getWordMap();
        Long totalWordNumRes = taarRes.getTotalWordNum();
        for (ToeflArticle ta :
                taList) {
            ToeflArtAnalysisResult taar = analyseOneArticle(ta);
            Map<String, Long> newWordMap = taar.getWordMap();
            totalWordNumRes = totalWordNumRes + taar.getTotalWordNum();
            for (Map.Entry<String, Long> entry : newWordMap.entrySet()) {
                String newKey = entry.getKey();
                if (resWordMap.containsKey(newKey)) {
                    Long num = resWordMap.get(newKey) + entry.getValue();
                    resWordMap.put(newKey, num);
                } else {
                    resWordMap.put(newKey, entry.getValue());
                }
            }
        }
        lastDealToeflArtAnalysisResult(taarRes,totalWordNumRes,resWordMap.size());

        return taarRes;
    }

    public static ToeflArtAnalysisResult analyseAllArticleList() {
        return analyseArticleList(getToeflArtList());
    }

    public static ToeflArtAnalysisResult analyseAllReadingArticleList() {
        List<ToeflArticle> toeflArtListTmp = new ArrayList<>();
        List<ToeflArticle> toeflArtListOrigin = getToeflArtList();
        for (ToeflArticle ta :
                toeflArtListOrigin) {
            if (ta != null && ta.getReadOrListenType() != null && ta.getReadOrListenType().equals(ToeflTypeEnum.R)) {
                toeflArtListTmp.add(ta);
            }
        }
        return analyseArticleList(toeflArtListTmp);
    }

    public static ToeflArtAnalysisResult analyseAllListeningArticleList() {
        List<ToeflArticle> toeflArtListTmp = new ArrayList<>();
        List<ToeflArticle> toeflArtListOrigin = getToeflArtList();
        for (ToeflArticle ta :
                toeflArtListOrigin) {
            if (ta != null && ta.getReadOrListenType() != null && ta.getReadOrListenType().equals(ToeflTypeEnum.L)) {
                toeflArtListTmp.add(ta);
            }
        }
        return analyseArticleList(toeflArtListTmp);
    }
    private static void  lastDealToeflArtAnalysisResult(ToeflArtAnalysisResult taar,Long totalWordNum,Integer uniqueNum){
        assembleToeflArtAnalysisResult(taar,totalWordNum,uniqueNum);
        lastStrictlyDealToeflArtAnalysisResult(taar);
    }

    private static void  assembleToeflArtAnalysisResult(ToeflArtAnalysisResult taar,Long totalWordNum,Integer uniqueNum){
        taar.setTotalWordNum(totalWordNum);
        taar.setUniqueWordNum(uniqueNum);
        taar.calcRate();
        //排序
        List<WordNum> list = new ArrayList<>();
        Map<String, Long> wordMap = taar.getWordMap();
        for (Map.Entry<String, Long> entry : wordMap.entrySet()) {
            WordNum wn = new WordNum(entry.getKey(), entry.getValue());
            list.add(wn);
        }
        Collections.sort(list);
        Collections.reverse(list);
        taar.setWordList(list);
        taar.setDar(Dictionary.analyseArtResultByNoOverlapDict(taar));
    }

    private static void  lastStrictlyDealToeflArtAnalysisResult(final ToeflArtAnalysisResult taar){
        if(taar == null){
            return;
        }
        if(CollectionUtils.isEmpty(taar.getWordList())){
            return;
        }
        ToeflArtAnalysisResult strictlyTaar;
        if(taar.getStrictlyTaar() == null){
            strictlyTaar = new ToeflArtAnalysisResult();
            taar.setStrictlyTaar(strictlyTaar);
        }else{
            strictlyTaar = taar.getStrictlyTaar();
            strictlyTaar.getWordMap().clear();
            strictlyTaar.getWordList().clear();
        }
        //利用原来的词构造字典，但是去掉所有复合词、缩写和变形词
        Set<String> dict = new HashSet<>();
        for (WordNum wn :
                taar.getWordList()) {
            String w = wn.getWord();
            if(Dictionary.isWordMaybeCompound(w) ||Dictionary.isWordMaybeAbbr(w) || Dictionary.isWordMaybeDeformed(w) ){
                continue;
            }
            dict.add(w);
        }
        Map<String, Long> wordMap = new HashMap<>();
        for (WordNum wn :
                taar.getWordList()) {
            String w = wn.getWord();
            String wU = Dictionary.lookUpWordInDict(w,dict);
            if(StringUtils.isNotBlank(wU)){
                addMapNum(wordMap,wU,wn.getSum());
            }else{
                addMapNum(wordMap,w,wn.getSum());
            }
        }
        strictlyTaar.setWordMap(wordMap);
        assembleToeflArtAnalysisResult(strictlyTaar,taar.getTotalWordNum(),wordMap.size());
    }

    private static void addMapNum(Map<String,Long> wordMap, String key, Long num){
        if(null == wordMap ){
            return;
        }
        if (wordMap.containsKey(key)) {
            Long value = wordMap.get(key);
            wordMap.put(key,value+ num );
        } else {
            wordMap.put(key, num);
        }
    }

    public static ToeflArtAnalysisResult analyseOneArticle(ToeflArticle ta) {
        if (ta.getTaar() != null) {
            return ta.getTaar();
        } else {
            return forceAnalyseOneArticle(ta);
        }
    }

    public static ToeflArtAnalysisResult forceAnalyseOneArticle(ToeflArticle ta) {
        ToeflArtAnalysisResult taar = new ToeflArtAnalysisResult();
        String dealStr;
        String title = ta.getTitle();
        String txtBody = ta.getTxtBody();
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(txtBody)) {
            dealStr = title + "  \r\n  " + txtBody;
        } else if (StringUtils.isNotBlank(txtBody)) {
            dealStr = txtBody;
        } else if (StringUtils.isNotBlank(title)) {
            dealStr = title;
        } else {
            return taar;
        }
        //替换中文单引号为英文'
        dealStr = dealStr.replaceAll("[‘’]","'");
        //大小写转换
        dealStr = dealStr.toLowerCase();
        String[] tokens = dealStr.split("[^a-zA-Z'\\-]");
        Long totalWordNum = 0L;
        Map<String, Long> wordMap = taar.getWordMap();
        for (String s :
                tokens) {
            boolean newFlag = true;
            if (StringUtils.isBlank(s)) {
                continue;
            }
            //处理单词，去掉首字母前的所有符号，防止单词只含有符号，或者是引号开头
            int i = 0;
            final int lenS = s.length();
            for (i = 0;i<lenS;i++){
                //前面已经全转化为小写了
                if(s.charAt(i)>='a'&&s.charAt(i)<='z'){
                    break;
                }
            }
            if(i>=lenS){
                //如果没有找到小写字母
                continue;
            }
            if(i!=0){
                s = s.substring(i,lenS);
            }

            String t = s.replaceAll("['\\-]","");
            if(StringUtils.isBlank(t)){
                continue;
            }

//            //处理连字符，连字符构成的分成独立单词处理
//            if(s.contains("-")) {
//                String[] ws = s.split("-");
//                for (String w :
//                        ws) {
//                    if (StringUtils.isBlank(w)) {
//                        continue;
//                    }
//                    totalWordNum++;
//                    if (wordMap.containsKey(w)) {
//                        Long num = wordMap.get(w);
//                        wordMap.put(s, num + 1);
//                    } else {
//                        wordMap.put(w, 1L);
//                    }
//                }
//            }else {
//                //不含连字符的直接处理
//                totalWordNum++;
//                if (wordMap.containsKey(s)) {
//                    Long num = wordMap.get(s);
//                    wordMap.put(s, num + 1);
//                } else {
//                    wordMap.put(s, 1L);
//                }
//            }
            //连字符还是不处理了，有些连字符的词是特殊用法
            totalWordNum++;
            addMapNum(wordMap,s,1L);
//                if (wordMap.containsKey(s)) {
//                    Long num = wordMap.get(s);
//                    wordMap.put(s, num + 1);
//                } else {
//                    wordMap.put(s, 1L);
//                }
        }
        lastDealToeflArtAnalysisResult(taar,totalWordNum,wordMap.size());
        ta.setTaar(taar);
        return taar;
    }


    public static Map<String, ToeflArticle> getToeflArtMap() {
        return toeflArtMap;
    }

    public static List<ToeflArticle> getToeflArtList() {
        return toeflArtList;
    }
}
