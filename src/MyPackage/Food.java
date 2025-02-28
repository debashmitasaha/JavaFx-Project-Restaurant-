package MyPackage;

import java.io.Serializable;

public class Food implements Serializable {
    private int restaurantId;
    private String category;
    private String name;
    private double price;

    public  int count =0;
    Food(int restaurantId,String category,String name,double price)
    {
        this.name=name;
        this.restaurantId=restaurantId;
        this.price=price;
        this.category=category;
    }

    public void displayDetails()
    {
        System.out.println("Food Name: "+name);
        System.out.println("Restaurant Id: "+restaurantId);
        System.out.println("Price: "+price);
        System.out.println("Category: "+category);
    }
    public void setRestaurantId(int id)
    {
        restaurantId=id;
    }
    public void setCategory(String s)
    {
        category=s;
    }
    public void setName(String s)
    {
        name=s;
    }
    public void setPrice(double p)
    {
        price=p;
    }

    public int getRestaurantId()
    {
        return this.restaurantId;
    }
    public String getCategory()
    {
        return this.category;
    }
    public String getName()
    {
        return this.name;
    }
    public double getPrice()
    {
        return this.price;
    }
}
