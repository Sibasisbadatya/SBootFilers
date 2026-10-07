package com.example.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.UUID;

public class ResponseBodyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestId = UUID.randomUUID().toString();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(httpServletResponse);
//        here we are not sending HttpServletResponse or ServletResponse rather we are sending a wrapper of
//        ContentCachingResponseWrapper. and we can edit in wrapper .
        chain.doFilter(request,contentCachingResponseWrapper);
        byte[] originalBodyBytes = contentCachingResponseWrapper.getContentAsByteArray();
        String originalBody = new String(originalBodyBytes);

        String modifiedBody = """
                {
                "originalResponse":%s,
                "appName":"Sibasis"
                }""".formatted(originalBody);

        contentCachingResponseWrapper.resetBuffer();
//contentCachingResponseWrapper.resetBuffer() is used to clear the response body
// that has been cached so far by ContentCachingResponseWrapper. because we have to reset previous result and add new

        contentCachingResponseWrapper.getWriter().write(modifiedBody);
        contentCachingResponseWrapper.copyBodyToResponse();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
