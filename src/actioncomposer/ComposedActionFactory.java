package src.actioncomposer;

import src.logger.Logger;
import src.messages.request.Request;
import src.messages.response.Response;
import src.messages.response.StatusResponse;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class ComposedActionFactory implements Runnable{
    Socket client;
    public ComposedActionFactory(Socket client){
        this.client = client;
    }

    private ComposedAction getComposedAction(Socket client) throws IOException {
        Request request = new Request(client.getInputStream());
        if(request.getIsScript()){
            return new ComposedScriptRun(client, request);
        }
        if(request.getMethodVerb().equalsIgnoreCase("GET")){
            return new ComposedFileGet(client, request);
        }else if(request.getMethodVerb().equalsIgnoreCase("HEAD")){
            return new ComposedHead(client, request);
        }else if(request.getMethodVerb().equalsIgnoreCase("POST")){
            return new ComposedFileGet(client, request);
        }else if(request.getMethodVerb().equalsIgnoreCase("PUT")){
            return new ComposedPut(client, request);
        }else if(request.getMethodVerb().equalsIgnoreCase("DELETE")){
            return new ComposedDelete(client, request);
        }else{
            return new ComposedStatusAction(client, request, 400);
        }
    }

    @Override
    public void run(){
        try{
            ComposedAction actions = this.getComposedAction(client);
        } catch (Exception exception){
            // This will only run when there is a complete failure to parse request, and an unchecked
            // exception is thrown in Request any failure to execute an action will be handled in ComposedAction!
            try {
                Response statusResponse = new StatusResponse(400);
                this.client.getOutputStream().write(statusResponse.generateResponse());
                this.client.close();
                Logger.writeLog(null, statusResponse, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
