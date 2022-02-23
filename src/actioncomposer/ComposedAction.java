package src.actioncomposer;

import src.actions.*;
import src.logger.Logger;
import src.messages.request.Request;
import src.messages.response.Response;
import src.messages.response.StatusResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public abstract class ComposedAction {
    protected Socket client;
    protected Request request;
    protected Response response;
    private final AuthAction auth;
    protected ComposedAction(Socket client, Request request) throws IOException {
        this.client = client;
        this.request = request;

        auth = new AuthAction(request.getResourcePath(), request.getAuthHeader());
        if(this.auth.getStatus() != ActionStatus.SUCCESS){
            switch (this.auth.getStatus()){
                case UNAUTHORIZED -> this.response = new StatusResponse(403);
                case USER_ERROR -> this.response = new StatusResponse(401, auth.getWwwAuth());
                case SERVER_ERROR -> this.response = new StatusResponse(500);
            }
            this.sendResponse();
            return;
        }
        try{
            execute();
        }catch (Exception exception){
            // This will execute if there is an unchecked exception thrown in ANY ACTION other than auth!
            this.response = new StatusResponse(500);
        }
        this.sendResponse();
    }

    abstract void execute();

    private void sendResponse() throws IOException{
        this.client.getOutputStream().write(this.response.generateResponse());
        this.client.close();
        Logger.writeLog(this.request, this.response, this.auth.getUserId());
    }
}
