package com.ths.first.gzip;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;


public class GZipServletFilter implements Filter {

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest  httpRequest  = (HttpServletRequest)  request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setLocale(LocaleContextHolder.getLocale());
        if ( acceptsGZipEncoding(httpRequest) ) {
            httpResponse.addHeader("Content-Encoding", "gzip");
            GZipServletResponseWrapper gzipResponse = new GZipServletResponseWrapper(httpResponse);
            gzipResponse.setLocale(LocaleContextHolder.getLocale());
            chain.doFilter(request, gzipResponse);
            gzipResponse.close();
            
        } else {
        	
        	
            chain.doFilter(request, response);
            
        }
    }

    private boolean acceptsGZipEncoding(HttpServletRequest httpRequest) {
        String acceptEncoding = httpRequest.getHeader("Accept-Encoding");
        return acceptEncoding != null && acceptEncoding.indexOf("gzip") != -1;
    }

}
