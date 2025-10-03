package com.food.foodorderapi.library;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.food.foodorderapi.library.constant.Constant;




@Component
@RequiredArgsConstructor
@Slf4j
public class RequestContextInitializerFilter implements Filter {

  private final Tracer tracer;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      Span current = tracer.currentSpan();
      TraceContext ctx = current.context();
      MDC.put(Constant.TRACE_ID, ctx.traceId());
      MDC.put(Constant.SPAN_ID, ctx.spanId());
      chain.doFilter(request, response);
    } finally {
      MDC.clear();
    }
  }
}
