package src.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

final public class MimeTypes {
    private final static ConcurrentHashMap<String,String> types;
    private MimeTypes(){}

    static {
        types = new ConcurrentHashMap<>();
        try {
            FileReader mimeTypesFile = new FileReader("./conf/mime.types");
            BufferedReader reader = new BufferedReader(mimeTypesFile);
            String line = reader.readLine();

            while (line != null) {
                String[] currentLine = line.split("\\s+");
                if (currentLine.length <= 1 || line.charAt(0) == '#') {
                    line = reader.readLine();
                    continue;
                }

                for (int i = 1; i < currentLine.length; i++) {
                    types.put(currentLine[i], currentLine[0]);
                }
                line = reader.readLine();
            }
        }
        catch (IOException ignored){
        }
    }

    public static String getMimeTypeFromFile(String fileName){
        int extensionIndex = fileName.lastIndexOf('.');
        if(extensionIndex != -1){
            String extension = fileName.substring(extensionIndex + 1);
            return types.get(extension);
        }
        return "text/text";
    }

    public static void debugPrint(){
        System.out.println("-------- config.MimeTypes --------");
        for( HashMap.Entry<String,String> entry : types.entrySet()){
            System.out.printf("%s: %s\n",entry.getKey(), entry.getValue());
        }
        System.out.println("---------------------------");
    }
}
