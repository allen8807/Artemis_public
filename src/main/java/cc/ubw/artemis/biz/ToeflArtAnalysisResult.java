package cc.ubw.artemis.biz;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2020/9/11 0011
 * Time: 19:10
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class ToeflArtAnalysisResult {
    //总词数
    private Long totalWordNum = 0L;
    //去重后的词数
    private Integer uniqueWordNum = 0;
    //去重词占总次数的比率
    private Double rate = 0d;
    //词汇计数表
    private Map<String, Long> wordMap = new HashMap<>();
    //词汇排序表
    private List<WordNum> wordList = new ArrayList<>();
    //字典分析结果
    private DictAnalyseResult dar = new DictAnalyseResult();
    //严格的分析结果，文章先自我去重。默认值为null，防止无限自动构造
    private ToeflArtAnalysisResult strictlyTaar = null;

    public ToeflArtAnalysisResult() {
    }

    public void calcRate() {
        if (totalWordNum != 0) {
            this.rate = Double.valueOf(uniqueWordNum) / Double.valueOf(totalWordNum);
        } else {
            this.rate = 0d;
        }
    }


    public Long getTotalWordNum() {
        return totalWordNum;
    }

    public void setTotalWordNum(Long totalWordNum) {
        this.totalWordNum = totalWordNum;
    }

    public Integer getUniqueWordNum() {
        return uniqueWordNum;
    }

    public void setUniqueWordNum(Integer uniqueWordNum) {
        this.uniqueWordNum = uniqueWordNum;
    }

    public Map<String, Long> getWordMap() {
        return wordMap;
    }

    public void setWordMap(Map<String, Long> wordMap) {
        this.wordMap = wordMap;
    }

    public List<WordNum> getWordList() {
        return wordList;
    }

    public void setWordList(List<WordNum> wordList) {
        this.wordList = wordList;
    }

    public String toFormatString() {
        return toFormatString(true,true);
    }

    public String toFormatString(boolean surplusListFlag,boolean wordListFlag) {
        StringBuilder sb = new StringBuilder();
        if(null!=strictlyTaar){
            sb.append("\r\n=============================================================================================\r\n");
            sb.append("以下为严格处理后的数据:");
            sb.append(strictlyTaar.toFormatString(false,false));
            sb.append("\r\n=============================================================================================\r\n");
        }
        sb.append("totalWordNum=\r\n\t\t" + totalWordNum
                + "\r\nuniqueWordNum=\r\n\t\t" + uniqueWordNum
                + "\r\nrate=\r\n\t\t" + rate
                + "\r\n\n");

        if(dar!=null){
            Integer sum = 0;
            Integer dictSum = 0;
            sb.append("\r\n字典独立词汇命中情况=\n");
            sb.append(String.format("%-14s", "字典ID"))
                    .append(String.format("%-8s", "字典含单词数"))
                    .append(String.format("%-8s", "字典命中数量"))
                    .append(String.format("%-6s", "占文章独立词比率"))
                    .append(String.format("%-8s", "占字典词比率"));
            sb.append("\r\n字典名称 \n");
            for (DictEnum dictEnum : DictEnum.values()){
               if(dar.getDictToUniAmountMap().get(dictEnum)!=null){
                   Integer dNum = Dictionary.getDictionaryWordNumMap().get(dictEnum);
                   Integer amount = dar.getDictToUniAmountMap().get(dictEnum);
                   dictSum+=dNum;
                   sb.append(String.format("%-16s", dictEnum.getId()))
                           .append(String.format("%-14s",  dNum))
                           .append(String.format("%-14s", amount ))
                           .append(String.format("%-14s", String.format("%.6f", amount/Double.valueOf(uniqueWordNum))))
                           .append(String.format("%-14s", String.format("%.6f", amount/Double.valueOf(dNum))))
                           .append("\r\n");
                   sum+=amount;
               }
            }
//            for (Map.Entry<DictEnum, Integer> entry : dar.getDictToUniAmountMap().entrySet()) {
//                Integer dNum = Dictionary.getDictionaryWordNumMap().get(entry.getKey());
//                dictSum+=dNum;
//                sb.append(String.format("%-16s", entry.getKey().getId()))
//                        .append(String.format("%-14s",  dNum))
//                        .append(String.format("%-14s", entry.getValue() ))
//                        .append(String.format("%-14s", String.format("%.6f", entry.getValue()/Double.valueOf(uniqueWordNum))))
//                        .append(String.format("%-14s", String.format("%.6f", entry.getValue()/Double.valueOf(dNum))))
//                        .append("\r\n");
//                sum+=entry.getValue();
//
//            }
            sb.append("\r\n");
            sb.append("字典总词数为=\t\t"+dictSum+"\r\n");
            int allDictNum = Dictionary.getAllDictionary().size();
            sb.append("字典去重后的总词数为=\t\t"+allDictNum+"\r\n");
            sb.append("字典总命中数为=\t\t"+sum+"\r\n");
            sb.append("字典独立词汇总命中率=\t\t"+sum/Double.valueOf(uniqueWordNum)+"\r\n");
            sb.append("独立词汇总字典命中率=\t\t"+sum/Double.valueOf(allDictNum)+"\r\n");
            Long sumL=0L;
            sb.append("\r\n字典词汇出现次数加成命中情况=\n");
            sb.append(String.format("%-14s", "字典ID"))
                    .append(String.format("%-8s", "字典含单词数"))
                    .append(String.format("%-8s", "字典命中次数"))
                    .append(String.format("%-7s", "占文章总词比率"))
                    .append("\r\n");
            for (DictEnum dictEnum : DictEnum.values()){
                if(dar.getDictToAllTimesMap().get(dictEnum)!=null){
                    Integer dNum = Dictionary.getDictionaryWordNumMap().get(dictEnum);
                    Long times = dar.getDictToAllTimesMap().get(dictEnum);
                    sb.append(String.format("%-16s", dictEnum.getId()))
                            .append(String.format("%-14s",  dNum))
                            .append(String.format("%-14s", times ))
                            .append(String.format("%-14s", String.format("%.6f", times/Double.valueOf(totalWordNum))))
                            .append("\r\n");
                    sumL+=times;
                }
            }

//            for (Map.Entry<DictEnum, Long> entry : dar.getDictToAllTimesMap().entrySet()) {
//                Integer dNum = Dictionary.getDictionaryWordNumMap().get(entry.getKey());
//                sb.append(String.format("%-16s", entry.getKey().getId()))
//                        .append(String.format("%-14s",  dNum))
//                        .append(String.format("%-14s", entry.getValue() ))
//                        .append(String.format("%-14s", String.format("%.6f", entry.getValue()/Double.valueOf(totalWordNum))))
//                        .append("\r\n");
//                sumL+=entry.getValue();
//            }
            sb.append("\r\n");
            sb.append("字典词汇出现次数总比率=\t\t"+sumL/Double.valueOf(totalWordNum)+"\r\n");
            //sb.append(JSONUtils.toPrettyJSONString(dar.getDictToAmountMap()));
            sb.append("\r\n--------------------剩余非字典词汇 surplusList=");
            sb.append(dar.getSurplusList().size() + "\r\n");
            if(surplusListFlag) {
                    for (WordNum w :
                            dar.getSurplusList()) {
                        sb.append(" ").append(w.getWord()).append(",").append(w.getSum()).append("\r\n");
                    }
            }
            if(wordListFlag) {
                sb.append("\r\n各字典词汇\r\n");
                for (DictEnum dictEnum : DictEnum.values()) {
                    if (dar.getDictContainMap().get(dictEnum) != null) {
                        List<WordNum> list = dar.getDictContainMap().get(dictEnum);
                        sb.append("\r\n--------------------" +dictEnum.getId()+" =");
                        sb.append(list.size() + "\r\n");
                        for (WordNum w :
                                list) {
                            sb.append(" ").append(w.getWord()).append(",").append(w.getSum()).append("\r\n");
                        }
                    }
                }
            }
        }

        if(wordListFlag) {
            sb.append("\r\n\n全部词汇 wordList=\r\n");
            if (wordList != null) {
                for (WordNum w :
                        wordList) {
                    sb.append(" ").append(w.getWord()).append(",").append(w.getSum()).append("\r\n");
                }
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return "ToeflArtAnalysisResult{" +
                "totalWordNum=" + totalWordNum +
                ", uniqueWordNum=" + uniqueWordNum +
                ", wordMap=" + wordMap +
                ", wordList=" + wordList +
                ", dar=" + dar +
                '}';
    }

    public Double getRate() {
        return rate;
    }

    public DictAnalyseResult getDar() {
        return dar;
    }

    public void setDar(DictAnalyseResult dar) {
        this.dar = dar;
    }

    public ToeflArtAnalysisResult getStrictlyTaar() {
        return strictlyTaar;
    }

    public void setStrictlyTaar(ToeflArtAnalysisResult strictlyTaar) {
        this.strictlyTaar = strictlyTaar;
    }
}
