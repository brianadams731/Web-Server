package src.auth;

import src.config.ServerConfig;

import java.io.File;
import java.io.IOException;

import java.nio.file.Paths;

public class Authorization {
    private final AccessFile accessFile;
    private final UserAuth userAuth;

    public Authorization(String resourceRequested) throws IOException, SecurityException{
        String accessFilePath = getAccessFileFromResourcePath(resourceRequested).getPath();
        this.accessFile = new AccessFile(accessFilePath);
        this.userAuth = new UserAuth(this.accessFile.getAuthUserFile());
    }

    public static boolean requiresAuth(String resourceRequested){
        try{
            File fileDir = getAccessFileFromResourcePath(resourceRequested);
            return fileDir.exists();
        }catch (NullPointerException exception){
            return false;
        }
    }


    public String getAuthName(){
        return this.accessFile.getAuthName();
    }
    public String getAuthType(){
        return this.accessFile.getAuthType();
    }
    public String getUserId(){
        return this.userAuth.getUserId();
    }

    private static File getAccessFileFromResourcePath(String resourceRequested){
        File parentDir = Paths.get(resourceRequested).getParent().toFile();
        return new File(String.format("%s/%s", parentDir, ServerConfig.getAccessFileName()));
    }
    public boolean isUserAuthorized(String encodedCred) throws IllegalArgumentException{
        return this.userAuth.isAuthorized(encodedCred);
    }

    public boolean debugTestIsAuth(String username, String password){
        return this.userAuth.debugTestIsAuth(username,password);
    }
    public boolean debugStripedTestIsAuth(String username, String encryptedPass){
        return this.userAuth.debugStripedTest(username,encryptedPass);
    }
}
