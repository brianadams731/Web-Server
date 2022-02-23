package src.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public abstract class ServerAction {
    private ActionStatus status;
    ServerAction(){}

    protected void setStatus(ActionStatus status){
        this.status = status;
    }
    public ActionStatus getStatus(){
        return this.status;
    }
}
