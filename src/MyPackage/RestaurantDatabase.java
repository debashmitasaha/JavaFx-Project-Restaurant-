package MyPackage;
import MyPackage.Restaurant;

import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class RestaurantDatabase implements Serializable {

        public  ArrayList<Restaurant> restaurants=new ArrayList<Restaurant>();
        public Set<String> set = new HashSet<String> ();

        public void loadResInfoFromFile(String rInput,String fInput)
        {
            new Thread(()->{
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(rInput));
                } catch (FileNotFoundException e) {
                    System.out.println("Exception in server class 5");
                }
                while (true) {
                    String line = null;
                    try {
                        line = br.readLine();
                    } catch (IOException e) {
                        System.out.println("Exception in server class 4");
                    }
                    if (line == null) break;
                    //System.out.println(line);
                    String [] array = line.split(",", -1);
                    int id= Integer.parseInt(array[0]);
                    String _name= array[1];
                    double scr= Double.parseDouble(array[2]);
                    String prc= array[3];
                    int zip=Integer.parseInt(array[4]);
                    ArrayList<String> temp= new ArrayList<String >();
                    for(int i=5;i< array.length;i++)
                    {
                        temp.add(array[i]);
                        set.add(array[i].toUpperCase());
                    }

                    Restaurant res= new Restaurant(id,_name,scr,prc,zip,temp);
                    restaurants.add(res);

                }
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Exception in server class 6");
                }
            }).start();

            new Thread(()-> {
                BufferedReader br2 = null;
                try {
                    br2 = new BufferedReader(new FileReader(fInput));
                } catch (FileNotFoundException e) {
                    System.out.println("Exception in server class 2");
                }
                while (true) {
                    String line = null;
                    try {
                        line = br2.readLine();
                    } catch (IOException e) {
                        System.out.println("Exception in server class 3");
                    }
                    if (line == null) break;
                    String [] array = line.split(",", -1);
                    int id= Integer.parseInt(array[0]);
                    String _cat= array[1];
                    String _name= array[2];
                    double prc= Double.parseDouble(array[3]);

                    Food food= new Food(id,_cat,_name,prc);
                    for(int j=0;j<restaurants.size();j++)
                    {
                        if(id== restaurants.get(j).getId())
                        {
                            String resName= restaurants.get(j).getName();
                            restaurants.get(j).foods.add(food);
                            break;
                        }
                    }

                }
                try {
                    br2.close();
                } catch (IOException e) {
                    System.out.println("Exception in server class 7");
                }
            }).start();

        }
    public ArrayList<Restaurant> searchByRestaurantName(String restaurantName)
    {
        ArrayList<Restaurant> result= new ArrayList<Restaurant>();
        String userInput=restaurantName.toUpperCase();
        int count=0;
        for(Restaurant r: restaurants)
        {
            if( ((r.getName()).toUpperCase())  .contains(userInput) )
            {
                result.add(r);
            }
        }
        return result;
    }
    public ArrayList<Restaurant> restaurantSearchByCategory(String cat)
    {

        ArrayList<Restaurant> result= new ArrayList<Restaurant>();
        String userInput=cat.toUpperCase();
        for(int i=0;i<restaurants.size();i++)
        {
            for(int j = 0; j<((restaurants.get(i)).category).size(); j++)
            {
                if(((restaurants.get(i).category.get(j)) .toUpperCase()).contains(userInput)  )
                {
                    result.add(restaurants.get(i));
                }
            }
        }
        return result;
    }
    public ArrayList<Restaurant> searchByRestaurantScore(double start,double end)
    {
        ArrayList<Restaurant> result= new ArrayList<Restaurant>();
        for(int i=0;i<restaurants.size();i++)
        {
            if( restaurants.get(i).getScore()>=start && restaurants.get(i).getScore()<=end )
            {
                result.add(restaurants.get(i));
            }
        }
        return result;
    }

    public ArrayList<Restaurant> searchByRestaurantPrice(String prc)
    {
        ArrayList<Restaurant> result= new ArrayList<Restaurant>();
        int count=0;
        for(int i=0;i<restaurants.size();i++)
        {
            if(prc.equals(restaurants.get(i).getPrice()) )
            {
                result.add(restaurants.get(i));
            }
        }
        return result;
    }
    public ArrayList<Restaurant> searchByRestaurantZip(int zip)
    {
        ArrayList<Restaurant> result= new ArrayList<Restaurant>();
        int count=0;
        for(int i=0;i<restaurants.size();i++)
        {
            if(zip == restaurants.get(i).getZip())
            {
                result.add(restaurants.get(i));
            }
        }
        return result;
    }


    public void DifferentCategoryWiseListofRestaurants()
    {
        ArrayList<Restaurant> temp= new ArrayList<Restaurant>();
        for(String cat: set){
            if(!cat.equals("")) {
                temp = restaurantSearchByCategory(cat);
                System.out.print(cat + ": ");
                System.out.print(temp.get(0).getName());
                for (Restaurant r : temp) {
                    if(r != temp.get(0)) {
                        System.out.print("," + r.getName());
                    }
                }
            }
            System.out.print("\n");
        }
        return;
    }
    public ArrayList<Food> foodSearchByFoodName(String foodName)
    {
        ArrayList<Food> result= new ArrayList<Food>();
        int count=0;
        String userInput= foodName.toUpperCase();
        for(int i=0;i<restaurants.size();i++)
        {
            for(int j=0;j<restaurants.get(i).foods.size();j++)
            {
                if(  ( (restaurants.get(i).foods.get(j).getName()).toUpperCase() )  .contains(userInput)   )
                {
                    result.add(restaurants.get(i).foods.get(j));
                }
            }
        }
        return result;
    }

    public ArrayList<Food>  ByNameInAGivenRestaurant(String foodname, String restaurantName)
    {
        ArrayList<Food> result= new ArrayList<Food>();
        int count=0;

        String userFood= foodname.toUpperCase();
        String userRestaurant= restaurantName.toUpperCase();

        for(int i=0;i<restaurants.size();i++)
        {
            if(((restaurants.get(i).getName() .toUpperCase()).contains(userRestaurant)  )      )
            {
                for(int j=0;j<restaurants.get(i).foods.size();j++)
                {
                    if(  ( (restaurants.get(i).foods.get(j).getName()).toUpperCase() )  .contains(userFood)   )
                    {
                        result.add(restaurants.get(i).foods.get(j));
                    }
                }
            }

        }

        return  result;
    }

    public ArrayList<Food> foodSearchByCategory(String cat)
    {
        ArrayList<Food> result= new ArrayList<Food>();
        String userInput= cat.toUpperCase();

        int count=0;
        for(int i=0;i<restaurants.size();i++) {
            for (int j = 0; j < restaurants.get(i).foods.size(); j++) {
                if( ( restaurants.get(i).foods.get(j).getCategory().toUpperCase() ) .contains(userInput)  )
                {
                    result.add(restaurants.get(i).foods.get(j));
                }

            }
        }
        return result;
    }

    public ArrayList<Food> ByCategoryInAGivenRestaurant(String categoryName, String restaurantName)
    {
        int count=0;
        ArrayList<Food> result= new ArrayList<Food>();
        String userCat= categoryName.toUpperCase();
        String userRestaurant= restaurantName.toUpperCase();

        for(int i=0;i<restaurants.size();i++) {

            if(  (    ( restaurants.get(i).getName() .toUpperCase() )   .contains(userRestaurant)  )      ){
                for (int j = 0; j < restaurants.get(i).foods.size(); j++) {
                    if(  ( (restaurants.get(i).foods.get(j).getCategory()).toUpperCase() )  .contains(userCat)   )
                    {
                        result.add(restaurants.get(i).foods.get(j));
                    }
                }
            }
        }

        return result;
    }


    public ArrayList<Food> searchByPriceRange(double prc1, double prc2)
    {
        ArrayList<Food> result= new ArrayList<Food>();
        int count= 0;
        for(int i=0;i<restaurants.size();i++) {
            for (int j = 0; j < restaurants.get(i).foods.size(); j++) {
                if(restaurants.get(i).foods.get(j).getPrice()>= prc1 && restaurants.get(i).foods.get(j).getPrice()<= prc2)
                {
                    result.add(restaurants.get(i).foods.get(j));
                }
            }
        }
        return result;
    }
    public ArrayList<Food> ByPriceRangeInAGivenRestaurant(double prc1, double prc2, String restaurantName)
    {
        ArrayList<Food> result= new ArrayList<Food>();
        int count =0;
        String userRestaurant= restaurantName.toUpperCase();

        for(int i=0;i<restaurants.size();i++) {
            if(  (    ( restaurants.get(i).getName() .toUpperCase() )   .contains(userRestaurant)  )      ){
                for (int j = 0; j < restaurants.get(i).foods.size(); j++) {
                    if(restaurants.get(i).foods.get(j).getPrice()>= prc1 && restaurants.get(i).foods.get(j).getPrice()<= prc2)
                    {
                        result.add(restaurants.get(i).foods.get(j));
                    }
                }
            }
        }

        return result;
    }

    public ArrayList<Food> displayCostliestFoodItem( String restaurantName)
    {
        ArrayList<Food> result= new ArrayList<Food>();
        double max=-100;

        String userRestaurant= restaurantName.toUpperCase();

        for(int i=0;i<restaurants.size();i++)
        {
            if(  (    ( restaurants.get(i).getName() .toUpperCase() )   .contains(userRestaurant)  )    )
            {
                for (int j = 0; j < restaurants.get(i).foods.size(); j++) {
                    if(restaurants.get(i).foods.get(j).getPrice()>max)
                    {
                        max=restaurants.get(i).foods.get(j).getPrice();
                    }
                }
            }
        }

        for(int i=0;i<restaurants.size();i++)
        {
            if(  (    ( restaurants.get(i).getName() .toUpperCase() )   .contains(userRestaurant)  )      ){
                for (int j = 0; j < restaurants.get(i).foods.size(); j++) {
                    if(restaurants.get(i).foods.get(j).getPrice() == max)
                    {
                        result.add(restaurants.get(i).foods.get(j));
                    }
                }
            }
        }

        return result;
    }

    public void displayRestaurantWithTotalFoodItem()
    {
        for(int i=0;i<restaurants.size();i++)
        {
            System.out.println(restaurants.get(i).getName()+" "+ restaurants.get(i).foods.size());
        }
    }

    public boolean searchById(int _id)
    {
        for(int i=0;i<restaurants.size();i++)
        {
            if(restaurants.get(i).getId() == _id)
            {
                return true;
            }

        }
        return false;
    }
    public boolean addRestaurant(Restaurant obj)
    {
        String userRestaurant= obj.getName().toUpperCase();
        int inputId= obj.getId();
        if(searchById(inputId))
        {
            return false;
        }
        else{
            for(int i=0;i<restaurants.size();i++) {
                if (  (restaurants.get(i).getName().toUpperCase()) .equals(userRestaurant)  )
                {
                    return false;
                }
            }
        }

        for(String s: obj.category)
        {
            set.add(s.toUpperCase());
        }

        restaurants.add(obj);
        return true;
    }

    public int addFood(String restaurantName,Food obj)
    {
        int count=0,count2=0;
        String userRestaurant= restaurantName.toUpperCase();

        for(int i=0;i<restaurants.size();i++) {
            if (  !(restaurants.get(i).getName().toUpperCase()) .equals(userRestaurant)  )
            {
                count2++;
            }
        }
        if(count2==restaurants.size()) return -1;

        String fName=obj.getName(). toUpperCase();
        String cName= obj.getCategory().toUpperCase();
            for(int i=0;i<restaurants.size();i++)
            {
                if(  (    ( restaurants.get(i).getName() .toUpperCase() )   .equals(userRestaurant)  )     )
                {
                    for (int j = 0; j < restaurants.get(i).foods.size(); j++) {
                        if(restaurants.get(i).foods.get(j).getName().toUpperCase().equals(fName)  &&  restaurants.get(i).foods.get(j).getCategory().toUpperCase().equals(cName))
                        {
                            count=1;
                            return 0;
                        }
                    }
                }
            }

            if(count!=1) {
                for (int i = 0; i < restaurants.size(); i++) {
                    if (((restaurants.get(i).getName().toUpperCase()).equals(userRestaurant))) {
                        restaurants.get(i).foods.add(obj);
                    }
                }
            }

        return 1 ;
    }

        public ArrayList<Restaurant> getRestaurants()
        {
            return restaurants;
        }
}
