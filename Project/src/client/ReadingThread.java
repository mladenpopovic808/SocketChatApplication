package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadingThread implements Runnable {

    public Socket socket;

    public ReadingThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while (true) {
                msg = in.readLine();
                System.out.println(msg);
            }
        } catch (IOException e) {
            System.out.println("Soket je zatvoren,napustili ste chat!");
        }







    }
}
