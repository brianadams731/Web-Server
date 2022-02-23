package src.messages.request;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ByteStreamReader {
    private Charset defaultCharSet;
    private final DataInputStream dataStream;

    ByteStreamReader(InputStream inputStream) throws IOException{
        defaultCharSet = StandardCharsets.ISO_8859_1;
        this.dataStream = new DataInputStream(inputStream);
    }

    public String getLine() throws IOException {
        byte carriageReturn = "\r".getBytes(defaultCharSet)[0];
        byte nextLine = "\n".getBytes(defaultCharSet)[0];


        ArrayList<Byte> byteArray = new ArrayList<>();
        try {
            byte currentByte = dataStream.readByte();

            while (currentByte != nextLine) {
                if (currentByte != carriageReturn) {
                    byteArray.add(currentByte);
                }
                currentByte = dataStream.readByte();
            }
        } catch (EOFException ignored){}
        byte[] bytes = new byte[byteArray.size()];
        for(int i = 0; i< byteArray.size(); i++){
            bytes[i] = byteArray.get(i);
        }
        return new String(bytes,this.defaultCharSet);
    }

    public byte[] getNBytes(int n) throws IOException {
        return this.dataStream.readNBytes(n);
    }
    public void changeCharSet(Charset charset){
        this.defaultCharSet = charset;
    }
}
