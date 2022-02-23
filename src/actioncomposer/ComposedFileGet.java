package src.actioncomposer;

import src.actions.*;
import src.messages.request.Request;
import src.messages.response.FileResponse;
import src.messages.response.StatusResponse;

import java.io.IOException;
import java.net.Socket;

class ComposedFileGet extends ComposedAction {
    ComposedFileGet(Socket client, Request request) throws IOException {
        super(client, request);
    }

    @Override
    void execute() {
        ServerAction cache = new CacheAction(request.getResourcePath(), request.getIfModifiedSince());
        if(cache.getStatus() == ActionStatus.UNMODIFIED){
            this.response = new StatusResponse(304);
            return;
        }

        GetAction get = new GetAction(request.getResourcePath());
        if(get.getStatus() != ActionStatus.SUCCESS){
            switch (get.getStatus()){
                case RESOURCE_NOT_FOUND -> this.response = new StatusResponse(404);
                case SERVER_ERROR -> this.response = new StatusResponse(500);
            }
        }else{
            this.response = new FileResponse(get);
            this.response.setLastModified(((WithPayload)get).getPayload());
        }
    }
}
