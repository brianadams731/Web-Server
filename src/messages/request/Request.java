package src.messages.request;

import src.logger.LoggableRequest;
import src.messages.Body;
import src.messages.headers.GivesHeaders;
import src.messages.headers.Headers;
import src.messages.headers.RequestHeaders;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class Request implements LoggableRequest, GivesHeaders {
    static private final SimpleDateFormat dateFormatter;
    private final String timeOfRequest;
    private final Resource resource;
    private final Headers headers;
    private final Body body;

    static {
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss Z");
    }

    public Request(InputStream inputStream) throws IOException{
        this.timeOfRequest = dateFormatter.format(new Date());
        ByteStreamReader dataStream = new ByteStreamReader(inputStream);
        this.headers = new RequestHeaders(dataStream);
        this.resource = new Resource(headers.getPath());
        this.body = new Body(dataStream, (int) headers.getContentLength());
    }


    public void debugPrint(){
        System.out.println("------------ Request ------------");
        System.out.printf("Time of Request %s\n", this.timeOfRequest);
        this.headers.debugPrint();
        this.body.debugPrint();
        this.resource.debugPrint();
        System.out.println("---------------------------------");
    }

    public String getResourcePath(){
        return this.resource.getPathToResource();
    }
    public String getContentType(){
        return this.headers.getContentType();
    }
    public String getIfModifiedSince(){
        return this.headers.getIfModifiedSince();
    }
    public Map<String, String> getQueryParams(){
        return this.resource.getQueryParameters();
    }
    public String getRawQueryString(){
        return this.resource.getRawQueryString();
    }
    public boolean getIsScript(){
        return this.resource.getIsScript();
    }
    public String getAuthHeader(){
        return this.headers.getAuthorization();
    }
    public byte[] getBody(){
        return this.body.getBody();
    }

    @Override
    public Map<String,String> getHeaders(){
        return this.headers.toStringMap();
    }
    @Override
    public String getHost() {
        return this.headers.getHost();
    }
    @Override
    public String getUserId() {
        return null;
    }
    @Override
    public String getIdentity() {
        return null;
    }

    @Override
    public String getMethodVerb() {
        return this.headers.getMethodVerb();
    }

    @Override
    public String getPath() {
        return this.headers.getPath();
    }

    @Override
    public String getTimeOfRequest() {
        return this.timeOfRequest;
    }

    @Override
    public String getProtocol() {
        return this.headers.getProtocol();
    }
}
