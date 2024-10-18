package server;

public class MessageHistoryThread implements Runnable {

    public MainApp mainApp;


    public MessageHistoryThread(MainApp mainApp) {
        this.mainApp=mainApp;

    }
    @Override
    public void run() {

        while(true){
            if(mainApp.getMessageHistory().size()>100){ //TODO vrati na 100 kada zavrsis
                mainApp.getMessageHistory().remove(0);
                System.out.println("Prekoracen broj poruka.Brisem prvu");
            }
        }


}

}
