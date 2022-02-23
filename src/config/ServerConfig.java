package src.config;

import java.io.*;
import java.util.ArrayList;

final public class ServerConfig {
    private static int listen;
    private static String documentRoot;
    private static String logFile;
    private static final ArrayList<Alias> alias;
    private static final ArrayList<Alias> scriptAlias;

    private static String directoryIndex;
    private static String accessFileName;

    private static String serverRoot;


    private ServerConfig(){}
    static {
        alias = new ArrayList<>();
        scriptAlias = new ArrayList<>();

        assignDefaultValues();
        try {
            FileReader configFile = new FileReader("./conf/httpd.conf");
            BufferedReader reader = new BufferedReader(configFile);
            String line = reader.readLine();

            while(line != null){
                line = line.replace("\"", "");
                String[] currentLine = line.split(" ");
                if(currentLine.length<2 || line.isBlank() || line.charAt(0) == '#'){
                    line = reader.readLine();
                    continue;
                }
                switch (currentLine[0]) {
                    case "ServerRoot" -> serverRoot = currentLine[1];
                    case "DocumentRoot" -> documentRoot = currentLine[1];
                    case "Listen" -> listen = Integer.parseInt(currentLine[1]);
                    case "LogFile" -> logFile = currentLine[1];
                    case "AccessFileName" -> accessFileName = currentLine[1];
                    case "DirectoryIndex" -> directoryIndex = currentLine[1];
                    case "Alias" -> alias.add(new Alias(currentLine[1],currentLine[2]));
                    case "ScriptAlias" -> scriptAlias.add(new Alias(currentLine[1],currentLine[2]));
                }
                line = reader.readLine();
            }
        }catch (IOException exception){

        }
    }

    private static void assignDefaultValues(){
        listen = 8080;
        accessFileName = ".htaccess";
        directoryIndex = "index.html";
    }

    public static void debugPrint(){
        System.out.println("------ Server Config ------");
        System.out.printf("Listen: %s\n",listen);
        System.out.printf("DocumentRoot: %s\n",documentRoot);
        System.out.printf("Log File: %s\n",logFile);
        System.out.println("Alias:");
        for(Alias alias: alias){
            System.out.printf("%s: %s\n", alias.getSymbolicPath(), alias.getAbsolutePath());
        }
        System.out.println("Script Alias:");
        for(Alias alias: scriptAlias){
            System.out.printf("%s: %s\n", alias.getSymbolicPath(), alias.getAbsolutePath());
        }
        System.out.printf("Access File Name: %s\n", accessFileName);
        System.out.printf("Directory Index: %s\n", directoryIndex);
        System.out.printf("ServerRoot: %s\n",serverRoot);
        System.out.println("---------------------------");
    }

    public static int getListen() {
        return listen;
    }
    public static String getDocumentRoot() {
        return documentRoot;
    }
    public static String getLogFile() {
        return logFile;
    }
    public static String getAccessFileName() {
        return accessFileName;
    }
    public static String getDirectoryIndex() {
        return directoryIndex;
    }
    public static String getServerRoot() {
        return serverRoot;
    }
    public static ArrayList<Alias> getAlias(){
        return alias;
    }
    public static ArrayList<Alias> getScriptAlias(){
        return scriptAlias;
    }
}
