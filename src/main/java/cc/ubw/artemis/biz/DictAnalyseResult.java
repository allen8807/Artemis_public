package cc.ubw.artemis.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2020/9/20 0020
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class DictAnalyseResult {
    //每个字典含有的单词数
    private Map<DictEnum,Integer> dictToUniAmountMap;
    //每个字典命中的总单词次数，计算时，每个单词要乘上出现次数
    private Map<DictEnum,Long> dictToAllTimesMap;
    //字典里的词
    private Map<DictEnum, List<WordNum>> dictContainMap;

    //不在字典里的词
    private List<WordNum> surplusList;

    public DictAnalyseResult(){
         dictToUniAmountMap = new HashMap<>();
         dictToAllTimesMap = new HashMap<>();
         surplusList = new ArrayList<>();
         dictContainMap = new HashMap<>();
        for (DictEnum dictEnum : DictEnum.values()) {
            dictContainMap.put(dictEnum, new ArrayList<>());
        }
    }

    public Map<DictEnum, Integer> getDictToUniAmountMap() {
        return dictToUniAmountMap;
    }

    public void setDictToUniAmountMap(Map<DictEnum, Integer> dictToUniAmountMap) {
        this.dictToUniAmountMap = dictToUniAmountMap;
    }

    public List<WordNum> getSurplusList() {
        return surplusList;
    }

    public void setSurplusList(List<WordNum> surplusList) {
        this.surplusList = surplusList;
    }

    public Map<DictEnum, Long> getDictToAllTimesMap() {
        return dictToAllTimesMap;
    }

    public void setDictToAllTimesMap(Map<DictEnum, Long> dictToAllTimesMap) {
        this.dictToAllTimesMap = dictToAllTimesMap;
    }

    public Map<DictEnum, List<WordNum>> getDictContainMap() {
        return dictContainMap;
    }

    public void setDictContainMap(Map<DictEnum, List<WordNum>> dictContainMap) {
        this.dictContainMap = dictContainMap;
    }
}
