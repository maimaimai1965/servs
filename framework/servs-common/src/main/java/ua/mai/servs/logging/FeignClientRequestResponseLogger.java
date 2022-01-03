package ua.mai.servs.logging;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import feign.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import ua.telesens.o320.trt.integration.bss.config.BusinessLog;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.ConnectException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static feign.Util.UTF_8;
import static feign.Util.decodeOrDefault;
import static net.logstash.logback.marker.Markers.*;

@Component
@Slf4j
public class FeignClientRequestResponseLogger extends Logger {

    public static final String DEFAULT_REQUEST_MESSAGE_PREFIX = "  REQ_IN : ";
    private static final int DEFAULT_MAX_REQUEST_PAYLOAD_LENGTH = 100;

    public static final String DEFAULT_RESPONSE_MESSAGE_PREFIX = "  RESP_OUT : ";
    private static final int DEFAULT_MAX_RESPONSE_PAYLOAD_LENGTH = 100;

    public static final boolean DEFAULT_AUTH_LOG_ACTIVE = true;
    public static final boolean DEFAULT_AUTH_LOG_PAYLOAD = false;
    public static final String DEFAULT_AUTH_LOG_AUTH_URI_PART = "/auth/";

    private String requestMessagePrefix = DEFAULT_REQUEST_MESSAGE_PREFIX;
    private boolean includeRequestPayload = true;
    private int maxRequestPayloadLength = DEFAULT_MAX_REQUEST_PAYLOAD_LENGTH;

    private String responseMessagePrefix = DEFAULT_RESPONSE_MESSAGE_PREFIX;
    private boolean includeResponsePayload = true;
    private int maxResponsePayloadLength = DEFAULT_MAX_RESPONSE_PAYLOAD_LENGTH;

    private boolean authLogActive = DEFAULT_AUTH_LOG_ACTIVE;
    private boolean authLogPayload = DEFAULT_AUTH_LOG_PAYLOAD;
    private String authLogAuthUriPart = DEFAULT_AUTH_LOG_AUTH_URI_PART;

    @PostConstruct
    private void postConstruct() throws IllegalAccessException {
//        Field[] fields = MiddlewareProps.EventType.class.getDeclaredFields();
//        eventTypeNames = new ArrayList<>();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            eventTypeNames.add(field.get(middlewareProps.getEventType()).toString());
//        }
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
//        super.logRequest(configKey, logLevel, request);
        if (log.isDebugEnabled()) {
            Optional.ofNullable(request.body()).ifPresentOrElse(bytes -> {
                JsonElement jsonElement = JsonParser.parseString(decodeOrDefault(request.body(), UTF_8, "Binary data"));
                log.debug("  REQ_OUT -> {}: {} {} body=[{}] ", request.requestTemplate().feignTarget().name(),
                      request.httpMethod().name(), request.url(), new Gson().fromJson(jsonElement.toString(), HashMap.class));
//                log.info("REQ_OUT", append("url", request.url()), append("body", new Gson().fromJson(jsonElement.toString(), HashMap.class)));
            }, () -> {
                log.debug("REQ_OUT -> {}: {} {}", request.requestTemplate().feignTarget().name(), request.httpMethod().name(),
                      request.url());
//                log.info("REQ_OUT", append("url", request.url()));
            });
        }
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        if (log.isDebugEnabled()) {
            int bodyLength = 0;
            int status = response.status();
            if (response.body() != null && !(status == 204 || status == 205)) {
                // HTTP 204 No Content "...response MUST NOT include a message-body"
                // HTTP 205 Reset Content "...response MUST NOT include an entity"
                byte[] bodyData = Util.toByteArray(response.body().asInputStream());
                bodyLength = bodyData.length;
                if (bodyLength > 0) {
                    log.debug("  RESP_IN <- {}: {} {} {} body=[{}]", response.request().requestTemplate().feignTarget().name(),
                          status, response.request().httpMethod().name(), response.request().url(),
                          decodeOrDefault(bodyData, UTF_8, "Binary data"));
                }
                return response.toBuilder().body(bodyData).build();
            } else {
                log.debug("  RESP_IN <- {}: {} {} {}", response.request().requestTemplate().feignTarget().name(),
                      status, response.request().httpMethod().name(), response.request().url());
                log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
            }
        }
        return response;
//        MiddlewareProps.OperationMethodUri operationProps = operationProps(response.request().requestTemplate());
//        BusinessLog.Operation operation = operationProps == null ? BusinessLog.Operation.NOTDEFINED :
//                BusinessLog.Operation.valueOf(operationProps.getOperation());
//
//        int status = response.status();
//
//        BusinessLog basicInfo =
//                BusinessLog.builder()
//                        .type(BusinessLog.Type.REQ)
//                        .operation(operation.getOperation())
//                        .duration(elapsedTime)
//                        .status(status)
//                        .build();
//
//        AtomicReference<String> businessLogMsg = new AtomicReference<>(operation.getMessage());
//        byte[] reqBodyData;
//        JsonObject jsonObject = new JsonObject();
//        String url = response.request().url();
//        String operationMethodUri = middlewareProps.getEndpoints().getOperationMethodUri().stream()
//                .filter(uri -> url.contains(uri.getUriTemplate())).findFirst().map(MiddlewareProps.OperationMethodUri::getUriTemplate).orElse(null);
//        Optional.ofNullable(operationMethodUri).ifPresent(uri -> {
//            String[] splitedByUri = url.split(uri);
//            if (splitedByUri.length > 1) {
//                String ident = splitedByUri[splitedByUri.length - 1].split("\\?")[0];
//                if (!ident.isBlank()) {
//                    jsonObject.addProperty("identifier", ident);
//                }
//            }
//        });
//        if ((reqBodyData = response.request().body()) != null) {
//            if (operation.getOperation().equals("ACT")) {
//                JsonElement jsonElement = JsonParser.parseString(decodeOrDefault(reqBodyData, UTF_8, "Binary data"));
//                if (jsonElement.isJsonObject() && !jsonElement.isJsonNull()) {
//                    JsonObject reqBody = jsonElement.getAsJsonObject();
//                    Optional.ofNullable(reqBody.get("state"))
//                            .ifPresent(state -> {
//                                if (!state.isJsonNull() && !state.isJsonArray() && !state.isJsonObject() && state.getAsString().equalsIgnoreCase("inactive")) {
//                                    basicInfo.setOperation("DEACT");
//                                    businessLogMsg.set(BusinessLog.Operation.DEACT.getMessage());
//                                }
//                            });
//                    getAdditionalData(jsonElement, jsonObject, operation.getOperation());
//                }
//            } else {
//                getAdditionalData(reqBodyData, jsonObject, operation.getOperation());
//            }
//        }
//        if (response.body() != null && !(status == 204 || status == 205)) {
//            byte[] responseBodyData = Util.toByteArray(response.body().asInputStream());
//            getAdditionalData(responseBodyData, jsonObject, operation.getOperation());
//            log.info(businessLogMsg.get(), appendFields(basicInfo), appendEntries(new Gson().fromJson(jsonObject.toString(), HashMap.class)));
//            return response.toBuilder().body(responseBodyData).build();
//        } else {
//            log.info(businessLogMsg.get(), appendFields(basicInfo), appendEntries(new Gson().fromJson(jsonObject.toString(), HashMap.class)));
//            return response;
//        }
//    }
//
//    private void getAdditionalData(byte[] bodyData, JsonObject jsonObjectAccumulator, String operation) {
//        JsonElement jsonElement = JsonParser.parseString(decodeOrDefault(bodyData, UTF_8, "Binary data"));
//        if (jsonElement.isJsonObject() && !jsonElement.isJsonNull()) {
//            processJsonObject(jsonElement.getAsJsonObject(), jsonObjectAccumulator, operation);
//        }
//    }
//
//    private void getAdditionalData(JsonElement bodyData, JsonObject jsonObjectAccumulator, String operation) {
//        processJsonObject(bodyData.getAsJsonObject(), jsonObjectAccumulator, operation);
//    }
//
//    private void processJsonElement(JsonElement e, String key, JsonObject jsonObjectAccumulator, String operation) {
//        if (e.isJsonArray()) {
//            processJsonObject(e.getAsJsonArray().get(0).getAsJsonObject(), jsonObjectAccumulator, operation);
//        }
//        if (e.isJsonObject()) {
//            processJsonObject(e.getAsJsonObject(), jsonObjectAccumulator, operation);
//        }
//        if (e.isJsonPrimitive()) {
//            boolean isKeyRequired = Arrays.asList(integrationProps.getAdditionalLogParams()).contains(key);
//            boolean isAccumulatorContains = jsonObjectAccumulator.keySet().contains(key);
//            if (isKeyRequired && !isAccumulatorContains) {
//                if (key.equals("id") && e.getAsString().matches("\\d{12}")) {
//                    jsonObjectAccumulator.add("msisdn", e);
//                } else if ((operation.equals(BusinessLog.Operation.BALRES.getOperation())
//                        || operation.equals(BusinessLog.Operation.BALRESCONF.getOperation())) && key.equals("id")) {
//                    jsonObjectAccumulator.add("balres_id", e);
//                } else if (operation.equals(BusinessLog.Operation.BALRES.getOperation()) && key.equals("requestId")) {
//                    jsonObjectAccumulator.add("balres_href", e);
//                } else if (operation.equals(BusinessLog.Operation.BALRESCONF.getOperation()) && key.equals("href")) {
//                    jsonObjectAccumulator.add("balres_href", e);
//                } else if (operation.equals(BusinessLog.Operation.GIFTACT.getOperation()) && key.equals("reason")) {
//                    jsonObjectAccumulator.add("melody", e);
//                } else if (operation.equals(BusinessLog.Operation.GIFTACT.getOperation()) && key.equals("type")) {
//                    jsonObjectAccumulator.add("bonus_code", e);
//                } else {
//                    if (!eventTypeNames.contains(e.getAsString())) {
//                        jsonObjectAccumulator.add(key.replaceAll("([A-Z][a-z])", "_$1").toLowerCase(), e);
//                    }
//                }
//            }
//        }
//
    }

//    private void processJsonObject(JsonObject jsonObject, JsonObject jsonObjectAccumulator, String operation) {
//        Set<Map.Entry<String, JsonElement>> members = jsonObject.entrySet();
//        for (Map.Entry<String, JsonElement> e : members) {
//            processJsonElement(e.getValue(), e.getKey(), jsonObjectAccumulator, operation);
//        }
//    }
//
//    private MiddlewareProps.OperationMethodUri operationProps(RequestTemplate requestTemplate) {
//        String uriTemplate = requestTemplate.path().replace(middlewareProps.getUrl(), "");
//        return middlewareProps.getEndpoints().getOperationMethodUri().stream()
//                .filter((operationMethodUri -> operationMethodUri.getMethodName().equals(requestTemplate.method())
//                        && uriTemplate.startsWith(operationMethodUri.getUriTemplate()))).findFirst().orElse(null);
//    }

    @Override
    protected IOException logIOException(String configKey, Level logLevel, IOException ioe, long elapsedTime) {
        log.info("  ERROR {}: {} (}ms)", ioe.getClass().getSimpleName(), ioe.getMessage(), elapsedTime);
        if (!(ioe instanceof ConnectException)) {
            if (log.isDebugEnabled()) {
                StringWriter sw = new StringWriter();
                ioe.printStackTrace(new PrintWriter(sw));
                log.debug("%s", sw.toString());
            }
        }
        return ioe;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
    }

    public void setRequestMessagePrefix(String requestMessagePrefix) {
        this.requestMessagePrefix = requestMessagePrefix;
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

    public void setIncludeResponsePayload(boolean includeResponsePayload) {
        this.includeResponsePayload = includeResponsePayload;
    }

    public void setMaxResponsePayloadLength(int maxResponsePayloadLength) {
        this.maxResponsePayloadLength = maxResponsePayloadLength;
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
}

