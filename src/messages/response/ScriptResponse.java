package src.messages.response;

import src.actions.Resource;
import src.actions.WithResource;

public class ScriptResponse extends Response{
    public ScriptResponse(WithResource resourceProvider){
        super();
        this.isOutputFromScript = true;
        this.addResourceToResponse(resourceProvider.getResource());
    }

    @Override
    public byte[] generateResponse() {
        return this.generateResponseWithBody();
    }
}
