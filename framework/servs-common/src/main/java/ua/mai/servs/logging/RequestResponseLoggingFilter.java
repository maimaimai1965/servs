package ua.mai.servs.logging;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
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
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    public static final String DEFAULT_BEFORE_REQUEST_MESSAGE_PREFIX = "request start: ";
    public static final String DEFAULT_BEFORE_REQUEST_MESSAGE_SUFFIX = "";
    public static final String DEFAULT_AFTER_REQUEST_MESSAGE_PREFIX = "request finish: ";
    public static final String DEFAULT_AFTER_REQUEST_MESSAGE_SUFFIX = "";
    private static final int DEFAULT_MAX_REQUEST_PAYLOAD_LENGTH = 100;

    public static final String DEFAULT_RESPONSE_MESSAGE_PREFIX = "response: ";
    public static final String DEFAULT_RESPONSE_MESSAGE_SUFFIX = "";
    private static final int DEFAULT_MAX_RESPONSE_PAYLOAD_LENGTH = 100;

    public static final boolean DEFAULT_AUTH_LOG_ACTIVE = true;
    public static final boolean DEFAULT_AUTH_LOG_PAYLOAD = false;
    public static final String DEFAULT_AUTH_LOG_AUTH_URI_PART = "/auth/";

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

    private boolean authLogActive = DEFAULT_AUTH_LOG_ACTIVE;
    private boolean authLogPayload = DEFAULT_AUTH_LOG_PAYLOAD;
    private String authLogAuthUriPart = DEFAULT_AUTH_LOG_AUTH_URI_PART;

    //TODO реализовать в логировании использование переменных, определенных выше.
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

        boolean authRequest = Strings.isNotEmpty(authLogAuthUriPart) && request.getRequestURI().contains(authLogAuthUriPart);

        if (log.isDebugEnabled() && (!authRequest || authLogActive)) {
            log.debug(beforeRequestMessagePrefix + reqInfo.append(beforeRequestMessageSuffix));
        }

        // ========= Log request and response payload ("body") ========
        // We CANNOT simply read the request payload here, because then the InputStream would be consumed and cannot be read again by the actual processing/server.
        //    String reqBody = DoogiesUtil._stream2String(request.getInputStream());   // THIS WOULD NOT WORK!
        // So we need to apply some stronger magic here :-)
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        Exception exception = null;
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);     // ======== This performs the actual request!
        }
        catch (Exception ex) {
            exception = ex;
            log.debug("ERROR : {}", ex.getMessage());
            throw ex;
        }
        finally {
            long duration = System.currentTimeMillis() - startTime;

            if (log.isDebugEnabled() && (!authRequest || authLogActive)) {
                // I can only log the request's body AFTER the request has been made and ContentCachingRequestWrapper did its work.
                if ((!authRequest) && includeRequestPayload || authRequest && authLogPayload) {
                    String requestBody = this.getContentAsString(wrappedRequest.getContentAsByteArray(), this.maxRequestPayloadLength,
                          request.getCharacterEncoding());
                    if (requestBody.length() > 0) {
                        reqInfo.append("payload=[").append(requestBody).append("]");
                    }
                    log.debug(afterRequestMessagePrefix + reqInfo.append(afterRequestMessageSuffix));
                }

                if (exception == null) {
                    String message = "";
                    if ((!authRequest) && includeResponsePayload || authRequest && authLogPayload) {
                        message = "payload=[" + getContentAsString(wrappedResponse.getContentAsByteArray(), this.maxResponsePayloadLength,
                              response.getCharacterEncoding()) + "]";
                    }
                    log.debug(responseMessagePrefix + response.getStatus() + (showRunTime ? (" (" + duration + "ms) ") : "") + message + responseMessageSuffix);
                    wrappedResponse.copyBodyToResponse();  // IMPORTANT: copy content of response back into original response
                }
            }
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

    public void setAuthLogActive(boolean authLogActive) {
        this.authLogActive = authLogActive;
    }

    public void setAuthLogPayload(boolean authLogPayload) {
        this.authLogPayload = authLogPayload;
    }

    public void setAuthLogAuthUriPart(String authLogAuthUriPart) {
        this.authLogAuthUriPart = authLogAuthUriPart;
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