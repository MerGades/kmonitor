package com.kevin.monitor.servlet;


import com.kevin.monitor.Manager;
import com.kevin.monitor.core.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

public class GeneralPerformanceFilter implements Filter {

    private Timer requestTimer;
    private String[] forbiddenUrlSuffixes;

    public GeneralPerformanceFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.requestTimer = new Timer();

        if (filterConfig.getInitParameter("forbiddenUrlSuffixes") != null &&
                filterConfig.getInitParameter("forbiddenUrlSuffixes").trim().length() > 0) {
            forbiddenUrlSuffixes = filterConfig.getInitParameter("forbiddenUrlSuffixes").split(",");
        }
        Manager.instance().initialization();
        Manager.instance().startupThread();
    }

    public void destroy() {
        Manager.instance().shutdownThread();
    }

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        final StatusExposingServletResponse wrappedResponse =
                new StatusExposingServletResponse((HttpServletResponse) response);
        if(letitgo((HttpServletRequest)request, response, chain)) return;

        final Timer.Context context = requestTimer.time();
        boolean error = false;
        String errorReason = "";
        int status = 200;
        try {
            chain.doFilter(request, wrappedResponse);
        } catch (IOException e) {
            error = true;
            status = 500;
            errorReason = e.getMessage();
            throw e;
        } catch (ServletException e) {
            error = true;
            status = 500;
            errorReason = e.getMessage();
            throw e;
        } catch (RuntimeException e) {
            error = true;
            status = 500;
            errorReason = e.getMessage();
            throw e;
        } finally {
            if (!error && request.isAsyncStarted()) {
                request.getAsyncContext().addListener(new AsyncResultListener(context));
            } else {
                context.stop();
                status = wrappedResponse.getStatus();
                if(status != 200){
                    error = true;
                    errorReason = "unknown exception";
                }
                if(Manager.instance().canProfile())
                    recordMetric((HttpServletRequest) request, context, status, errorReason);
            }
        }
    }

    private boolean letitgo(HttpServletRequest request,
                            ServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        if (null == forbiddenUrlSuffixes) {
            return false;
        }
        String requestUri = request.getRequestURI();
        for (String forbiddenSuffix : forbiddenUrlSuffixes) {
            if (requestUri != null && requestUri.trim().length() > 0) {
                if (requestUri.endsWith(forbiddenSuffix)) {
                    chain.doFilter(request, response);
                    return true;
                }
            }
        }
        return false;
    }

    private void recordMetric(HttpServletRequest request,
                              Timer.Context context,
                              int status, String msg) {
        Metric metric = new Metric(genKey(request), context.duration(), context.getStartTime());
        metric.setStatus(status);
        metric.setReason(msg);
        ReservoirFactory.getInstance().add(metric);
    }

    private String genKey(HttpServletRequest request){
        String key = request.getRequestURI();
        key = key == null ? "/" : key;
        return key;
    }

    /**
     * set status for status check
     * reference from metric
     */
    private static class StatusExposingServletResponse extends HttpServletResponseWrapper {
        private int httpStatus = 200;

        public StatusExposingServletResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void sendError(int sc) throws IOException {
            httpStatus = sc;
            super.sendError(sc);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            httpStatus = sc;
            super.sendError(sc, msg);
        }

        @Override
        public void setStatus(int sc) {
            httpStatus = sc;
            super.setStatus(sc);
        }

        @Override
        public void setStatus(int sc, String sm) {
            httpStatus = sc;
            super.setStatus(sc, sm);
        }

        public int getStatus() {
            return httpStatus;
        }
    }

    /**
     * when use the servlet3.0 this will be used
     */
    private class AsyncResultListener implements AsyncListener {
        private Timer.Context context;
        private boolean done = false;

        public AsyncResultListener(Timer.Context context) {
            this.context = context;
        }

        public void onComplete(AsyncEvent event) throws IOException {
            if (!done) {
                HttpServletResponse suppliedResponse = (HttpServletResponse) event.getSuppliedResponse();
                context.stop();
                // todo report metric code will be there
            }
        }

        public void onTimeout(AsyncEvent event) throws IOException {
            context.stop();
            done = true;
        }

        public void onError(AsyncEvent event) throws IOException {
            context.stop();
            done = true;
        }

        public void onStartAsync(AsyncEvent event) throws IOException {
        }
    }
}