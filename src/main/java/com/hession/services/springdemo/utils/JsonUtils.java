package com.hession.services.springdemo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
/**
 * @author hession
 * json <-> Object互相转换工具类
 *
 * */
@Slf4j
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
    }

    public static ObjectMapper defaultMapper() {
        return mapper;
    }

    public static <T> T readValue(String s, Class<T> cls) {
        Preconditions.checkArgument(StringUtils.isNotBlank(s), "字串不能为空");
        Preconditions.checkArgument(cls != null, "Class类型不能为空");

        try {
            return mapper.readValue(s, cls);
        } catch (Throwable e) {
            log.error("无法将[{}]转换为类型为[{}]的对象: {}", s, cls.getSimpleName(), ExceptionUtils.getStackTrace(e));
            return null;
        }
    }


    /**
     * @Description: json 转Map
     * @Param: s json字符串
     * @Param: keyCls 键类型
     * @Param: keyCls 值类型
     * @return: Map<k , v>
     * @Author: hession
     * @Date: 2018/11/9
     */
    public static <K, V> Map<K, V> readValue2Map(String s, Class<K> keyCls, Class<V> valueCls) {
        Preconditions.checkArgument(StringUtils.isNotBlank(s), "字串不能为空");
        Preconditions.checkArgument(keyCls != null, "Class类型不能为空");
        Preconditions.checkArgument(valueCls != null, "Class类型不能为空");

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(Map.class, keyCls, valueCls);
            return mapper.readValue(s, javaType);
        } catch (Throwable e) {
            log.error("无法将[{}]转换为类型为key->[{}],value->[{}]的Map对象: {}", s, keyCls.getSimpleName(), valueCls.getSimpleName(), ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    /**
     * @Descrption: json to List
     * 要确保cls类有构造函数
     * @Param: s json字符串
     * @Param: cls 实体类类型
     * @return:
     * @Author: hession
     * @Date: 2018/11/9
     */
    public static <T> List<T> readValue2List(String s, Class<T> cls) {
        Preconditions.checkArgument(StringUtils.isNotBlank(s), "字串不能为空");
        Preconditions.checkArgument(cls != null, "Class类型不能为空");
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, cls);
            return mapper.readValue(s, javaType);
        } catch (Throwable e) {
            log.error("无法将[{}]转换为类型为key->[{}]的对象: {}", s, cls.getSimpleName(), ExceptionUtils.getStackTrace(e));
            return null;
        }
    }


    public static <T> T readValue(Map<String, Object> map, Class<T> cls) {
        Preconditions.checkArgument(map != null, "map不能为null");
        Preconditions.checkArgument(cls != null, "Class类型不能为空");

        try {
            return mapper.convertValue(map, cls);
        } catch (Throwable e) {
            log.error("无法将Map[{}]转换为类型为[{}]的对象: {}", map, cls.getSimpleName(), ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    public static String writeValue(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (Throwable e) {
            log.error("json转换异常", e);
            return null;
        }
    }

    public static Object valueFromJsonKey(String json, String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(json), "json内容不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "key不能为空");
        Map<String, Object> map = readValue(json, Map.class);
        return map.get(key);
    }
}
