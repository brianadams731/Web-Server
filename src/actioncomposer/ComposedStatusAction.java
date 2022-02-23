package src.actioncomposer;

import src.messages.request.Request;
import src.messages.response.StatusResponse;

import java.io.IOException;
import java.net.Socket;

class ComposedStatusAction extends ComposedAction{
    private final int statusCode;
    ComposedStatusAction(Socket client, Request request, int statusCode) throws IOException {
        super(client,request);
        this.statusCode = statusCode;
    }

    @Override
    void execute() {
        this.response = new StatusResponse(statusCode);
    }

}
