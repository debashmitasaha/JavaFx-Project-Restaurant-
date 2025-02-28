package MyPackage;

import util.SocketWrapper;

import java.io.IOException;
import java.util.ArrayList;

public class ReadThreadCustomerClient implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;

    public ReadThreadCustomerClient (SocketWrapper socketWrapper) {
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                String msg =(String) socketWrapper.read();
                System.out.println(msg);
            }
        } catch (Exception e) {
            System.out.println("Read thread customer exception");
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
