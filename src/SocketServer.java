package src;

import src.config.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {
    private static ServerSocket instance;
    private SocketServer(){}

    public static ServerSocket getInstance() throws IOException{
        if(instance == null){
            instance = new ServerSocket(ServerConfig.getListen());
            System.out.printf("Server started on port %d\n", ServerConfig.getListen());
        }
        return instance;
    }
}
