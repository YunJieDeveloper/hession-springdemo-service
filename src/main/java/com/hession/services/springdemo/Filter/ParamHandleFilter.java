package com.hession.services.springdemo.Filter;

import com.google.common.base.Charsets;
import com.hession.services.springdemo.Filter.wrapper.ParamServletRequestWrapper;
import com.hession.services.springdemo.entity.RequestEntity;
import com.hession.services.springdemo.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @program: hession-springdemo-service
 * @description: 通过过滤器操作实现对请求参数的逻辑处理
 * @author: Hession
 * @create: 2019-03-24 12:59
 **/

@Slf4j
public class ParamHandleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (Objects.equals(HttpMethod.GET.name(), request.getMethod())) {
            ParamServletRequestWrapper paramWrapper = new ParamServletRequestWrapper(request, null);
            //根据参数名称获取get请求参数value
            String mobile = paramWrapper.getParameter("mobile");
            /**
             * 处理get请求参数
             * */
            paramWrapper.setParameter("mobile", String.format("+86%s", mobile));
            //将paramWrapper通过过滤器链往下传
            filterChain.doFilter(paramWrapper, servletResponse);

        } else if (Objects.equals(HttpMethod.POST.name(), request.getMethod())) {
            String requestURI = request.getRequestURI();
            if (requestURI.contains("/bill/getInfo")) {
                String plainBody = null;
                try {
                    //1、获取post请求参数body体
                    plainBody = this.stream2Str(request.getInputStream());
                    //2、对入参进行包装处理
                    RequestEntity entity = null;
                    entity = JsonUtils.readValue(plainBody, RequestEntity.class);
                    if (Objects.isNull(entity)) {
                        //todo 如果entity为null 说明入参参数类型不是RequestEntity，就无须包装处理，可直接执行下一个Filter
                        filterChain.doFilter(request, servletResponse);
                        return;
                    }
                    entity.setMobile(String.format("+86%s%s", entity.getMobile(), "bodyFilterWrapper"));
                    //3、重新转换为String类型经过wrapper包装放回filterChain
                    plainBody = JsonUtils.writeValue(entity);
                } catch (IOException e) {
                    log.error("get body occur error");
                }
                ParamServletRequestWrapper paramWrapper = new ParamServletRequestWrapper(request, plainBody);
                //将paramWrapper通过过滤器链往下传
                filterChain.doFilter(paramWrapper, servletResponse);
            }
            /** 一定要调用此方法放过去 */
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            filterChain.doFilter(request, servletResponse);
        }

    }


    private String stream2Str(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}
