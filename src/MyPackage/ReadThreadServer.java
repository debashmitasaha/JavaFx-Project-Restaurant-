package MyPackage;

import util.SocketWrapper;

import java.io.IOException;
import java.util.HashMap;

import MyPackage.MyOrder;

public class ReadThreadServer implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;
    public HashMap<String, SocketWrapper> RestaurantClientMap= new HashMap<>();

    public ReadThreadServer(SocketWrapper socketWrapper, HashMap<String, SocketWrapper> RestaurantClientMap) {
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        this.RestaurantClientMap=RestaurantClientMap;
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o=socketWrapper.read();
                if( o instanceof MyOrder)
                {
                    System.out.println("Customer has placed a order.");

                    MyOrder order= (MyOrder) o;
                    String resId= order.getResId();
                    System.out.println(resId);

                    SocketWrapper ResSocketWrapper= RestaurantClientMap.get(resId);
                    if(ResSocketWrapper==null)
                    {
                        socketWrapper.write((Object) "Can't Place Order in this restaurant. Please try ordering in another restaurant.");
                    }
                    ResSocketWrapper.write((Object) "A customer has placed an order in your restaurant.");
                    ResSocketWrapper.write(order);

                }
                else {

                    String s = (String) socketWrapper.read();
                    String[] str = s.split(",");
                    System.out.println(s);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                System.out.println("henlo");
            }
        }
    }
}



