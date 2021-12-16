package ua.mai.servs.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j
public class RequestResponseLogFilter extends OncePerRequestFilter {

    public static final String DEFAULT_BEFORE_REQUEST_MESSAGE_PREFIX = "request start: ";
    public static final String DEFAULT_BEFORE_REQUEST_MESSAGE_SUFFIX = "";
    public static final String DEFAULT_AFTER_REQUEST_MESSAGE_PREFIX = "request finish: ";
    public static final String DEFAULT_AFTER_REQUEST_MESSAGE_SUFFIX = "";
    private static final int DEFAULT_MAX_REQUEST_PAYLOAD_LENGTH = 100;

    public static final String DEFAULT_RESPONSE_MESSAGE_PREFIX = "response: ";
    public static final String DEFAULT_RESPONSE_MESSAGE_SUFFIX = "";
    private static final int DEFAULT_MAX_RESPONSE_PAYLOAD_LENGTH = 100;

    private String beforeRequestMessagePrefix = DEFAULT_BEFORE_REQUEST_MESSAGE_PREFIX;
    private String beforeRequestMessageSuffix = DEFAULT_BEFORE_REQUEST_MESSAGE_SUFFIX;
    private String afterRequestMessagePrefix = DEFAULT_AFTER_REQUEST_MESSAGE_PREFIX;
    private String afterRequestMessageSuffix = DEFAULT_AFTER_REQUEST_MESSAGE_SUFFIX;
    private boolean includeRequestPayload = true;
    private int maxRequestPayloadLength = DEFAULT_MAX_REQUEST_PAYLOAD_LENGTH;

    private String responseMessagePrefix = DEFAULT_RESPONSE_MESSAGE_PREFIX;
    private String responseMessageSuffix = DEFAULT_RESPONSE_MESSAGE_SUFFIX;
    private boolean includeResponsePayload = true;
    private int maxResponsePayloadLength = DEFAULT_MAX_RESPONSE_PAYLOAD_LENGTH;
    private boolean showRunTime = true;

    /**
     * Log each request and respponse with full Request URI, content payload and duration of the request in ms.
     * @param request the request
     * @param response the response
     * @param filterChain chain of filters
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                                    throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        StringBuffer reqInfo =new StringBuffer(request.getMethod())
              .append(" ")
              .append(request.getRequestURI());
//              .append(request.getRequestURL());
        if (request.getQueryString() != null) {
            reqInfo.append("?").append(request.getQueryString());
        }
//        if (request.getAuthType() != null) {
//            reqInfo.append(", authType=")
//                  .append(request.getAuthType());
//        }
//        if (request.getUserPrincipal() != null) {
//            reqInfo.append(", principalName=")
//                  .append(request.getUserPrincipal().getName());
//        }

        if (log.isDebugEnabled()) {
            log.debug(beforeRequestMessagePrefix + reqInfo.append(beforeRequestMessageSuffix));
        }

        // ========= Log request and response payload ("body") ========
        // We CANNOT simply read the request payload here, because then the InputStream would be consumed and cannot be read again by the actual processing/server.
        //    String reqBody = DoogiesUtil._stream2String(request.getInputStream());   // THIS WOULD NOT WORK!
        // So we need to apply some stronger magic here :-)
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);     // ======== This performs the actual request!
        } finally {

            long duration = System.currentTimeMillis() - startTime;

            if (log.isDebugEnabled()) {
                String message = "";

                // I can only log the request's body AFTER the request has been made and ContentCachingRequestWrapper did its work.
                if (includeRequestPayload) {
                    String requestBody = this.getContentAsString(wrappedRequest.getContentAsByteArray(), this.maxRequestPayloadLength,
                          request.getCharacterEncoding());
                    if (requestBody.length() > 0) {
                        reqInfo.append("payload=[").append(requestBody).append("]");
                    }
                    log.debug(afterRequestMessagePrefix + reqInfo.append(afterRequestMessageSuffix));
                }

                message = "";
                if (includeResponsePayload) {
                    message = "payload=[" + getContentAsString(wrappedResponse.getContentAsByteArray(), this.maxResponsePayloadLength,
                          response.getCharacterEncoding()) + "]";
                }
                log.debug(
                      responseMessagePrefix + response.getStatus() + (showRunTime ? (" (" + duration + "ms) ") : "") + message + responseMessageSuffix);
            }

            wrappedResponse.copyBodyToResponse();  // IMPORTANT: copy content of response back into original response
        }
    }

    public void setBeforeRequestMessagePrefix(String beforeRequestMessagePrefix) {
        this.beforeRequestMessagePrefix = beforeRequestMessagePrefix;
    }

    public void setBeforeRequestMessageSuffix(String beforeRequestMessageSuffix) {
        this.beforeRequestMessageSuffix = beforeRequestMessageSuffix;
    }

    public void setAfterRequestMessagePrefix(String afterRequestMessagePrefix) {
        this.afterRequestMessagePrefix = afterRequestMessagePrefix;
    }

    public void setAfterRequestMessageSuffix(String afterRequestMessageSuffix) {
        this.afterRequestMessageSuffix = afterRequestMessageSuffix;
    }

    public void setIncludeRequestPayload(boolean includeRequestPayload) {
        this.includeRequestPayload = includeRequestPayload;
    }

    public void setMaxRequestPayloadLength(int maxRequestPayloadLength) {
        this.maxRequestPayloadLength = maxRequestPayloadLength;
    }

    public void setResponseMessagePrefix(String responseMessagePrefix) {
        this.responseMessagePrefix = responseMessagePrefix;
    }

    public void setResponseMessageSuffix(String responseMessageSuffix) {
        this.responseMessageSuffix = responseMessageSuffix;
    }

    public void setIncludeResponsePayload(boolean includeResponsePayload) {
        this.includeResponsePayload = includeResponsePayload;
    }

    public void setMaxResponsePayloadLength(int maxResponsePayloadLength) {
        this.maxResponsePayloadLength = maxResponsePayloadLength;
    }

    public void setShowRunTime(boolean showRunTime) {
        this.showRunTime = showRunTime;
    }

    private String getContentAsString(byte[] buf, int maxLength, String charsetName) {
        if (buf == null || buf.length == 0) return "";
        int length = Math.min(buf.length, maxLength);
        try {
            return new String(buf, 0, length, charsetName);
        } catch (UnsupportedEncodingException ex) {
            return "Unsupported Encoding";
        }
    }

}