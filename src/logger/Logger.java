package src.logger;

import src.config.ServerConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    static private final File logFile;
    private Logger(){}
    static {
        logFile = new File(ServerConfig.getLogFile());
        File logsDir = new File(logFile.getParent());
        if (!logsDir.exists()){
            logsDir.mkdirs();
        }
    }

    public static synchronized void writeLog(LoggableRequest req, LoggableResponse res, String userId) {
        try{
            logFile.createNewFile();
            FileWriter fileWriter = new FileWriter(logFile,true);
            fileWriter.write(generateLog(req, res, userId));
            System.out.println(generateLog(req,res,userId));
            fileWriter.close();
        }catch (IOException exception){
            System.out.println("ERROR: Failed to Log");
            exception.printStackTrace();
        }
    }
    private static String wrapLog(String field){
        try{
            return (field == null || field.length() == 0)?"-":field;
        }catch (NullPointerException exception){
            return "-";
        }
    }

    private static String getTimeOfLog(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss Z");
        return dateFormatter.format(new Date());
    }

    private static String generateLog(LoggableRequest req, LoggableResponse res, String userId){
        if(req == null){
            // This will only be used to log when request cannot be parsed
            return String.format("- - - [%s] \"- - -\" %s %s\n",getTimeOfLog(), res.getStatusCode(), res.getSizeOfResponse());
        }
        return String.format("%s %s %s [%s] \"%s %s %s\" %s %s\n",
                wrapLog(req.getHost()),
                wrapLog(req.getUserId()),
                wrapLog(userId),
                wrapLog(req.getTimeOfRequest()),
                wrapLog(req.getMethodVerb()),
                wrapLog(req.getPath()),
                wrapLog(req.getProtocol()),
                res.getStatusCode(),
                res.getSizeOfResponse()
        );
    }
}
