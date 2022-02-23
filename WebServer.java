import src.SocketServer;
import src.actioncomposer.ComposedActionFactory;
import src.config.MimeTypes;
import src.config.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class WebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = SocketServer.getInstance();
        while(true){
            Socket client = server.accept();
            Thread tread = new Thread(new ComposedActionFactory(client));
            tread.start();
        }
    }

}
