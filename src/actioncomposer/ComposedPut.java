package src.actioncomposer;

import src.actions.ActionStatus;
import src.actions.PutAction;
import src.actions.ServerAction;
import src.config.MimeTypes;
import src.messages.request.Request;
import src.messages.response.StatusResponse;

import java.io.IOException;
import java.net.Socket;

class ComposedPut extends ComposedAction{
    ComposedPut(Socket client, Request request) throws IOException {
        super(client, request);
    }

    @Override
    void execute() {
        ServerAction put = new PutAction(this.request.getResourcePath(), this.request.getBody());
        if(put.getStatus() != ActionStatus.SUCCESS){
            switch (put.getStatus()){
                case RESOURCE_ALREADY_EXISTS -> this.response = new StatusResponse(409);
                case SERVER_ERROR -> this.response = new StatusResponse(500);
            }
        }else{
            this.response = new StatusResponse(201);
        }
    }
}
