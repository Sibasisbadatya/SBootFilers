package com.example.demo.config;

import com.example.demo.filter.DummyFilter;
import com.example.demo.filter.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


//    here we can set and manage filter than in indiviual filter
//    FilterRegistrationBean is mainly used when you want explicit control over a servlet filter's
//    registration — URL patterns, order, servlet mappings, enabled state, etc.
    @Bean
    public FilterRegistrationBean<DummyFilter> getDummyFilterBean() {

        FilterRegistrationBean<DummyFilter> registrationBean =
                new FilterRegistrationBean<>();

        registrationBean.setFilter(new DummyFilter());

        // registrationBean.setOrder();

        registrationBean.addUrlPatterns("/api/*","/admin/*");
//        this filter is only applied for this url pattern.

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<RequestFilter> getRequestFilterBean() {

        FilterRegistrationBean<RequestFilter> registrationBean =
                new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestFilter());

        // registrationBean.setOrder();

        registrationBean.addUrlPatterns("/api/*","/admin/*");
//        this filter is only applied for this url pattern.

        return registrationBean;
    }
}
