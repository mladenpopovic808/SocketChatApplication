package server;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainApp {

    public static List<String> forbiddenWords;
    public volatile List<String> messageHistory;
    public static Map<String, PrintWriter>userStreams=new ConcurrentHashMap<>();
    public static  ServerSocket serverSocket;
    public static int PORT=8000;

    public MainApp() throws Exception{
        forbiddenWords=new ArrayList<>();
        forbiddenWords.add("idiot");
        forbiddenWords.add("slepac");
        forbiddenWords.add("majmun");
        forbiddenWords.add("kreten");
        forbiddenWords.add("debil");
        forbiddenWords.add("bakaprase");
        messageHistory= new CopyOnWriteArrayList<>();///koristicemo iterator

        serverSocket=new ServerSocket(PORT);
        System.out.println("Server pokrenut na portu "+PORT);

        Thread messageHistory=new Thread(new MessageHistoryThread(this));
        messageHistory.start();

        while(true){
            Socket clientSocket=serverSocket.accept();
            Thread serverThread=new Thread(new ServerThread(clientSocket,this));
            serverThread.start();
        }
    }

    public void welcomeUser(String newUsername,PrintWriter out) {

        //block lock,provera jos jednom za ime
        userStreams.put(newUsername,out);
        String message = "Caskanju se pridruzio korisnik " + "\"" + newUsername + "\"";
        messageHistory.add(message);

        for (Map.Entry<String, PrintWriter> entry : userStreams.entrySet()) {

            PrintWriter out2 = entry.getValue();

            ///da ne bismo slali novom korisniku obavestenje da je on nov
            if (!newUsername.equals(entry.getKey())) {
                out2.println(message);
            }
        }
    }
    public void sendMessageToEveryone(String message,String sender){

        messageHistory.add(message);

        for(Map.Entry<String,PrintWriter> entry :userStreams.entrySet()){
            ///da ne bismo ispisivali 2 put korisniku poruku koju je on ispisao.
            if(!entry.getKey().equals(sender)){
                entry.getValue().println(message);
            }
        }
    }
    public void removeUser(String username){
        System.out.println("Usao da obrisem korisnika");
        try {
            userStreams.get(username).close();
            userStreams.remove(username);
            String msg="Korisnik "+username+" je izasao.";
            sendMessageToEveryone(msg,username);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public String checkBadWords(String sentence){

        String split[] = sentence.split(" ");
        String finalSentence="";
        for (String s:split) {
            if(forbiddenWords.contains(s)){
                for(int i=0;i<s.length();i++){
                    if(i==0 || i==s.length()-1){
                        finalSentence+=s.charAt(i);
                        continue;
                    }
                    finalSentence+="*";
                }
                finalSentence+=" ";
            }else{
                finalSentence+=s+" ";
            }
        }
        return finalSentence;
    }

    public static void main(String[] args) {
        try{
            new MainApp();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, PrintWriter> getUserStreams() {
        return userStreams;
    }

    public List<String> getMessageHistory() {
        return messageHistory;
    }
}
