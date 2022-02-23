package src.messages.headers;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class Headers {
    protected String path;
    protected String methodVerb;
    protected String protocol;
    protected String host;
    protected String connection;
    protected long contentLength;
    protected String[] accept;
    protected String acceptCharSet;
    protected String authorization;
    protected String ifModifiedSince;

    protected String statusCode;
    protected String server;
    protected String lastModified;
    protected String reasonPhrase;
    protected String contentType;
    protected String wwwAuthenticate;
    protected String charset;
    protected String date;

    public Headers(){}

    protected abstract String generateHeaders();
    protected String formatHeader(String topLine){
        String headers = String.format("%s\r\n", topLine);
        Map<String, String> headerMap = toStringMap();
        for(HashMap.Entry<String,String> entry : headerMap.entrySet()){
            headers = headers.concat(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()));
        }
        return headers;
    }

    public abstract Map<String,String> toStringMap();
    public byte[] getHeadersWithEnd(){
        String headers = this.generateHeaders() + "\r\n";
        return headers.getBytes(StandardCharsets.UTF_8);
    }
    public byte[] getHeadersWithoutEnd(){
        return this.generateHeaders().getBytes(StandardCharsets.UTF_8);
    }

    public String getPath() {
        return path;
    }
    public String getMethodVerb() {
        return methodVerb;
    }
    public String getProtocol() {
        return protocol;
    }
    public String getHost() {
        return host;
    }
    public long getContentLength() {
        return contentLength;
    }
    public String getContentType(){
        return this.contentType;
    }
    public String getIfModifiedSince(){
        return this.ifModifiedSince;
    }
    public String getAuthorization() {
        return authorization;
    }

    public String getStatusCode(){
        return this.statusCode;
    }
    public String getReasonPhrase(){
        return this.reasonPhrase;
    }

    public void setStatus(int status, String reasonPhrase) {
        this.statusCode = Integer.toString(status);
        this.reasonPhrase = reasonPhrase;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public void setCharset(String charset) {
        this.charset = charset;
    }
    public void setLastModified(String lastModified){
        this.lastModified = lastModified;
    }
    public void setContentLength(long contentLength){
        this.contentLength = contentLength;
    }
    public void setWwwAuthenticate(String wwwAuthenticate){
        this.wwwAuthenticate = wwwAuthenticate;
    }
    public void debugPrint(){
        System.out.println(this.generateHeaders());
    }
}
