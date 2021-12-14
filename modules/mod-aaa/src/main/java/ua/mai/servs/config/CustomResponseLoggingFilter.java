package ua.mai.servs.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
//import ua.telesens.o320.trt.integration.bss.props.IntegrationProps;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Slf4j
public class CustomResponseLoggingFilter implements Filter {

//    private final IntegrationProps integrationProps;

//    public CustomLoggingFilter(IntegrationProps integrationProps) {
//        this.integrationProps = integrationProps;
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        logResponse(response);

//        CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
//                new CachedBodyHttpServletRequest((HttpServletRequest) request);
//        CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
//              new CachedBodyHttpServletResponse((HttpServletResponse) response);
//        String[] splitedUrl = cachedBodyHttpServletRequest.getRequestURL().toString()
//                .split(integrationProps.getApiPrefix());
//        if (splitedUrl.length > 1 && splitedUrl[splitedUrl.length - 1].equals("/notification")) {
//            HttpServletResponse responseWrapper = (HttpServletResponse) response;
//            long startTime = System.currentTimeMillis();
//            chain.doFilter(cachedBodyHttpServletRequest, response);
//            long duration = System.currentTimeMillis() - startTime;
//
//            BusinessLog basicInfo =
//                    BusinessLog.builder()
//                            .type(BusinessLog.Type.SRV)
//                            .operation(BusinessLog.Operation.CLBK.getOperation())
//                            .duration(duration)
//                            .status(responseWrapper.getStatus())
//                            .build();
//
//            JsonObject notificationResource =
//                    new GsonBuilder().create().fromJson(cachedBodyHttpServletRequest.getReader(), JsonObject.class);
//            JsonObject event = notificationResource.getAsJsonObject("event");
//            JsonObject details = event.getAsJsonObject("details");
//
//            JsonObject jsonObject = new JsonObject();
//            Optional.ofNullable(event.get("eventType"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "event_type"));
//            Optional.ofNullable(notificationResource.get("hubId"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "hub_id"));
//            Optional.ofNullable(event.get("error"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "error"));
//            Optional.ofNullable(event.get("errorDescription"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "error_description"));
//            Optional.ofNullable(event.get("correlationId"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "correlation_id"));
//            Optional.ofNullable(details.get("msisdn"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "msisdn"));
//            Optional.ofNullable(details.get("newMsisdn"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "new_msisdn"));
//            Optional.ofNullable(details.get("previousMsisdn"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "msisdn"));
//            Optional.ofNullable(details.get("accountNumber"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "account_number"));
//            Optional.ofNullable(details.get("melodyId"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "melody_id"));
//            Optional.ofNullable(details.get("tos"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "tos"));
//            Optional.ofNullable(details.get("ocsId"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "ocs_id"));
//            Optional.ofNullable(details.get("result"))
//                    .ifPresent(s -> addJsonObjProp(jsonObject, s, "result"));
//
//            log.info(BusinessLog.Operation.CLBK.getMessage(), appendFields(basicInfo),
//                    appendEntries(new Gson().fromJson(jsonObject.toString(), HashMap.class))
//            );
//        } else {
//            chain.doFilter(cachedBodyHttpServletRequest, response);
//        }
    }

    private void logResponse(ServletResponse response) {
        log.debug(response.toString());
    }


    private void addJsonObjProp(JsonObject jsonObject, JsonElement s, String key) {
        if (!s.isJsonNull() && !s.isJsonArray() && !s.isJsonObject()) {
            jsonObject.addProperty(key, s.getAsString());
        }
    }

    class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

        private byte[] cachedBody;

        public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
            super(request);
            InputStream requestInputStream = request.getInputStream();
            this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return new CachedBodyServletInputStream(this.cachedBody);
        }

        @Override
        public BufferedReader getReader() throws IOException {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
            return new BufferedReader(new InputStreamReader(byteArrayInputStream));
        }
    }

    class CachedBodyServletInputStream extends ServletInputStream {
        private InputStream cachedBodyInputStream;

        public CachedBodyServletInputStream(byte[] cachedBody) {
            this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
        }

        @Override
        public boolean isFinished() {
            try {
                return cachedBodyInputStream.available() == 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            return cachedBodyInputStream.read();
        }
    }


}
