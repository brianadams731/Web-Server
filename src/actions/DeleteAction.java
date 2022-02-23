package src.actions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DeleteAction extends ServerAction {
    public DeleteAction(String pathToResource){
        super();
        this.removeFile(pathToResource);
    }

    private void removeFile(String pathToFile){
        File toDelete = new File(pathToFile);
        if(!toDelete.exists()){
            this.setStatus(ActionStatus.RESOURCE_NOT_FOUND);
            return;
        }
        try{
            Files.deleteIfExists(toDelete.toPath());
            this.setStatus(ActionStatus.SUCCESS);
        }catch (IOException exception){
            this.setStatus(ActionStatus.SERVER_ERROR);
        }
    }
}
