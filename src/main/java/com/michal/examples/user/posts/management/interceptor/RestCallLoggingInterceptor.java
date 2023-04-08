package com.michal.examples.user.posts.management.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.*;

/**
 * @author Michal Remis
 */
@Component
public class RestCallLoggingInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(RestCallLoggingInterceptor.class);

    private final ObjectMapper objectMapper;

    public RestCallLoggingInterceptor(
            ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws JsonProcessingException {

        logger.info("New request Method: [{}], URL : [{}], Parameters [{}], Headers: [{}]",
                request.getMethod(),
                request.getRequestURL(),
                convertParametersMapToString(request.getParameterMap()),
                objectMapper.writeValueAsString(getHeadersMap(request))
        );
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) throws JsonProcessingException {

        logger.info("New response Code [{}], Method: [{}], URL : [{}], Headers: [{}]",
                response.getStatus(),
                request.getMethod(),
                request.getRequestURL(),
                objectMapper.writeValueAsString(getHeadersMap(response))
        );
    }

    private Map<String, Collection<String>> getHeadersMap(Object object) {

        final Map<String, Collection<String>> headers = new HashMap<>();

        if (object instanceof final HttpServletRequest request) {

            Collections.list(request.getHeaderNames()).forEach(e -> {
                if (!headers.containsKey(e)) {
                    headers.put(e, Collections.list(request.getHeaders(e)));
                } else {
                    headers.get(e).addAll(Collections.list(request.getHeaders(e)));
                }
            });

        } else if (object instanceof final HttpServletResponse response) {

            response.getHeaderNames().forEach(e -> {
                if (!headers.containsKey(e)) {
                    headers.put(e, response.getHeaders(e));
                } else {
                    headers.get(e).addAll(response.getHeaders(e));
                }
            });

        } else {
            logger.error("Wrong object type: [{}]", object.getClass().getSimpleName());
        }

        return headers;
    }

    private String convertParametersMapToString(Map<String, String[]> map) {
        String convertedParameters = "";
        if (map != null && !map.isEmpty()) {
            StringBuilder mapAsString = new StringBuilder("{");
            for (String key : map.keySet()) {
                mapAsString.append(key).append("=").append(Arrays.toString(map.get(key))).append(", ");
            }
            mapAsString.delete(mapAsString.length() - 2, mapAsString.length()).append("}");
            convertedParameters = mapAsString.toString();
        }
        return convertedParameters;
    }
}
