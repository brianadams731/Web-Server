package src.logger;

public interface LoggableRequest {
    String getHost(); // is the IP address of the client (remote host) which made the request to the server.
    String getUserId(); // is the userid of the person requesting the document. Usually "-" unless .htaccess has requested authentication.
    String getIdentity(); //is the RFC 1413 identity of the client. Usually "-".
    String getMethodVerb(); // http method
    String getPath(); // the resource requested
    String getTimeOfRequest();  //is the date, time, and time zone that the request was received, by default in strftime format %d/%b/%Y:%H:%M:%S %z.
    String getProtocol();
}
