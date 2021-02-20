package cc.ubw.artemis.biz;

import java.util.HashMap;
import java.util.Map;

public enum ToeflTypeEnum {
    RP(0, "RP", "阅读Passage"),
    LC(1, "LC", "听力对话"),
    LL(2, "LL", "听力Lecture"),
    SI(3, "SI", "独立口语"),
    SCM(4, "SCM", "材料口语对话"),
    SLM(5, "SLM", "材料口语Lecture"),
    SC(6, "SC", "口语对话"),
    SL(7, "LL", "口语Lecture"),
    WT(8, "WT", "综合写作"),
    WD(9, "WD", "独立写作"),
    R(10, "R", "阅读"),
    L(11, "L", "听力"),
    OTHER(12, "OTHER", "其他类型");

    private static final Map<String, ToeflTypeEnum> ID_MAP = new HashMap<String, ToeflTypeEnum>();

    static {
        for (ToeflTypeEnum typeEnum : ToeflTypeEnum.values()) {
            ID_MAP.put(typeEnum.getId(), typeEnum);
        }
    }

    private int code;
    private String id;
    private String desc;

    private ToeflTypeEnum(int code, String id, String desc) {
        this.setCode(code);
        this.setId(id);
        this.setDesc(desc);
    }

    public static ToeflTypeEnum getEnumById(String id) {
        ToeflTypeEnum typeEnum = ID_MAP.get(id);
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

    @Override
    public String toString() {
        return "ToeflTypeEnum{" +
                "code=" + code +
                ", id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

}
