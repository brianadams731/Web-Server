package src.messages.response;

import src.actions.Resource;
import src.actions.WithResource;

public class FileResponse extends Response{
    public FileResponse(WithResource resourceProvider){
        super();
        this.isOutputFromScript = false;
        this.addResourceToResponse(resourceProvider.getResource());
    }

    @Override
    public byte[] generateResponse() {
        return this.generateResponseWithBody();
    }
}
