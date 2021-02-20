package cc.ubw.artemis.commom;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2020/9/14 0014
 * Time: 8:44
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class JSONUtils {
    private static final Log LOG = LogFactory.getLog(JSONUtils.class);
    private static ObjectMapper objMapper = new ObjectMapper();
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        //有些字段不需要序列化
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //取消timestamp转换
        objMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //统一日期样式
        objMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        //忽略空Bean转JSON的错误
        objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //忽略JSON字串和JAVA对象属性不匹配错误
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转Json格式字符串
     *
     * @param obj 对象
     * @return Json格式字符串
     */

    public static <T> String toJSONString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error("[toJSONString] parse object to JSON string error", e);
            return null;
        }
    }

    /**
     * 对象转Json格式字符串(格式化的Json字符串)
     *
     * @param obj 对象
     * @return 美化的Json格式字符串
     */
    public static <T> String toPrettyJSONString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error("[toPrettyJSONString] parse object to JSON string error", e);
            return null;
        }
    }

    /**
     * 字符串转换为自定义对象
     *
     * @param str   要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义对象
     */
    public static <T> T parseObj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objMapper.readValue(str, clazz);
        } catch (Exception e) {
            LOG.error("[parseObj] parse string to object error", e);
            return null;
        }
    }


    public static <T> T parseObj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objMapper.readValue(str, typeReference));
        } catch (IOException e) {
            LOG.error("[parseObj] parse string to object error", e);
            return null;
        }
    }

    public static <T> T parseObj(String str, Class<?> collectionClazz, Class<?>... elementClazz) {
        JavaType javaType = objMapper.getTypeFactory().constructParametricType(collectionClazz, elementClazz);
        try {
            return objMapper.readValue(str, javaType);
        } catch (IOException e) {
            LOG.error("[parseObj] parse string to object error", e);
            return null;
        }
    }

}
