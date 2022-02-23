package src.messages.headers;

import src.messages.request.ByteStreamReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestHeaders extends Headers{
    Map<String, String> unidentifiedHeaders;
    public RequestHeaders(ByteStreamReader reader) throws IOException {
        super();
        String line = reader.getLine();
        this.unidentifiedHeaders = new LinkedHashMap<>();
        boolean isFirstLine = true;

        while(!line.equals("")) {
            String[] currentLine = line.split(" ");

            if(isFirstLine) {
                this.methodVerb = currentLine[0];
                this.path = currentLine[1];
                this.protocol = currentLine[2];
                isFirstLine = false;
                line = reader.getLine();
                continue;
            }
            if(currentLine.length < 2){
                System.out.println("Error: Not a Key value pair");
                line = reader.getLine();
                continue;
            }
            switch (currentLine[0]) {
                case "Host:" -> this.host = currentLine[1];
                case "Connection:" -> this.connection = currentLine[1];
                case "Accept:" -> this.accept = currentLine[1].split(",");
                case "Content-Length:" -> this.contentLength = Integer.parseInt(currentLine[1]);
                case "Content-Type:" -> this.contentType = currentLine[1];
                case "If-Modified-Since:" -> this.ifModifiedSince = line.replace("If-Modified-Since:", "").trim();
                case "Authorization:" -> {
                    this.authorization = (currentLine.length == 3)?currentLine[2]:currentLine[1];
                }
                case "Accept-Charset:" -> this.acceptCharSet = currentLine[1];
                default -> this.unidentifiedHeaders.put(currentLine[0],currentLine[1]);
            }
            line = reader.getLine();
        }
    }

    @Override
    public Map<String, String> toStringMap() {
        Map<String,String> headers = new LinkedHashMap<>();
        headers.put("Host", this.host);
        headers.put("Connection", this.connection);
        headers.put("Accept", String.join(" ", accept));
        headers.put("Content-Length", String.valueOf(this.contentLength));
        headers.put("If-Modified-Since", this.ifModifiedSince);
        headers.put("Accept-Charset", this.acceptCharSet);
        headers.putAll(this.unidentifiedHeaders);
        headers.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue().isBlank());
        return headers;
    }

    @Override
    protected String generateHeaders() {
        String firstLine = String.format("%s %s %s", this.methodVerb, this.path, this.protocol);
        return super.formatHeader(firstLine);
    }
}
