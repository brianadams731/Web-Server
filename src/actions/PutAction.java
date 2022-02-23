package src.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PutAction extends ServerAction {
    public PutAction(String pathName, byte[] fileContent){
        super();
        this.addFile(pathName, fileContent);
    }

    private void addFile(String pathToFile, byte[] fileContent){
        File fileToCreate = new File(pathToFile);
        File directoryToFile = new File(fileToCreate.getParent());
        try{
            directoryToFile.mkdirs();
            boolean fileDidNotExist = fileToCreate.createNewFile();
            if(!fileDidNotExist){
                boolean deletedSuccess = fileToCreate.delete();
                if(!deletedSuccess){
                    setStatus(ActionStatus.SERVER_ERROR);
                    return;
                }
            }
        } catch (IOException exception){
            setStatus(ActionStatus.SERVER_ERROR);
            exception.printStackTrace();
        }

        try(FileOutputStream fileOut = new FileOutputStream(fileToCreate)){
            fileOut.write(fileContent);
            setStatus(ActionStatus.SUCCESS);
        } catch (IOException exception){
            setStatus(ActionStatus.SERVER_ERROR);
        }
    }
}
