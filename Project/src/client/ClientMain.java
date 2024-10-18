package client;

import server.MainApp;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {

    public PrintWriter out;
    public BufferedReader in;
    public Socket serverSocket;
    private Thread readingThread;
    private Thread writingThread;

    public ClientMain(){

        try {
            serverSocket=new Socket("localhost", MainApp.PORT);
            in=new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            out=new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()),true);
            Scanner sc=new Scanner(System.in);

            ///dobrodosli,unesite username
            String msg=in.readLine();
            System.out.println(msg);

            ///username
            String username=sc.nextLine();
            out.println(username);
            msg=in.readLine();

            while(msg.equals("exists")){
                System.out.println("Taj username vec postoji,unesite ponovo :");
                out.println(sc.nextLine());
                msg=in.readLine();
            }
            ///Uspesno ste prijavljeni pod username-om "username". Uzivajte u caskanju!
            System.out.println(msg);
            //out.println("Cao server");

            readingThread=new Thread(new ReadingThread(serverSocket));
            writingThread=new Thread(new WritingThread(username,this,serverSocket)); ///prosledjujem this,da bih mogao da ubijem procese
            readingThread.start();
            writingThread.start();
            readingThread.join();
            writingThread.join();
            ///napraviti threadove za citanje i pisanje.Kod pisanja,kada napisemo exit,clientMain ce da interuptuje ta 2 thread-a


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            killProcesses();
            System.out.println("Client je zatvoren");
        }

    }
    public void killProcesses(){
        try {
            readingThread.interrupt();
            writingThread.interrupt();
            in.close();
            out.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new ClientMain();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
