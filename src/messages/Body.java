package src.messages;

import src.messages.request.ByteStreamReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Body {
    private byte[] content;
    public Body(){}
    public Body(ByteStreamReader reader, int contentLength) throws IOException {
        content = reader.getNBytes(contentLength);
    }

    public byte[] getBody() {
        return this.content;
    }

    public void setBody(byte[] body){
        this.content = body;
    }

    public void debugPrint(){
        System.out.println("-------- Body --------");
        System.out.printf("Content: %s\n", new String(content, StandardCharsets.ISO_8859_1));
    }
}
