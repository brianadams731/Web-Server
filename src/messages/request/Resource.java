package src.messages.request;

import src.config.Alias;
import src.config.ServerConfig;

import java.util.HashMap;

class Resource {
    private HashMap<String, String> queryParameters;
    private String rawQueryString;
    private String pathToResource;
    private boolean isScript;

    Resource(String path){
        this.isScript = false;
        this.queryParameters = new HashMap<>();
        this.rawQueryString = "";
        if(path.isBlank()){
            return;
        }
        this.parseParams(path);
        this.parsePathToResource(path);
    }

    private void parseParams(String path){
        int queryIndex;
        if((queryIndex = path.indexOf('?')) != -1){
            this.rawQueryString = path.substring(queryIndex + 1);
            String[] params = path.substring(queryIndex + 1).split("&");
            for(String param:params){
                try{
                    String[] currentParam = param.split("=");
                    queryParameters.put(currentParam[0],currentParam[1]);
                }catch (IndexOutOfBoundsException exception){
                    System.out.println("Invalid Param");
                }
            }
        }
    }

    private void parsePathToResource(String path){
        String tempPath;
        Alias matchedAlias;
        if((matchedAlias = isInAlias(path)) != null){
            tempPath = path.replace(
                    matchedAlias.getSymbolicPath(),
                    matchedAlias.getAbsolutePath()
            );
        }else{
            tempPath = formatNonAlias(path);
        }
        int endOfPath = (tempPath.indexOf('?') != -1)? tempPath.indexOf('?'):tempPath.length();
        this.pathToResource = tempPath.substring(0,endOfPath);
    }

    private Alias isInAlias(String path){
        for(Alias alias: ServerConfig.getAlias()){
            if(path.contains(alias.getSymbolicPath())){
                return alias;
            }
        }
        for(Alias alias: ServerConfig.getScriptAlias()){
            if(path.contains(alias.getSymbolicPath())){
                this.isScript = true;
                return alias;
            }
        }
        return null;
    }

    private String formatNonAlias(String tempPath){
        int indexOfEnd = (tempPath.indexOf('?') != -1)? tempPath.indexOf('?'):tempPath.length();
        String path = String.format("%s%s",ServerConfig.getDocumentRoot(),tempPath.substring(0,indexOfEnd));
        if(path.charAt(path.length()-1) == '/'){
            path = String.format("%s%s",path,ServerConfig.getDirectoryIndex());
        }
        return path;
    }

    public void debugPrint(){
        System.out.println("--------- Resource ---------");
        System.out.printf("Path to resource: %s\n", this.pathToResource);
        System.out.println("Query Params:");
        for(String key:this.queryParameters.keySet()){
            System.out.printf("%s: %s\n",key, this.queryParameters.get(key));
        }
        System.out.printf("Raw Query String: %s\n", this.rawQueryString);
        System.out.println("----------------------------");
    }

    public String getRawQueryString() {
        return rawQueryString;
    }
    public HashMap<String, String> getQueryParameters() {
        return queryParameters;
    }
    public String getPathToResource() {
        return pathToResource;
    }
    public boolean getIsScript(){
        return this.isScript;
    }
}
