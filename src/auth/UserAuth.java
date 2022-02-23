package src.auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

class UserAuth {
    Map<String,String> permittedUsers;
    private String userId;
    UserAuth(String pathToAuthUserFile) throws IOException, SecurityException {
        permittedUsers = new HashMap<>();
        File authUsersFile = new File(pathToAuthUserFile);
        if(!authUsersFile.canRead()){
            if(authUsersFile.setReadable(true)){
                throw new SecurityException();
            }
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToAuthUserFile));
        String line = bufferedReader.readLine();
        while(line != null){
            String[] currentLine = line.split(":");
            if(currentLine.length < 2){
                continue;
            }
            String parsedValue = currentLine[1].replace("{SHA}", "").trim();
            permittedUsers.put(currentLine[0],parsedValue);
            line = bufferedReader.readLine();
        }
    }

    public void debugPrint(){
        for(Map.Entry<String,String> entry : permittedUsers.entrySet()){
            System.out.printf("%s : %s", entry.getKey(), entry.getValue());
        }
    }


    // CODE PROVIDED BY PROFESSOR
    public boolean isAuthorized( String authInfo ) throws IllegalArgumentException{
        if(authInfo == null){
            return false;
        }
        String credentials = new String(Base64.getDecoder().decode( authInfo ), StandardCharsets.UTF_8);

        String[] tokens = credentials.split( ":" );
        if(tokens.length < 2){
            System.out.println("Error: Auth Header user hash is malformed, lacks : when decoded");
            return false;
        }
        this.userId = tokens[0];

        String username = tokens[0];
        String password = encryptClearPassword(tokens[1]);
        return verifyUser(username, password);
    }

    private boolean verifyUser( String username, String password ) {
        for(Map.Entry<String,String> entry : permittedUsers.entrySet()){
            if(username.equals(entry.getKey()) && password.equals(entry.getValue())){
                return true;
            }
        }
        return false;
    }

    private String encryptClearPassword( String password ) throws IllegalArgumentException{
        try {
            MessageDigest mDigest = MessageDigest.getInstance( "SHA-1" );
            byte[] result = mDigest.digest( password.getBytes() );
            return Base64.getEncoder().encodeToString( result );
        } catch( Exception e ) {
            return "";
        }
    }

    public String getUserId(){
        return this.userId;
    }

    public boolean debugStripedTest(String username,String password){
        return verifyUser(username,password);
    }
    public boolean debugTestIsAuth(String username, String password){
        String pass = encryptClearPassword(password);
        return verifyUser(username, pass);
    }
}
