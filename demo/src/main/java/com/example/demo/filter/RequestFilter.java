package com.example.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletRequest.getHeader("auth");
        httpServletRequest.getRequestURI();
//        here in above for those methods we simply has get but for body below
        BufferedReader reader =
                httpServletRequest.getReader(); // or.getInputStream
//        because in body there can be audio json or video
//and the stream can only be read once and since here it is read so,when this request will go then dispatcher servlet then
//        during reading from dispatcher servlet it will give EXCEPTION. because during reading the pointer already moved to end of line
//        so it cant be read again to convert to DTO by jakson library. and sice the stream can be any size so
//        tomcat will not store the data and hence it cant be read again from start.


        StringBuilder body = new StringBuilder();

        String line = reader.readLine();

        while (line != null) {
            body.append(line);
            line = reader.readLine();
        }

        System.out.println(body);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
