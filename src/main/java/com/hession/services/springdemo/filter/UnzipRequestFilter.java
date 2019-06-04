package com.hession.services.springdemo.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * @author hession
 * 过滤器：
 * 根据请求头:Content-Encoding 判断过滤器是否对数据调请求request执行解压缩操作
 */
@Slf4j
/**
 * todo 使用@WebFilter方式  所有URL请求都进入了该过滤器，urlPatterns一直不生效，找不到原因。。。
 * */
@WebFilter(filterName = "unzipRequestFilter", urlPatterns ={"/hession/*"})
@Component
@Order(0)
public class UnzipRequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    log.info("action=doFilter, this is UnzipRequestFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (StringUtils.isNotBlank(request.getHeader(HttpHeaders.CONTENT_ENCODING))
                && request.getHeader(HttpHeaders.CONTENT_ENCODING).contains("gzip")
        ) {
            filterChain.doFilter(new GzipRequestWrapper(request), response);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    public static class GzipRequestWrapper extends HttpServletRequestWrapper {

        private HttpServletRequest request;

        public GzipRequestWrapper(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {

            ServletInputStream stream = request.getInputStream();
            // 如果对内容进行了压缩,则解压
            final GZIPInputStream gzipInputStream = new GZIPInputStream(stream);
            return new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return gzipInputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener arg0) {

                }
            };
        }
    }
}

