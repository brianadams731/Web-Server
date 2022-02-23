package src.logger;

public interface LoggableResponse {
    String getStatusCode(); // is the HTTP status code returned to the client. 2xx is a successful response, 3xx a redirection, 4xx a client error, and 5xx a server error.
    long getSizeOfResponse(); //  is the size of the object returned to the client, measured in bytes.
}
