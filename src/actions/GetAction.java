package src.actions;

import src.config.MimeTypes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;

public class GetAction extends ServerAction implements WithResource, WithPayload {
    static SimpleDateFormat lastModifiedFormat;
    Resource resource;
    String lastModified;

    static {
        lastModifiedFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
    }
    public GetAction(String pathToResource){
        super();
        this.getFile(pathToResource);
    }

    private void getFile(String pathToResource){
        try{
            File file = new File(pathToResource);
            if(!file.exists()){
                this.setStatus(ActionStatus.RESOURCE_NOT_FOUND);
                return;
            }
            resource = new Resource(
                    Files.readAllBytes(file.toPath()),
                    Files.size(file.toPath()),
                    MimeTypes.getMimeTypeFromFile(file.getName())
            );
            this.setStatus(ActionStatus.SUCCESS);
            this.lastModified = lastModifiedFormat.format(file.lastModified());
        }catch (IOException exception){
            this.setStatus(ActionStatus.SERVER_ERROR);
        }catch (IllegalArgumentException ignored){
            System.out.println("ERROR");
        }
    }

    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public String getPayload(){
        return this.lastModified;
    }
}
