package main.java.clientFX;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientFX  {
    public ObjectOutputStream oos ;
    private ObjectInputStream ois;

    public ClientFX(String address , int port) throws IOException {
        Socket client = new Socket(address, port);
        this.oos = new ObjectOutputStream(client.getOutputStream());
        this.ois = new ObjectInputStream(client.getInputStream());
    }

}

