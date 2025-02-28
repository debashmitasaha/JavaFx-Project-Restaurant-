package MyPackage;

import util.SocketWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadThreadRestaurentClient implements Runnable {

    private Thread thr;
    private SocketWrapper socketWrapper;
    public RestaurantDatabase restaurantManagement = new RestaurantDatabase();

    public ReadThreadRestaurentClient(SocketWrapper socketWrapper, ArrayList<HashMap<String,Integer>> orderUpdate) {
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o= socketWrapper.read();
                if(o instanceof MyOrder)
                {
                    System.out.println("pao");
                    MyOrder obj=(MyOrder) o;
                    int id= Integer.parseInt(obj.getResId());
                    //ArrayList<HashMap<String,Integer> > foodList=obj.getOrderlist();

                    HashMap<String,Integer> map= obj.orders;
                    String food= obj.getfoodName();
                    int count=map.get(food);

                    System.out.println("Ordered Items: ");
                    System.out.println("Item: "+food+ " Count:"+ count  );

                }
                else{
                    String s = (String) o;
                    System.out.println(s);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



