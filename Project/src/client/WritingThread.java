package client;


import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WritingThread implements Runnable{

    private String username;
    private ClientMain client;
    private Socket socket;


    public WritingThread(String username, ClientMain client, Socket socket) {
        this.username = username;
        this.client=client;
        this.socket=socket;
    }
    
    @Override
    public void run() {
        try{
            //System.out.println(username);
        //PrintWriter out= MainApp.userStreams.get(username); // zasto ne radi?
        PrintWriter out=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        Scanner sc=new Scanner(System.in);

        String msg=sc.nextLine();

        while(!msg.equals("exit")){

            out.println(msg);///saljemo serveru
            msg=sc.nextLine();
        }
        out.println(msg);
        out.close();
        client.killProcesses();

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
