package cc.ubw.artemis.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DictEnum {
    /**
     * 该字典排列顺序即位字典查询顺序，在目前算法中，查到后会去掉该单词，所以注意这里的顺序 2020-09-22
     *
     */
    GRAMMAR_ABBR(-2, "GRAMMAR_ABBR", "grammar_abbr.dict","语法词缩写"),
    ALPHABET(-1, "ALPHABET", "alphabet.dict","字母表"),

    TOEFL_YBT_LV_1(1, "TOEFL_YBT_LV_1", "toefl_ybt_lv1.dict","托福一本通1级词汇"),
    TOEFL_YBT_LV_2(2, "TOEFL_YBT_LV_2", "toefl_ybt_lv2.dict","托福一本通2级词汇"),
    TOEFL_YBT_LV_3(3, "TOEFL_YBT_LV_3", "toefl_ybt_lv3.dict","托福一本通3级词汇"),
    TOEFL_YBT_LV_4(4, "TOEFL_YBT_LV_4", "toefl_ybt_lv4.dict","托福一本通4级词汇"),
    TOEFL_YBT_LV_5(5, "TOEFL_YBT_LV_5", "toefl_ybt_lv5.dict","托福一本通5级词汇"),
    TOEFL_YBT_LV_6(6, "TOEFL_YBT_LV_6", "toefl_ybt_lv6.dict","托福一本通6级词汇"),
    EASIEST_LV_0(50, "EASIEST_LV_0", "easiest_lv0.dict","太简单不录入的词汇"),
    JUNIOR_SCHOOL(100, "JUNIOR_SCHOOL", "junior_school.dict","初中词汇表，简单词兜底用"),
    SENIOR_SCHOOL(101, "SENIOR_SCHOOL", "senior_school.dict","高中词汇表，兜底用"),
    IRR_VERB(200, "IRR_VERB", "irregular_verb.dict","不规则动词表"),
    IRR_ABBR(201, "IRR_ABBR", "irregular_abbr.dict","不规则缩写表"),
    OTHER(1000, "OTHER", "","其他没有在枚举类里的词典");
    private static final Map<String, DictEnum> ID_MAP = new HashMap<String, DictEnum>();
    private static final Map<String, DictEnum> FILENAME_MAP = new HashMap<String, DictEnum>();
    private static final List<DictEnum> DICT_ENUM_LIST = new ArrayList<>();


    static {
        for (DictEnum dictEnum : DictEnum.values()) {
            ID_MAP.put(dictEnum.getId(), dictEnum);
            FILENAME_MAP.put(dictEnum.getFilename(),dictEnum);
        }
    }

    private int code;
    private String id;
    private String desc;
    private String filename;
    private DictEnum(int code, String id, String filename, String desc) {
        this.setCode(code);
        this.setId(id);
        this.setFilename(filename);
        this.setDesc(desc);
    }

    public static DictEnum getEnumById(String id) {
        DictEnum typeEnum = ID_MAP.get(id);
        if (typeEnum == null) {
            typeEnum = OTHER;
        }
        return typeEnum;
    }

    public static DictEnum getEnumByFilename(String filename) {
        DictEnum typeEnum = FILENAME_MAP.get(filename);
        if (typeEnum == null) {
            typeEnum = OTHER;
        }
        return typeEnum;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
