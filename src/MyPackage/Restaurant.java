package MyPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.lang.String;
public class Restaurant implements Serializable {
    private int id;
    private String name;
    private double score;
    private String price;
    private int zip;
    public ArrayList<String> category = new ArrayList<String>();
    public  ArrayList<Food> foods=new ArrayList<Food>();

   Restaurant(int id,String name, double score, String price, int zip,ArrayList<String> cat )
   {
       this.id=id;
       this.name=name;
       this.score=score;
       this.price=price;
       this.zip=zip;

       for(String s: cat)
       {
           category.add(s);
       }

   }
    public void displayDetails()
    {
        System.out.println("Restaurant Id: "+id);
        System.out.println("Restaurant Name: "+name);
        System.out.println("Score: "+score);
        System.out.println("Price: "+price);
        System.out.println("Zip code: "+zip);
        System.out.println("Category: ");
        for(int j=0;j<category.size();j++)
        {
            System.out.println(category.get(j));
        }
    }
    public void setId(int x)
    {
        id=x;
    }
    public void setName(String _name)
    {
        name=_name;
    }
    public void setScore(double n)
    {
        score=n;
    }
    public void setPrice(String s)
    {
        price=s;
    }
    public void setZip(int n)
    {
        zip=n;
    }
    public void setCategory(String ... s)
    {
        for (String str:s) {
            category.add(str);
        }
    }

    public  int getId()
    {
        return  this.id;
    }
    public String getName()
    {
        return this.name;
    }
    public double getScore()
    {
        return this.score;
    }
    public String getPrice()
    {
        return this.price;
    }
    public int getZip()
    {
        return this.zip;
    }

     public ArrayList<String> getCategory()
     {
        return this.category;
     }

     public ArrayList<Food> getFood()
     {
         return foods;
     }
    public ArrayList<String > getFoodName()
    {
        ArrayList<String> foodName= new ArrayList<>();
        for(int i=0;i<foods.size();i++)
        {
            foodName.add(foods.get(i).getName());
        }
        return foodName;
    }
}
