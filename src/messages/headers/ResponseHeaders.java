package src.messages.headers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHeaders extends Headers{
    static SimpleDateFormat dateFormat;
    static final String HTTP_Version;
    static final String SERVER_NAME;

    static {
        dateFormat = new SimpleDateFormat("E, dd MM yyyy HH:mm:ss z");
        HTTP_Version = "HTTP/1.1";
        SERVER_NAME = "Adams-Kois";
    }

    public ResponseHeaders(){
        super();
        this.date = dateFormat.format(new Date());
        this.server = SERVER_NAME;
    }

    @Override
    public Map<String, String> toStringMap() {
        Map<String,String> headers = new LinkedHashMap<>();
        headers.put("Server", this.server);
        headers.put("Date", this.date);
        headers.put("WWW-Authenticate", this.wwwAuthenticate);
        headers.put("Content-Type", this.contentType);
        headers.put("Last-Modified", this.lastModified);
        if(this.contentLength != 0){
            headers.put("Content-Length", Long.toString(this.contentLength));
        }
        headers.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue().isBlank());
        return headers;
    }

    @Override
    protected String generateHeaders() {
        String firstLine = String.format("%s %s %s",HTTP_Version, this.statusCode, this.reasonPhrase);
        return super.formatHeader(firstLine);
    }
}
