package MyPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MyOrder implements Serializable {
    private String from;
    private String foodName;
    private String resId;
    private String text;
    private  String clientId;
    public ArrayList<HashMap<String,Integer> >orderlist= new ArrayList<>();
    public HashMap<String,Integer> orders= new HashMap<>();

    public MyOrder(String clientId,String resId,ArrayList<HashMap<String,Integer>>  orderlist)
    {
        this.clientId=clientId;
        this.resId=resId;
        this.orderlist=orderlist;
    }

    public MyOrder(String foodName,String resId,HashMap<String,Integer> orders)
    {
        this.foodName=foodName;
        this.resId=resId;
        this.orders=orders;
    }
    public String getfoodName()
    {
        return foodName;
    }
    public String getResId()
    {
        return resId;
    }
    public String getClientId()
    {
        return clientId;
    }
    public  ArrayList<HashMap<String,Integer> > getOrderlist()
    {
        return orderlist;
    }
}
