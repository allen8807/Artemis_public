package cc.ubw.artemis.biz;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2020/9/20 0020
 * Time: 12:52
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class Dictionary {
    private static final Log LOG = LogFactory.getLog(AnalyseToeflArticle.class);
    private static Map<DictEnum,Set<String>> dictionaryMap = new HashMap<>();
    private static Map<DictEnum,Integer> dictionaryWordNumMap = new HashMap<>();
    private static Map<String, String> dictFileMap = new HashMap<>();
    private final static Set<String> twoLettersWordsCouldDeform =new HashSet<>();
    private  static Set<String> allDictionary =new HashSet<>();
    static {

        //两字母的词一共有am,an,as,at,ax,by,do,go,he,hi,if,in,is,it,me,my,no,of,on,or,so,to,up,us,we,tv,ox,ha,ho,er,ma,uh,um,
        //去掉介词代词叹词等，剩下可以形变的只有ax,do,go,tv,ox,er;
        // 去掉缩写tv,er,又由于ox复数为oxen不规则，
        // 所以只保留ax,do,go
        twoLettersWordsCouldDeform.add("ax");
        twoLettersWordsCouldDeform.add("do");
        twoLettersWordsCouldDeform.add("go");
        refreshDictionaries();
    }

    public static void refreshDictionaries() {
        readDictionaries();
//        constructDictionaries();
        constructUniDictionaries();
    }

    private static void readDictionaries() {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Map<String, String> dictFileMapTmp = new HashMap<>();
        try {
            Resource[] resources = resolver.getResources("classpath:static/dictionary/*.dict");
            for (Resource resource : resources) {
                String string = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
                String filename = resource.getFilename();
                dictFileMapTmp.put(filename, string);
            }
        } catch (IOException e) {
            LOG.error("[readArticles] error:", e);
            e.printStackTrace();
        }
        dictFileMap.clear();
        dictFileMap = dictFileMapTmp;
    }

    private static void constructUniDictionaries() {
        //按枚举类的顺序构造字典，且保证字典不重复,这个不重复不用考虑变形问题
        Set<String> allDict = new HashSet<>();
        for (DictEnum dictEnum : DictEnum.values()) {
           String fileContent = dictFileMap.get(dictEnum.getFilename());
           if(StringUtils.isNotBlank(fileContent)){
               Set<String> wordSet = new HashSet<>();
               String[] words = fileContent.split("[^a-zA-Z'\\-]");
               for (String w :
                       words) {
                   if (StringUtils.isNotBlank(w)) {
                       w = w.trim();
                       if(!allDict.contains(w)){
                           allDict.add(w);
                           wordSet.add(w.trim());
                       }
//                       else {
//                           LOG.info("[constructUniDictionaries] " + dictEnum.getId() + " has duplicated word: " + w);
//                       }
                   }
               }
               dictionaryMap.put(dictEnum, wordSet);
               dictionaryWordNumMap.put(dictEnum,wordSet.size());
           }
        }
        //如果还有不在枚举类字典里的字典，统一放到OTHER里
        Set<String> otherSet = new HashSet<>();
        for (Map.Entry<String, String> entry : dictFileMap.entrySet()) {
            DictEnum dictEnum = DictEnum.getEnumByFilename(entry.getKey());
            if (dictEnum == null || dictEnum.equals(DictEnum.OTHER)) {
                if (StringUtils.isNotBlank(entry.getValue())) {
                    String[] words = entry.getValue().split("[^a-zA-Z'\\-]");
                    for (String w :
                            words) {
                        if (StringUtils.isNotBlank(w)) {
                            w = w.trim();
                            if(!allDict.contains(w)){
                                allDict.add(w);
                                otherSet.add(w.trim());
                            }
                        }
                    }
                }
            }
        }
        if( CollectionUtils.isNotEmpty(otherSet)){
            dictionaryMap.put(DictEnum.OTHER,otherSet);
            dictionaryWordNumMap.put(DictEnum.OTHER,otherSet.size());
        }
        allDictionary = allDict;
    }

    private static void constructDictionaries() {
        Set<String> allDict = new HashSet<>();
        //由于该模式下字典间不去重，所以这里的other里只能放最后一个
        for (Map.Entry<String, String> entry : dictFileMap.entrySet()) {
            DictEnum dictEnum = DictEnum.getEnumByFilename(entry.getKey());
            Set<String> wordSet = new HashSet<>();
            if (StringUtils.isNotBlank(entry.getValue())) {
                String[] words = entry.getValue().split("[^a-zA-Z'\\-]");
                for (String w :
                        words) {
                    if (StringUtils.isNotBlank(w)) {
                        wordSet.add(w.trim());
                    }
                }
            }
            dictionaryMap.put(dictEnum, wordSet);
            dictionaryWordNumMap.put(dictEnum,wordSet.size());
            allDict.addAll(wordSet);
        }
        allDictionary = allDict;
    }
    public static String lookUpWordInDictStrictly(final String word,Set<String> set){

        if(CollectionUtils.isEmpty(set)||StringUtils.isBlank(word)){
            return null;
        }
        if(set.contains(word)){
            return word;
        }else {
            return null;
        }
    }
    //总词典查询
    public static String lookUpWordInAllDictStrictly(final String word){
        if(StringUtils.isBlank(word)){
            return null;
        }
        if(allDictionary.contains(word)){
            return word;
        }else {
            return null;
        }
    }

    /**
     * 判断单词是否可能是复合词
     * @param wordOri
     * @return
     */
    public static boolean isWordMaybeCompound(final String wordOri) {
        if(StringUtils.isBlank(wordOri)){
            return false;
        }
        return (wordOri.lastIndexOf('-') != -1);
    }
    /**
     * 判断单词是否可能是缩写
     * @param wordOri
     * @return
     */
    public static boolean isWordMaybeAbbr(final String wordOri) {
        if(StringUtils.isBlank(wordOri)){
            return false;
        }
        return (wordOri.lastIndexOf('\'') != -1);
    }

    /**
     * 判断单词是否可能是变形词
     * @param wordOri
     * @return
     */
    public static boolean isWordMaybeDeformed(final String wordOri){
        if(StringUtils.isBlank(wordOri)){
            return false;
        }
        int len = wordOri.length();
        if(len <= 3){
            return false;
        }
        //复数
        if(wordOri.charAt(len-1) == 's'){
            return true;
        }
        //现在分词
        if(wordOri.charAt(len-3) == 'i' && wordOri.charAt(len-2) == 'n' && wordOri.charAt(len-1) == 'g' ){
            return true;
        }
        //过去分词
        if( wordOri.charAt(len-2) == 'e' && wordOri.charAt(len-1) == 'd' ){
            return true;
        }
        //比较级
        if( wordOri.charAt(len-2) == 'e' && wordOri.charAt(len-1) == 'r' ){
            return true;
        }
        //最高级
        if(wordOri.charAt(len-3) == 'e' && wordOri.charAt(len-2) == 's' && wordOri.charAt(len-1) == 't' ){
            return true;
        }
        // 副词
        if( wordOri.charAt(len-2) == 'l' && wordOri.charAt(len-1) == 'y' ){
            return true;
        }
        return false;
    }

    //这里只考虑规则的变形，有可能会把不同的词归到一个词上，不过概率比较低，就不处理了
    public static String lookUpWordInDict(final String wordOri, Set<String> set) {
        String res = null;
        if (CollectionUtils.isEmpty(set) || StringUtils.isBlank(wordOri)) {
            return null;
        }
        //如果是连词，直接走严格查询，跳过
        if( wordOri.lastIndexOf('-')!=-1){
            return lookUpWordInDictStrictly(wordOri,set);
        }

        // 如果含缩写， 如果超过两个则报错
        //测试代码超过两个报错
        String [] sss = wordOri.split("[']");
        if(sss.length >2){
            LOG.error("[lookUpWordInDict]too many abbr in wordOri:"+ wordOri);
        }

        //总词典查询结果
        String allRes = null;


        //处理加'的缩写问题，先严格匹配，匹配不到再进行处理
        int offset = wordOri.lastIndexOf('\'');
        int lenOri = wordOri.length();
        String wordDeal = wordOri;

        if(offset!=-1){
            String abbrRes = lookUpWordInDictStrictly(wordOri,set);
            if(StringUtils.isNotBlank(abbrRes)){
                return abbrRes;
            }else {
                allRes =  lookUpWordInAllDictStrictly(wordOri);
                if(StringUtils.isNotBlank(allRes)){
                    //总词典里有就不用再变形查询了
                    return  null;
                }
                if(lenOri > 3 && wordOri.charAt(lenOri-3) =='n'&&wordOri.charAt(lenOri-2) =='\''&&wordOri.charAt(lenOri-1) =='t'){
                    //如果是n't结尾的去掉n't
                    wordDeal = wordOri.substring(0,lenOri-3);
                }else {
                    String w1 = wordOri.substring(0, offset);
                    String w2 = wordOri.substring(offset + 1, wordOri.length());
                    //如果后面缩写部分长度大于2则保留，否则保留前面的。有可能是前面是缩写
                    if (w2.length() > 2) {
                        LOG.info("[lookUpWordInDict] abbr is much longer: " + wordDeal);
                        wordDeal = w2;
                    } else {
                        wordDeal = w1;
                    }
                }
            }
        }

        int len = wordDeal.length();
        String tmp = "";
        if (set.contains(wordDeal)) {
            return wordDeal;
        } else{
            allRes =  lookUpWordInAllDictStrictly(wordOri);
            if(StringUtils.isNotBlank(allRes)){
                //总词典里有就不用再变形查询了
                return  null;
            }
        }

        if (len > 3) {
            //只考虑长度大于3的，也就是说至少4个字母，变形后的词ax,do,go最短的三个也有四个字母
            //变形还原，
            // 1.名词单复数变形
            // 2.1动词第三人称单数变形
            // 由于名词单复数变形基本和动词第三人称单数变形一致，所以合成一个
            //加-s,-es,-ies,-ves还原
            if ( wordDeal.charAt(len - 1) == 's') {
                //-s,其实也包括结尾是e加s的
                tmp = wordDeal.substring(0, len - 1);
                res = checkDeformedWord(set,tmp);
                if (StringUtils.isBlank(res)) {
                    //-es
                    if (wordDeal.charAt(len - 2) == 'e') {
                        tmp = wordDeal.substring(0, len - 2);
                        res = checkDeformedWord(set,tmp);
                        if (StringUtils.isBlank(res)) {
                            //-ies
                            if (wordDeal.charAt(len - 3) == 'i') {
                                tmp = wordDeal.substring(0, len - 3);
                                res = checkDeformedWord(set,tmp + "y");
                            } else if (wordDeal.charAt(len - 3) == 'v') {
                                //-ves -> -f
                                tmp = wordDeal.substring(0, len - 3);
                                res = checkDeformedWord(set,tmp + "f");
                                if (StringUtils.isBlank(res)) {
                                    //-ves -> -fe
                                    tmp = wordDeal.substring(0, len - 3);
                                    res = checkDeformedWord(set,tmp + "fe");
                                }
                            }
                        }
                    }
                }
            }
            //2.2动词现在分词
            // -ing,去e加-ing,双写结尾加ing,ie变y加ing,c变ck加ing
            if (StringUtils.isBlank(res) && len > 4 && wordDeal.substring(len - 3, len).equals("ing")) {
                // -ing
                tmp = wordDeal.substring(0, len - 3);
                res = checkDeformedWord(set,tmp);
                if (StringUtils.isBlank(res)) {
                    //去e加-ing
                    tmp = wordDeal.substring(0, len - 3);
                    res = checkDeformedWord(set,tmp + "e");
                    if (StringUtils.isBlank(res)) {
                        if (wordDeal.charAt(len - 4) == 'y') {
                            //ie变y加ing
                            tmp = wordDeal.substring(0, len - 4);
                            res = checkDeformedWord(set,tmp + "ie");
                        }
                        if (StringUtils.isBlank(res) && len > 5) {
                            if (wordDeal.charAt(len - 4) == 'k' && wordDeal.charAt(len - 5) == 'c') {
                                //c变ck加ing
                                tmp = wordDeal.substring(0, len - 4);
                                res = checkDeformedWord(set,tmp);
                            }
                            if (StringUtils.isBlank(res) && wordDeal.charAt(len - 4) == wordDeal.charAt(len - 5)) {
                                //双写结尾加ing
                                tmp = wordDeal.substring(0, len - 4);
                                res = checkDeformedWord(set,tmp);
                            }
                        }
                    }

                }
            }
            //2.3动词过去分词 过去式
            // -ed, -d, 双写结尾加ed, y变i加ed,c变ck加ed
            if (StringUtils.isBlank(res) && wordDeal.charAt(len - 2) == 'e' && wordDeal.charAt(len - 1) == 'd') {
                //-ed,这里可能会把结尾是e但去掉e也是单词的词误判了，不过小比例，不考虑了
                tmp = wordDeal.substring(0, len - 2);
                res = checkDeformedWord(set,tmp);
                if (StringUtils.isBlank(res)) {
                    //-d
                    tmp = wordDeal.substring(0, len - 1);
                    res = checkDeformedWord(set,tmp);
                    if (StringUtils.isBlank(res)) {
                        if (wordDeal.charAt(len - 3) == 'i') {
                            //y变i加ed
                            tmp = wordDeal.substring(0, len - 3);
                            res = checkDeformedWord(set,tmp + "y");
                        }
                        if (StringUtils.isBlank(res) && len > 4) {
                            if (wordDeal.charAt(len - 3) == 'k' && wordDeal.charAt(len - 4) == 'c') {
                                //c变ck加ed
                                tmp = wordDeal.substring(0, len - 4);
                                res = checkDeformedWord(set,tmp);
                            }
                            if (StringUtils.isBlank(res) && wordDeal.charAt(len - 3) == wordDeal.charAt(len - 4)) {
                                //双写结尾加ed
                                tmp = wordDeal.substring(0, len - 3);
                                res = checkDeformedWord(set,tmp);
                            }
                        }
                    }
                }
            }
            //3.1 形容词比较级变形
            //-er,-r,-ier,双写结尾加er
            if (StringUtils.isBlank(res) && wordDeal.charAt(len - 2) == 'e' && wordDeal.charAt(len - 1) == 'r'){
                //-er
                tmp = wordDeal.substring(0, len - 2);
                res = checkDeformedWord(set,tmp);
                if(StringUtils.isBlank(res)){
                    //-r
                    tmp = wordDeal.substring(0, len - 1);
                    res = checkDeformedWord(set,tmp);
                    if(StringUtils.isBlank(res)){
                        //ier
                        if (wordDeal.charAt(len - 3) == 'i') {
                            tmp = wordDeal.substring(0, len - 3);
                            res = checkDeformedWord(set,tmp + "y");
                        }
                        if (StringUtils.isBlank(res)  && len > 4 && wordDeal.charAt(len - 3) == wordDeal.charAt(len - 4)) {
                            //双写结尾加er
                            tmp = wordDeal.substring(0, len - 3);
                            res = checkDeformedWord(set,tmp);
                        }
                    }
                }
            }
            //3.2 形容词最高级变形
            //-est,-st,-iest,双写结尾加est
            if (StringUtils.isBlank(res)  && len > 4 && wordDeal.charAt(len - 3) == 'e' && wordDeal.charAt(len - 2) == 's' && wordDeal.charAt(len - 1) == 't'){
                //-est
                tmp = wordDeal.substring(0, len - 3);
                res = checkDeformedWord(set,tmp);
                if(StringUtils.isBlank(res)){
                    //-st
                    tmp = wordDeal.substring(0, len - 2);
                    res = checkDeformedWord(set,tmp);
                    if(StringUtils.isBlank(res)){
                        //-iest
                        if (wordDeal.charAt(len - 4) == 'i') {
                            tmp = wordDeal.substring(0, len - 4);
                            res = checkDeformedWord(set,tmp + "y");
                        }
                        if (StringUtils.isBlank(res) && len > 5 && wordDeal.charAt(len - 4) == wordDeal.charAt(len - 5)) {
                            //双写结尾加est
                            tmp = wordDeal.substring(0, len - 4);
                            res = checkDeformedWord(set,tmp);
                        }
                    }
                }
            }
            //3.3 形容词变副词
            //-ly, -y,le结尾去e加y,去e加ly, y变i加ly, ic加ally
            if (StringUtils.isBlank(res) && wordDeal.charAt(len - 2) == 'l' && wordDeal.charAt(len - 1) == 'y'){
                //-ly
                tmp = wordDeal.substring(0, len - 2);
                res = checkDeformedWord(set,tmp);
                if(StringUtils.isBlank(res)){
                    //-y
                    tmp = wordDeal.substring(0, len - 1);
                    res = checkDeformedWord(set,tmp);
                    if(StringUtils.isBlank(res)){
                        //le结尾去e加y
                        tmp = wordDeal.substring(0, len - 1);
                        res = checkDeformedWord(set,tmp+"e");
                    }
                    if(StringUtils.isBlank(res)){
                        //去e加ly
                        tmp = wordDeal.substring(0, len - 2);
                        res = checkDeformedWord(set,tmp + "e");
                        if(StringUtils.isBlank(res)) {
                            //y变i加ly,
                            if (wordDeal.charAt(len - 3) == 'i') {
                                tmp = wordDeal.substring(0, len - 3);
                                res = checkDeformedWord(set,tmp + "y");
                            }
                        }
                    }
                }
                if(StringUtils.isBlank(res) && len > 5 &&wordDeal.charAt(len - 4) == 'a' && wordDeal.charAt(len - 3) == '1' ){
                    //-ally
                    tmp = wordDeal.substring(0, len - 4);
                    res = checkDeformedWord(set,tmp);
                }
            }
        }
        return res;
    }
    //检查变形后的词
    private static String checkDeformedWord(Set<String> set,String word){
        //变形产生的词最短要两个字母
        if(word.length() <2){
            return null;
        }
        //或者在已知的两个字母的单词里否则不考虑
        if(word.length()==2){
            if(!twoLettersWordsCouldDeform.contains(word)) {
                return null;
            }
        }
        if(set.contains(word)){
            return word;
        }else{
            return null;
        }
    }
    //该函数统计的是字典间无重叠的情况
    public static DictAnalyseResult analyseArtResultByNoOverlapDict(final ToeflArtAnalysisResult taar){
        DictAnalyseResult result = new DictAnalyseResult();
        if(taar==null){
            return null;
        }
        Map<DictEnum,Integer> dictToUniAmountMap = result.getDictToUniAmountMap();
        Map<DictEnum,Long> dictToAllTimesMap = result.getDictToAllTimesMap();
        List<WordNum> surplusList = result.getSurplusList();
        Map<DictEnum, List<WordNum>> dictContainMap = result.getDictContainMap();
        surplusList.addAll(taar.getWordList());
        //按枚举类的顺序查询字典
        for (DictEnum dictEnum : DictEnum.values()){
            Set<String> dict = dictionaryMap.get(dictEnum);
            if( null != dict){
                Integer amount = 0;
                Long times = 0L;
                if( null != dictToUniAmountMap.get(dictEnum) ){
                    amount = dictToUniAmountMap.get(dictEnum);

                }
                if(null != dictToAllTimesMap.get(dictEnum)){
                    times = dictToAllTimesMap.get(dictEnum);
                }
                Iterator<WordNum> it = surplusList.iterator();
                while(it.hasNext()){
                    WordNum wn = (WordNum)it.next();
                    if(StringUtils.isNotBlank(lookUpWordInDict(wn.getWord(),dict))){
                        amount++;
                        times= times + wn.getSum();
                        dictContainMap.get(dictEnum).add(wn);
                        it.remove();
                    }
                }
                dictToUniAmountMap.put(dictEnum,amount);
                dictToAllTimesMap.put(dictEnum,times);
            }





        }
//        for (Map.Entry<DictEnum,Set<String>> entry : dictionaryMap.entrySet()) {
//            Integer amount = 0;
//            Long times = 0L;
//            if(dictToUniAmountMap.get(entry.getKey())!=null){
//                amount = dictToUniAmountMap.get(entry.getKey());
//
//            }
//            if(dictToAllTimesMap.get(entry.getKey())!=null){
//                times = dictToAllTimesMap.get(entry.getKey());
//            }
//            Set<String> dict = entry.getValue();
//            if(dict!=null){
//                Iterator<WordNum> it = surplusList.iterator();
//                while(it.hasNext()){
//                    WordNum wn = (WordNum)it.next();
//                    if(lookUpWordInDict(wn.getWord(),dict)){
//                        amount++;
//                        times= times + wn.getSum();
//                        it.remove();
//                    }
//                }
//            }
//            dictToUniAmountMap.put(entry.getKey(),amount);
//            dictToAllTimesMap.put(entry.getKey(),times);
//        }
        return result;
    }

    public static Map<DictEnum, Set<String>> getDictionaryMap() {
        return dictionaryMap;
    }

    public static Map<DictEnum, Integer> getDictionaryWordNumMap() {
        return dictionaryWordNumMap;
    }

    public static Set<String> getAllDictionary() {
        return allDictionary;
    }
}
