package src.actioncomposer;

import src.actions.ActionStatus;
import src.actions.DeleteAction;
import src.actions.ServerAction;
import src.messages.request.Request;
import src.messages.response.StatusResponse;

import java.io.IOException;
import java.net.Socket;

class ComposedDelete extends ComposedAction{
    ComposedDelete(Socket client, Request request) throws IOException {
        super(client, request);
    }

    @Override
    void execute() {
        ServerAction delete = new DeleteAction(request.getResourcePath());
        if(delete.getStatus() != ActionStatus.SUCCESS){
            switch (delete.getStatus()){
                case RESOURCE_NOT_FOUND -> this.response = new StatusResponse(404);
                case SERVER_ERROR -> this.response = new StatusResponse(500);
            }
        }else {
            this.response = new StatusResponse(204);
        }
    }
}
