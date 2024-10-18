package server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Iterator;


public class ServerThread implements Runnable{

    public Socket clientSocket;
    public MainApp mainApp;
    public PrintWriter out;
    public BufferedReader in;


    public ServerThread(Socket clientSocket, MainApp mainApp) {
        this.clientSocket=clientSocket;
        this.mainApp=mainApp;


    }
    @Override
    public void run() {
        try {
            out=new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()),true);
            in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("Unesite username :");

            ///dok korisnik ne unese nepostojeci username
            String username=in.readLine();

            while(mainApp.getUserStreams().containsKey(username)){//atomicno

                out.println("exists");
                username=in.readLine();
            }
            System.out.println("Welcome "+username);

            printAllPreviousMessages();

            mainApp.welcomeUser(username,out);

            out.println("Uspesno ste prijavljeni pod username-om "+"\""+username+"\""+". Uzivajte u caskanju!");

            String userInput=in.readLine();
            while(!userInput.equals("exit")){
                String message=username+" : "+ mainApp.checkBadWords(userInput)+" ("+java.time.LocalTime.now()+" "+LocalDate.now()+")";
                mainApp.sendMessageToEveryone(message,username); ///upisuje u listu poruka
                userInput=in.readLine();

            }
            mainApp.removeUser(username);

        } catch (IOException e) {
            System.out.println("Zatvoren je serverThread usled error-a");
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
    private void printAllPreviousMessages(){
        Iterator<String> iterator=mainApp.getMessageHistory().iterator();
        while(iterator.hasNext()){
            out.println(iterator.next());
        }

    }

}


















