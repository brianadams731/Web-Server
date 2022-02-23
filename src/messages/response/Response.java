package src.messages.response;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

import src.actions.Resource;
import src.actions.WithResource;
import src.config.MimeTypes;
import src.logger.LoggableResponse;
import src.messages.Body;
import src.messages.headers.ResponseHeaders;

public abstract class Response implements LoggableResponse {
    protected ResponseHeaders headers;
    protected Body body;
    protected boolean isOutputFromScript;

    private static final HashMap<Integer,String> status;
    static {
        status = new HashMap<>();
        status.put(200,"OK");
        status.put(201,"Created");
        status.put(204,"No Content");
        status.put(304,"Not Modified");
        status.put(400,"Bad Request");
        status.put(401,"Unauthorized");
        status.put(403,"Forbidden");
        status.put(404,"Not Found");
        status.put(500,"Internal Server Error");
    }

    protected Response(){
        this.headers = new ResponseHeaders();
        this.body = new Body();
        this.isOutputFromScript = false;
    }

    public abstract byte[] generateResponse();

    protected byte[] generateResponseWithoutBody(){
        return this.headers.getHeadersWithEnd();
    }
    protected byte[] generateResponseWithBody(){
        if(this.body.getBody() == null){
            this.setStatus(500);
            return this.headers.getHeadersWithEnd();
        }
        byte[] headers = this.isOutputFromScript?this.headers.getHeadersWithoutEnd():this.headers.getHeadersWithEnd();
        byte[] body = this.getBody();
        return concatenateByteArray(headers, body);
    }

    protected void addResourceToResponse(Resource resource){
        if(resource == null || resource.getResource() == null){
            this.setStatus(500);
            return;
        }
        this.setBody(resource.getResource());
        this.setStatus(200);
        if(!this.isOutputFromScript){
            this.headers.setContentLength(resource.getResourceSize());
            this.headers.setContentType(resource.getResourceType());
        }
    }

    private byte[] concatenateByteArray(byte[] arrayOne, byte[] arrayTwo){
        byte[] retByte = new byte[arrayOne.length + arrayTwo.length];
        System.arraycopy(arrayOne, 0, retByte, 0, arrayOne.length);
        System.arraycopy(arrayTwo, 0, retByte, arrayOne.length, arrayTwo.length);
        return retByte;
    }

    @Override
    public String getStatusCode(){
        return this.headers.getStatusCode();
    }
    @Override
    public long getSizeOfResponse(){
        return this.headers.getContentLength();
    }

    protected byte[] getBody(){
        return this.body.getBody();
    }
    public void setStatus(int statusCode){
        this.headers.setStatus(statusCode, status.get(statusCode));
    }
    public void setLastModified(String lastModified){
        this.headers.setLastModified(lastModified);
    }
    protected void setBody(byte[] body){
        this.body.setBody(body);
    }
}
