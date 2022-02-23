package src.auth;

import java.io.*;

class AccessFile {
    private String authUserFile;
    private String authType;
    private String authName;
    private String require;

    AccessFile(String pathToAccessFile) throws IOException{
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new FileReader(pathToAccessFile));
        String line = bufferedReader.readLine();

        while(line != null){
            String[] currentLine = line.split(" ");
            if(currentLine.length < 2){
                line = bufferedReader.readLine();
                continue;
            }
            switch (currentLine[0]){
                case "AuthUserFile" -> this.authUserFile = currentLine[1].replaceAll("\"", "");
                case "AuthType" -> this.authType = currentLine[1];
                case "AuthName" -> this.authName = line
                        .replaceAll("AuthName", "")
                        .replaceAll("\"","")
                        .trim();
                case "Require" -> this.require = currentLine[1];
            }
            line = bufferedReader.readLine();
        };
    }

    public void debugPrint(){
        System.out.printf("AuthUserFile: %s\n", this.authUserFile);
        System.out.printf("AuthType: %s\n", this.authType);
        System.out.printf("AuthName: %s\n", this.authName);
        System.out.printf("Require: %s\n", this.require);
    }

    public String getAuthUserFile() {
        return authUserFile;
    }
    public String getAuthType() {
        return authType;
    }
    public String getAuthName() {
        return authName;
    }
    public String getRequire() {
        return require;
    }
}
