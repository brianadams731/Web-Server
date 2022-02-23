package src.actions;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CacheAction extends ServerAction{
    static SimpleDateFormat dateFormat;
    static{
        dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
    }
    public CacheAction(String pathToResource, String userLastModified){
        // Tue, 15 Nov 1994 12:45:26 GMT <- IMF-fixdate
        super();
        if(pathToResource == null || userLastModified == null){
            this.setStatus(ActionStatus.SUCCESS);
            return;
        }
        File file = new File(pathToResource);
        if(!file.exists()){
            this.setStatus(ActionStatus.RESOURCE_NOT_FOUND);
            return;
        }

        long fileLastModified = floorToSecond(file.lastModified());
        try{
            long requestLastModified = dateFormat.parse(userLastModified).getTime();
            if(fileLastModified > requestLastModified){
                this.setStatus(ActionStatus.MODIFIED);
                return;
            }
            this.setStatus(ActionStatus.UNMODIFIED);
        }catch (ParseException exception){
            System.out.println("ERROR: Incorrect format, please use IMF-fixdate format!!!!");
            this.setStatus(ActionStatus.USER_ERROR);
        }
    }
    private long floorToSecond(long valueToFloor){
        long remainder = valueToFloor % 1000;
        return valueToFloor - remainder;
    }
}
