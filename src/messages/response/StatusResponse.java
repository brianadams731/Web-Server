package src.messages.response;

public class StatusResponse extends Response{
    public StatusResponse(int statusCode){
        super();
        this.setStatus(statusCode);
    }

    public StatusResponse(int statusCode, String wwwAuth){
        super();
        this.setStatus(statusCode);
        this.headers.setWwwAuthenticate(wwwAuth);
    }

    public StatusResponse(int statusCode, long contentLength, String contentType) {
        super();
        this.setStatus(statusCode);
        this.headers.setContentLength(contentLength);
        this.headers.setContentType(contentType);
    }

    @Override
    public byte[] generateResponse() {
        return this.generateResponseWithoutBody();
    }
}
