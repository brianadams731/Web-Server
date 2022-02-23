package src.cgi;

import src.config.ServerConfig;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ProcessRunner {
    private final Process process;
    private final OutputStream inputToProcess;
    private final InputStream outputFromProcess;
    private final InputStream errorStream;


    public ProcessRunner(String pathToResource,
                         Map<String,String> environmentVariables,
                         String queryString,
                         String protocol) throws IOException, FileNotFoundException{
        File resource = new File(pathToResource);
        if(!resource.exists()){
            throw new FileNotFoundException();
        }
        ProcessBuilder processBuilder = new ProcessBuilder(resource.getName());
        processBuilder.environment().putAll(this.formatEnvironmentVariables(environmentVariables, protocol, queryString));
        processBuilder.directory(resource.getParentFile());
        process = processBuilder.start();
        inputToProcess = process.getOutputStream();
        outputFromProcess = process.getInputStream();
        errorStream = process.getErrorStream();
    }


    private Map<String,String> formatEnvironmentVariables(Map<String,String> inMap, String queryString, String protocol){
        Map<String, String> retMap = new HashMap<>();
        for(Map.Entry<String,String> entry : inMap.entrySet()){
            String formattedKey = entry.getKey().toUpperCase().replaceAll("-","_");
            retMap.put(String.format("HTTP_%s", formattedKey),entry.getValue());
        }
        retMap.put("SERVER_PROTOCOL", protocol);
        retMap.put("QUERY_STRING", queryString);
        return retMap;
    }

    public void end(){
        process.destroy();
    }

    public void writeIn(byte[] input) {
        try{
            this.inputToProcess.write(input);
            this.inputToProcess.flush();
            this.inputToProcess.close();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public byte[] readOut() {
        try {
            byte[] output = this.outputFromProcess.readAllBytes();
            byte[] error = this.errorStream.readAllBytes();
            int exit = this.process.exitValue();
            process.destroy();
            if(exit != 0 || error.length > 0){
                return null;
            }
            return output;
        }catch (IOException ignored){
        }finally {
            process.destroy();
        }
        return null;
    }

    public void debugPrintOut(){
        try{
            String out = new String(this.outputFromProcess.readAllBytes());
            System.out.println(out);
            process.destroy();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
