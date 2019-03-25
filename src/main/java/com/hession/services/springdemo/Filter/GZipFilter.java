package com.hession.services.springdemo.Filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * @author hession
 * 过滤器：
 * 根据请求头:Accept-Encoding 判断过滤器是否对数据调请求执行压缩操作
 */
@Slf4j
public class GZipFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("action=GZipFilter start");
        if (StringUtils.isNotBlank(request.getHeader(HttpHeaders.ACCEPT_ENCODING))
                && request.getHeader(HttpHeaders.ACCEPT_ENCODING).contains("none")
        ) {
            filterChain.doFilter(request, response);
            log.info("action=GZipFilter end");
        } else {
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
            filterChain.doFilter(request, responseWrapper);
            byte[] gzipdata = gzip(responseWrapper.getContentAsByteArray());
            responseWrapper.addHeader(HttpHeaders.CONTENT_ENCODING, "gzip");
            responseWrapper.resetBuffer();
            responseWrapper.setContentLength(gzipdata.length);
            responseWrapper.getResponse().getOutputStream().write(gzipdata);
        }

    }
  private byte[] gzip(byte[]data){
      ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream(10*1024);
      GZIPOutputStream outputStream=null;
      try {
          outputStream=new GZIPOutputStream(byteArrayOutputStream);
          outputStream.write(data);
      } catch (IOException e) {
          log.error("GZIPOutputStream occur error");
      }finally {
          if (outputStream!=null){
              try {
                  outputStream.close();
              } catch (IOException e) {
                  log.error("outputStream close error");
              }
          }
      }
      return byteArrayOutputStream.toByteArray();
  }
}

