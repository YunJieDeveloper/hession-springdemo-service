package com.hession.services.springdemo.filter.wrapper;

import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author hession
 * @Description 自定义包装器，对请求参数进行自定义处理
 * @Date 13:14 2019-03-24
 */

@Slf4j
public class ParamServletRequestWrapper extends HttpServletRequestWrapper {

    //用于存储请求body体(post)
    private String plainText = null;
    // 用于存储请求参数(get)
    private Map<String , String[]> params = new ConcurrentHashMap<>();

    public ParamServletRequestWrapper(HttpServletRequest request, String plainText) {
        super(request);
        this.plainText = plainText;
      // 把请求参数添加到自定义的map当中
        this.params.putAll(request.getParameterMap());
    }

    @Override
    public String getContentType() {
        return MediaType.APPLICATION_JSON_UTF8_VALUE;
    }

    /**
     * 重写getParameter，代表参数从当前类中的map获取
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        String[]values = params.get(name);
        if(values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    /**
     * 重写getParameterValues方法，从当前类的 map中取值
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    /**
     * 添加参数到map中
     * @param extraParams
     */
    public void setParamters (Map<String, Object> extraParams) {
        for (Map.Entry<String, Object> entry : extraParams.entrySet()) {
            setParameter(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 添加参数到map中
     * @param name
     * @param value
     */
    public void setParameter(String name, Object value) {
        if (value != null) {
            System.out.println(value);
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }


    /**
     * 重写字节流方法
     * <p>
     * 解决处理post请求参数body时流只能读取一次的问题
     */
    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(plainText.getBytes(Charsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    /**
     * 重写字符流方法
     * <p>
     * 解决处理post请求参数body时流只能读取一次的问题
     */
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
