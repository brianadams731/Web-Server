package src.actions;

import src.auth.Authorization;

import java.io.IOException;

public class AuthAction extends ServerAction{
    private String wwwAuth;
    private String userId;
    public AuthAction(String pathToResource, String encodedCred){
       super();
       this.checkAuth(pathToResource, encodedCred);
    }
    private void checkAuth(String pathToResource, String encodedCred){
        if(!Authorization.requiresAuth(pathToResource)){
            setStatus(ActionStatus.SUCCESS);
            return;
        }
        try {
            Authorization auth = new Authorization(pathToResource);
            if (encodedCred == null) {
                setStatus(ActionStatus.USER_ERROR);
                this.wwwAuth = String.format("Basic Realm=%s",auth.getAuthName());
                return;
            }
            if (auth.isUserAuthorized(encodedCred)) {
                setStatus(ActionStatus.SUCCESS);
                this.userId = auth.getUserId();
                return;
            }
            setStatus(ActionStatus.UNAUTHORIZED);
        }catch (IOException | SecurityException | NullPointerException exception) {
            System.out.println("SECURITY EXCEPTION");
            setStatus(ActionStatus.SERVER_ERROR);
        }catch (IllegalArgumentException exception){
            setStatus(ActionStatus.UNAUTHORIZED);
        }
    }

    public String getUserId() {
        return userId;
    }
    public String getWwwAuth() {
        return wwwAuth;
    }
}
