package com.hession.services.springdemo.filter;

import com.hession.services.springdemo.handler.RequestLimitHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author hession
 * 过滤器：
 *   对请求进行限流：通过redis计数方式实现
 */
@Slf4j
public class RequestLimitFilter implements Filter {
    private RequestLimitHandler requestLimitHandler;

    public RequestLimitFilter(RequestLimitHandler requestLimitHandler) {
        this.requestLimitHandler = requestLimitHandler;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("action=doFilter, this is RequestLimitFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        try {
            requestLimitHandler.recordRequestCount(requestURI);
            log.info("action=requestLimit start");
            filterChain.doFilter(request, servletResponse);
            log.info("action=requestLimit end");
        } catch (Exception e) {
            log.error("action=requestLimit, too many request, url=[{}]", requestURI);
            sendErrorMsg(requestURI, response, e);
        }
    }

    private void sendErrorMsg(String uri, HttpServletResponse response, Exception e) throws IOException {
        response.reset();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(430);
        response.getWriter().write(e.getMessage() + " : " + uri);
    }
}

