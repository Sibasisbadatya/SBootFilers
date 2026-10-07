package com.example.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

//@Order(1)
//this annotation required if multiple filters were there
//for multiple filter if we dont give order annotation then spring IOC may run any filter first but that will be undeterministic
//@Component
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
// Filter Chain --> here from request it goes to tomcat -> filter -> dispatcher servlet
//        but in filter phase there may be different filters so it's called filterChain.
//        request -> filter 1->2->3 from response -> filter 3->2->1


        System.out.println("Request entered in logging filter");
// here servletRequest is of ServletRequest which may be different kind of request if we want to know some data for
//        http request we can typecast.
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String requestId = UUID.randomUUID().toString();
        resp.setHeader("X-Request-ID", requestId);

//        Question is why not during response returning through filterChain
//        so during returning through filterChain we can not set header because
//        response is already committed and in read-only mode and sent to client so we can not set header after that.
//        during reponse (ResponseEntity) sent from controller it does to dispatcher servlet and then filter again then to clint.but
//        when ResponseEntity come to dispatcher servlet ,dispatcher servlet write the content of ResponseEntity in servletResponse and makes it READ-ONLY.


//        we set earlier for response because the ResponseServlet and for Request also which is made by TomactServlet Container and sent to forward step
//        where we just set content of our own .in that ResponseServlet only we modify which will go to filters and then got return.
        long startTime = System.currentTimeMillis();
        System.out.println("HTTP METHOD" + req.getMethod());
        System.out.println("Request URI" + req.getRequestURI());
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {

//        it calls next filters doFilter if not it automatically calls dispatche Servlet

            System.out.println("Response leaving logging filter");
            System.out.println("HTTP RESPONSE" + resp.getStatus());
            System.out.println("Time taken to process the request: " + (System.currentTimeMillis() - startTime) + "ms");
        }
//        best practice to use try and finally so that response(returning side) side logs always gets printedd

    }
}


//FILTER LIFECYCLE
//ini()
//
//doFilter()
//
//destroy()
