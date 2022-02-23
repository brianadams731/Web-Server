package src.actioncomposer;

import src.actions.ActionStatus;
import src.actions.ExecuteScriptAction;
import src.messages.request.Request;
import src.messages.response.Response;
import src.messages.response.ScriptResponse;
import src.messages.response.StatusResponse;

import java.io.IOException;
import java.net.Socket;

class ComposedScriptRun extends ComposedAction{
    ComposedScriptRun(Socket client, Request request) throws IOException {
        super(client, request);
    }

    @Override
    void execute() {
        ExecuteScriptAction scriptAction = new ExecuteScriptAction(
                this.request.getResourcePath(),
                this.request.getHeaders(),
                this.request.getRawQueryString(),
                this.request.getProtocol(),
                this.request.getBody()
        );
        if(scriptAction.getStatus() != ActionStatus.SUCCESS){
            switch (scriptAction.getStatus()){
                case RESOURCE_NOT_FOUND -> this.response = new StatusResponse(404);
                case SERVER_ERROR -> this.response = new StatusResponse(500);
            }
        }else{
            this.response = new ScriptResponse(scriptAction);
        }
    }

}
