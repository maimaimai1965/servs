package ua.mai.servs.config;

import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.stream.Stream;

public class CustomCommonsRequestLoggingFilter extends CommonsRequestLoggingFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType != null) {
            return Stream.of(
                        //                             MediaType.APPLICATION_XML_VALUE,
                        MediaType.TEXT_XML_VALUE, "text/xml; charset=UTF-8")
                  .anyMatch(contentType::equalsIgnoreCase);
        } else {
            return false;
        }
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        //        if (logger.isDebugEnabled()) {
        //            String body;
        //            try {
        //                body = StreamUtils.copyToString(request.getInputStream(), Charset.defaultCharset());
        //            } catch (IOException e) {
        //                body = "?????";
        //            }
        //            String m = request.getMethod() + " " + request.getRequestURI() + " " + body;
        //            super.beforeRequest(request, m);
        //        }
        super.beforeRequest(request, message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        super.afterRequest(request, message);
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
