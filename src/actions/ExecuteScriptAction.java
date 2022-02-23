package src.actions;

import src.cgi.ProcessRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ExecuteScriptAction extends ServerAction implements WithResource{
    Resource resource;
    public ExecuteScriptAction(
            String pathToResource,
            Map<String,String> environmentVariables,
            String queryString,
            String protocol,
            byte[] body
            ){
        try {
            ProcessRunner processRunner = new ProcessRunner(pathToResource, environmentVariables, queryString, protocol);

            if(body != null && body.length != 0){
                processRunner.writeIn(body);
            }

            this.resource = new Resource(processRunner.readOut());
            if(this.resource.getResource() == null){
                this.setStatus(ActionStatus.SERVER_ERROR);
                return;
            }
            this.setStatus(ActionStatus.SUCCESS);
        }catch (FileNotFoundException exception){
            this.setStatus(ActionStatus.RESOURCE_NOT_FOUND);
        }catch (IOException exception){
            this.setStatus(ActionStatus.SERVER_ERROR);
        }
    }

    @Override
    public Resource getResource() {
        return this.resource;
    }
}
