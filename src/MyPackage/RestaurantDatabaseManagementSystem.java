package MyPackage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import MyPackage.Restaurant;
import MyPackage.RestaurantDatabase;


public class RestaurantDatabaseManagementSystem {
    private static final String rInput = "restaurant.txt";
    private static final String rOutput = "out.txt";
    private static final String fInput = "menu.txt";
    private static final String fOutput = "out1.txt";


    public static void main(String[] args) throws Exception {

        RestaurantDatabase RestaurantManagement = new RestaurantDatabase();
        ArrayList<Restaurant> newRestaurant=new ArrayList<Restaurant>();
        ArrayList<Food> newFood=new ArrayList<Food>();

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
                RestaurantManagement.set.add(array[i].toUpperCase());
            }

            Restaurant res= new Restaurant(id,_name,scr,prc,zip,temp);
            RestaurantManagement.restaurants.add(res);

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
            for(int j=0;j<RestaurantManagement.restaurants.size();j++)
            {
                if(id== RestaurantManagement.restaurants.get(j).getId())
                {
                        String resName= RestaurantManagement.restaurants.get(j).getName();
                        RestaurantManagement.restaurants.get(j).foods.add(food);
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

        System.out.println("Welcome to the Restaurant Database System");
        Scanner scanner = new Scanner(System.in);
        int option = 0, subOption = 0;
        while (true) {
            System.out.println("Main Menu:\n" +
                    "1) Search Restaurants\n" +
                    "2) Search Food Items\n" +
                    "3) Add Restaurant\n" +
                    "4) Add Food Item to the Menu\n" +
                    "5) Exit System");


            System.out.println("Enter the option you want to execute:");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    while (true) {
                        System.out.println("Restaurant Searching Options:\n" +
                                "1) By Name\n" +
                                "2) By Score\n" +
                                "3) By Category\n" +
                                "4) By Price\n" +
                                "5) By Zip Code\n" +
                                "6) Different Category Wise List of Restaurants\n" +
                                "7) Back to Main Menu");


                        System.out.println("Enter the sub-option you want to execute: ");
                        subOption = scanner.nextInt();
                        scanner.nextLine();

                        if (subOption == 1) {
                            System.out.println("Enter the restaurant name: ");
                            String str = scanner.nextLine();
                            
                            ArrayList<Restaurant> output = new ArrayList<Restaurant>();
                            output = RestaurantManagement.searchByRestaurantName(str);
                            if (output.isEmpty()) {
                                System.out.println("No such restaurant with this name.");
                                System.out.println();
                            } else {
                                System.out.println("Details of the restaurants that match with your search:");
                                System.out.println();
                                for(Restaurant r:output) {

                                    r.displayDetails();
                                }
                            }
                        }
                        else if (subOption == 2) {
                            System.out.println("Enter two numbers as the score range: ");
                            double s = scanner.nextDouble();
                            scanner.nextLine();
                            double e = scanner.nextDouble();
                            scanner.nextLine();

                            ArrayList<Restaurant> output = new ArrayList<Restaurant>();
                            output = RestaurantManagement.searchByRestaurantScore(s, e);

                            if (output.isEmpty()) {
                                System.out.println("No such restaurant with this score range.");
                                System.out.println();
                            } else {
                                System.out.println("Details of the restaurants that match with your search:");
                                System.out.println();
                                for (int i = 0; i < output.size(); i++) {
                                    output.get(i).displayDetails();
                                    System.out.println();
                                }
                            }
                        }
                        else if (subOption == 3) {
                            System.out.println("Enter a category:");
                            String s = scanner.nextLine();

                            ArrayList<Restaurant> output = new ArrayList<Restaurant>();
                            output = RestaurantManagement.restaurantSearchByCategory(s);

                            if (output.isEmpty()) {
                                System.out.println("No such restaurant with this category.");
                                System.out.println();
                            } else {
                                System.out.println("Details of the restaurants that match with your search:");
                                System.out.println();
                                for (int i = 0; i < output.size(); i++) {
                                    output.get(i).displayDetails();
                                    System.out.println();
                                }
                            }
                        }
                        else if (subOption == 4) {
                            System.out.println("Enter a price:");
                            String s = scanner.nextLine();

                            ArrayList<Restaurant> output = new ArrayList<Restaurant>();
                            output = RestaurantManagement.searchByRestaurantPrice(s);

                            if (output.isEmpty()) {
                                System.out.println("No such restaurant with this price.");
                                System.out.println();
                            } else {
                                System.out.println("Details of the restaurants that match with your search:");
                                System.out.println();
                                for (int i = 0; i < output.size(); i++) {
                                    output.get(i).displayDetails();
                                    System.out.println();
                                }
                            }
                        }
                        else if (subOption == 5) {
                            System.out.println("Enter a zip code:");
                            int x = scanner.nextInt();
                            scanner.nextLine();

                            ArrayList<Restaurant> output = new ArrayList<Restaurant>();
                            output = RestaurantManagement.searchByRestaurantZip(x);

                            if (output.isEmpty()) {
                                System.out.println("No such restaurant with this zip code.");
                                System.out.println();
                            } else {
                                System.out.println("Details of the restaurants that match with your search:");
                                System.out.println();
                                for (int i = 0; i < output.size(); i++) {
                                    output.get(i).displayDetails();
                                    System.out.println();
                                }
                            }
                        } else if (subOption == 6) {
                            RestaurantManagement.DifferentCategoryWiseListofRestaurants();
                        }
                        else if (subOption == 7) {
                            break;
                        }
                    }
                        break;

                case 2:
                    while(true)
                    {
                        System.out.println("Food Item Searching Options:\n" +
                                "1) By Name\n" +
                                "2) By Name in a Given Restaurant\n" +
                                "3) By Category\n" +
                                "4) By Category in a Given Restaurant \n" +
                                "5) By Price Range\n" +
                                "6) By Price Range in a Given Restaurant\n" +
                                "7) Costliest Food Item(s) on the Menu in a Given Restaurant\n" +
                                "8) List of Restaurants and Total Food Item on the Menu\n" +
                                "9) Back to Main Menu");

                        System.out.println("Enter the sub-option you want to execute: ");
                        subOption = scanner.nextInt();
                        scanner.nextLine();
                        
                        if(subOption==1)
                        {
                            System.out.println("Enter the food name:");
                            String str=scanner.nextLine();
                            
                            ArrayList<Food> output = new ArrayList<Food>();
                            output = RestaurantManagement.foodSearchByFoodName(str);
                            
                            if (output.isEmpty()) {
                                System.out.println("No such food item with this name.");
                                System.out.println();
                            } else {
                                System.out.println("Details of the food items that match with your search:");
                                System.out.println();
                                for (int i = 0; i < output.size(); i++) {
                                    output.get(i).displayDetails();
                                    System.out.println();
                                }
                            }
                        }

                        else if(subOption==2)
                        {
                            System.out.println("Enter the food name:");
                            String f=scanner.nextLine();
                            System.out.println("Enter the restaurant name:");
                            String r=scanner.nextLine();

                            ArrayList<Food> output = new ArrayList<Food>();
                            output = RestaurantManagement.ByNameInAGivenRestaurant(f,r);

                            if (output.isEmpty()) {
                                System.out.println("No such food item with this name on the menu of this restaurant.");
                                System.out.println();
                            } else {
                                System.out.println("Details of the food items that match with your search:");
                                System.out.println();
                                for (int i = 0; i < output.size(); i++) {
                                    output.get(i).displayDetails();
                                    System.out.println();
                                }
                            }
                        }

                        else if(subOption==3)
                        {
                            System.out.println("Enter a food item category:");
                            String str=scanner.nextLine();


                            ArrayList<Food> output = new ArrayList<Food>();
                            output = RestaurantManagement.foodSearchByCategory(str);

                            if (output.isEmpty()) {
                                System.out.println("No such food item with this category.");
                                System.out.println();
                            } else {
                                System.out.println("Details of the food items that match with your search:");
                                System.out.println();
                                for (int i = 0; i < output.size(); i++) {
                                    output.get(i).displayDetails();
                                    System.out.println();
                                }
                            }
                        }

                        else if(subOption==4)
                        {
                            System.out.println("Enter the food item category:");
                            String cat=scanner.nextLine();
                            System.out.println("Enter the restaurant name:");
                            String r=scanner.nextLine();

                            ArrayList<Food> output = new ArrayList<Food>();
                            output = RestaurantManagement.ByCategoryInAGivenRestaurant(cat,r);

                            if (output.isEmpty()) {
                                System.out.println("No such food item with this category on the menu of this restaurant.");
                                System.out.println();
                            } else {
                                System.out.println("Details of the food items that match with your search:");
                                System.out.println();
                                for (int i = 0; i < output.size(); i++) {
                                    output.get(i).displayDetails();
                                    System.out.println();
                                }
                            }
                        }

                        else if(subOption==5)
                        {
                            System.out.println("Enter two numbers as price range:");
                            double s=scanner.nextDouble();
                            scanner.nextLine();
                            double e=scanner.nextDouble();
                            scanner.nextLine();

                            ArrayList<Food> output = new ArrayList<Food>();
                            output = RestaurantManagement.searchByPriceRange(s,e);

                            if (output.isEmpty()) {
                                System.out.println("No such food item with this price range.");
                                System.out.println();
                            } else {
                                System.out.println("Details of the food items that match with your search:");
                                System.out.println();
                                for (int i = 0; i < output.size(); i++) {
                                    output.get(i).displayDetails();
                                    System.out.println();
                                }
                            }
                        }

                        else if(subOption==6)
                        {
                            System.out.println("Enter two numbers as price range:");
                            double s=scanner.nextDouble();
                            scanner.nextLine();
                            double e=scanner.nextDouble();
                            scanner.nextLine();
                            System.out.println("Enter the restaurant name:");
                            String str=scanner.nextLine();

                            ArrayList<Food> output = new ArrayList<Food>();
                            output = RestaurantManagement.ByPriceRangeInAGivenRestaurant(s,e,str);

                            if (output.isEmpty()) {
                                System.out.println("No such food item with this price range on the menu of this restaurant.");
                                System.out.println();
                            } else {
                                System.out.println("Details of the food items that match with your search:");
                                System.out.println();
                                for (int i = 0; i < output.size(); i++) {
                                    output.get(i).displayDetails();
                                    System.out.println();
                                }
                            }
                        }

                        else if(subOption==7)
                        {
                            System.out.println("Enter the restaurant name:");
                            String str=scanner.nextLine();

                            ArrayList<Food> output = new ArrayList<Food>();
                            output = RestaurantManagement.displayCostliestFoodItem(str);

                            if(output.size()==0)
                            {
                                System.out.println("No such restaurant with this name.");
                                System.out.println();
                            }
                            else {
                                System.out.println("Details of the food items that match with your search:");
                                System.out.println();
                                for (int i = 0; i < output.size(); i++) {
                                    output.get(i).displayDetails();
                                    System.out.println();
                                }
                            }
                        }

                        else if(subOption ==8)
                        {
                            RestaurantManagement.displayRestaurantWithTotalFoodItem();
                        }

                        else if (subOption==9) {
                            break;
                        }
                    }

                    break;
                case 3:
                    ArrayList<String> cat=new ArrayList<String>();
                    System.out.println("Enter the restaurant id:");
                    int id= scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter the restaurant name:");
                    String _name= scanner.nextLine();
                    System.out.println("Enter the score of that restaurant: ");
                    double scr=scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Enter the price in dollar: ");
                    String prc= scanner.nextLine();
                    System.out.println("Enter the zip code: ");
                    int zip= scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter at least one category(You can give at most three categories): ");
                    String cat1= scanner.nextLine();
                    cat.add(cat1);

                    System.out.println("Do you want to input more categories? Press 1 for yes and 2 for no");
                    int opn= scanner.nextInt();
                    scanner.nextLine();
                    if(opn==1)
                    {
                        System.out.println("How many category would you like to give? Press 1 for one and 2 for two");
                        int opn2= scanner.nextInt();
                        scanner.nextLine();
                        if(opn2==1) {
                            String cat2 = scanner.nextLine();
                            cat.add(cat1);
                        }else if(opn2==2) {
                            String cat2 = scanner.nextLine();
                            cat.add(cat2);
                            String cat3 = scanner.nextLine();
                            cat.add(cat3);
                        }
                    }


                    Restaurant restaurant= new Restaurant(id,_name,scr,prc,zip,cat);
                    boolean k= RestaurantManagement.addRestaurant(restaurant);
                    if(!k){
                        System.out.println("Restaurant already exists.");
                    }
                    else {
                        System.out.println("Successfully added!");
                        newRestaurant.add(restaurant);
                    }

                    break;

                case 4:
                    System.out.println("Enter the name of the restaurant: ");
                    String rName= scanner.nextLine();
                    System.out.println("Enter the name of the food: ");
                    String fName= scanner.nextLine();
                    System.out.println("Enter the price of the food: ");
                    double _prc=scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Enter the category: ");
                    String Cat=scanner.nextLine();

                    ArrayList<Restaurant> temp = new ArrayList<Restaurant>();
                    temp=RestaurantManagement.searchByRestaurantName(rName);
                    int resId=0;
                    for(Restaurant r: temp)
                    {
                         resId=r.getId();
                    }

                    Food food= new Food(resId,Cat,fName,_prc);
                    int x=RestaurantManagement.addFood(rName,food);
                    if(x==-1)
                        System.out.println("Restaurant does not exist.");

                    else if(x==0)
                        System.out.println("Food already exists.");
                        else if(x==1) {
                        System.out.println("Successfully added!");
                        newFood.add(food);
                    }
                    System.out.println();

                    break;

                case 5:
                    for(Restaurant r: newRestaurant) {

                        String text1 = r.getId() + "," + r.getName()+ "," + r.getScore() + "," + r.getPrice() + "," + r.getZip();
                        for (String str : r.getCategory()) {
                            if (r.getCategory().size() < 3) {
                               text1 = text1 + "," + str + ",";
                            } else {
                                text1 = text1 + "," + str;
                            }
                        }

                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rInput, true))) {
                            bw.write(System.lineSeparator());
                            bw.write(text1);
                            //bw.newLine();
                        }
                    }

                    for(Food f: newFood) {
                        String text2 = f.getRestaurantId()+","+f.getCategory()+","+f.getName()+","+f.getPrice();
                        try (BufferedWriter bw2 = new BufferedWriter(new FileWriter(fInput, true))) {
                            bw2.write(System.lineSeparator());
                            bw2.write(text2);
                            //bw2.newLine();
                        }
                    }

                    return;
            }
        }
    }
}
