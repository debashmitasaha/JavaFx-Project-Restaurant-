package MyPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import util.SocketWrapper;

import MyPackage.Restaurant;
import static MyPackage.Server.*;

public class CustomerClient implements Serializable {

    public static RestaurantDatabase restaurantManagement = new RestaurantDatabase();

    public static void main(String args[]) {

        String serverAddress = "127.0.0.1";
        int serverPort = 55555;
        try {
            System.out.print("Enter the type of the client, name of the client: ");
            Scanner scanner = new Scanner(System.in);
            String clientName = scanner.nextLine();
            String[] str = clientName.split(",");
            String clientId = str[1];
            SocketWrapper socketWrapper = new SocketWrapper(serverAddress, serverPort);
            socketWrapper.write(clientName);
            String msg = (String) socketWrapper.read();
            Object o = socketWrapper.read();
            if (o instanceof RestaurantDatabase) {
                restaurantManagement = (RestaurantDatabase) o;
                ArrayList<Restaurant> resList = restaurantManagement.restaurants;
                System.out.println("Here is the list of all restaurants:");
                for (Restaurant r : resList) {
                    System.out.println(r.getName());
                }
                for (int k = 0; k < resList.size(); k++) {
                    for (int p = 0; p < resList.get(k).foods.size(); p++) {
                        System.out.println(resList.get(k).foods.get(p).getName());
                    }
                }
            }

            new CustomerClient();

            System.out.println("Welcome to the Restaurant Database System");
            /*System.out.println("Want to see the restaurant list or food list?");
            System.out.println("Press 1 for restaurant list , press 2 for food list or press 3 for searching options.");
            Scanner scanner = new Scanner(System.in);*/
            int showOption = 0, option = 0, subOption = 0;


            while (true) {
                System.out.println("Main Menu:\n" +
                        /*"1) Show Food List\n" +
                        "2) Show Restaurant list\n" +*/
                        "1) Search Restaurants\n" +
                        "2) Search Food Items\n" +
                        "3) Order Food\n");

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
                                String stri = scanner.nextLine();

                                ArrayList<Restaurant> output = new ArrayList<Restaurant>();
                                output = restaurantManagement.searchByRestaurantName(stri);
                                if (output.isEmpty()) {
                                    System.out.println("No such restaurant with this name.");
                                    System.out.println();
                                } else {
                                    System.out.println("Details of the restaurants that match with your search:");
                                    System.out.println();
                                    for (Restaurant r : output) {

                                        r.displayDetails();
                                    }
                                }
                            } else if (subOption == 2) {
                                System.out.println("Enter two numbers as the score range: ");
                                double s = scanner.nextDouble();
                                scanner.nextLine();
                                double e = scanner.nextDouble();
                                scanner.nextLine();

                                ArrayList<Restaurant> output = new ArrayList<Restaurant>();
                                output = restaurantManagement.searchByRestaurantScore(s, e);

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
                            } else if (subOption == 3) {
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
                            } else if (subOption == 4) {
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
                            } else if (subOption == 5) {
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
                            } else if (subOption == 7) {
                                break;
                            }
                        }
                        break;

                    case 2:
                        while (true) {
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

                            if (subOption == 1) {
                                System.out.println("Enter the food name:");
                                String stri = scanner.nextLine();

                                ArrayList<Food> output = new ArrayList<Food>();
                                output = restaurantManagement.foodSearchByFoodName(stri);

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
                            } else if (subOption == 2) {
                                System.out.println("Enter the food name:");
                                String f = scanner.nextLine();
                                System.out.println("Enter the restaurant name:");
                                String r = scanner.nextLine();

                                ArrayList<Food> output = new ArrayList<Food>();
                                output = restaurantManagement.ByNameInAGivenRestaurant(f, r);

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
                            } else if (subOption == 3) {
                                System.out.println("Enter a food item category:");
                                String stri = scanner.nextLine();


                                ArrayList<Food> output = new ArrayList<Food>();
                                output = restaurantManagement.foodSearchByCategory(stri);

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
                            } else if (subOption == 4) {
                                System.out.println("Enter the food item category:");
                                String cat = scanner.nextLine();
                                System.out.println("Enter the restaurant name:");
                                String r = scanner.nextLine();

                                ArrayList<Food> output = new ArrayList<Food>();
                                output = restaurantManagement.ByCategoryInAGivenRestaurant(cat, r);

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
                            } else if (subOption == 5) {
                                System.out.println("Enter two numbers as price range:");
                                double s = scanner.nextDouble();
                                scanner.nextLine();
                                double e = scanner.nextDouble();
                                scanner.nextLine();

                                ArrayList<Food> output = new ArrayList<Food>();
                                output = restaurantManagement.searchByPriceRange(s, e);

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
                            } else if (subOption == 6) {
                                System.out.println("Enter two numbers as price range:");
                                double s = scanner.nextDouble();
                                scanner.nextLine();
                                double e = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println("Enter the restaurant name:");
                                String stri = scanner.nextLine();

                                ArrayList<Food> output = new ArrayList<Food>();
                                output = restaurantManagement.ByPriceRangeInAGivenRestaurant(s, e, stri);

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
                            } else if (subOption == 7) {
                                System.out.println("Enter the restaurant name:");
                                String stri = scanner.nextLine();

                                ArrayList<Food> output = new ArrayList<Food>();
                                output = restaurantManagement.displayCostliestFoodItem(stri);

                                if (output.size() == 0) {
                                    System.out.println("No such restaurant with this name.");
                                    System.out.println();
                                } else {
                                    System.out.println("Details of the food items that match with your search:");
                                    System.out.println();
                                    for (int i = 0; i < output.size(); i++) {
                                        output.get(i).displayDetails();
                                        System.out.println();
                                    }
                                }
                            } else if (subOption == 8) {
                                restaurantManagement.displayRestaurantWithTotalFoodItem();
                            } else if (subOption == 9) {
                                break;
                            }
                        }

                        break;

                    case 3:

                        HashMap<String,Integer> map= new HashMap<>();
                        for(Restaurant r:restaurantManagement.restaurants)
                        {
                            for(Food f: r.foods)
                            {
                                map.put(f.getName(),0);
                            }
                        }

                        System.out.println("Enter the name of the restaurant you want to order from");
                        String resName = scanner.nextLine();
                        String resId="";
                        for(int p=0;p<restaurantManagement.restaurants.size();p++)
                        {
                            //System.out.println("error");
                            if(resName.equalsIgnoreCase(restaurantManagement.restaurants.get(p).getName()))
                            {
                               // System.out.println("error2");
                                resId=Integer.toString(restaurantManagement.restaurants.get(p).getId());
                            }
                        }
                        System.out.println("Enter the number of item you want to order:");
                        int num = scanner.nextInt();
                        scanner.nextLine();
                        ArrayList<HashMap<String,Integer>> orderlist = new ArrayList<>();

                        //HashMap<String,Integer> map= new HashMap<>();
                        System.out.println("Please place your order and number of food of corresponding item.");
                        int n=0;
                        String fName="";
                        for (int r = 0; r < num; r++)
                        {
                            int put=0;
                             fName = scanner.nextLine();
                            n=scanner.nextInt();
                            scanner.nextLine();
                            int x= map.get(fName);
                            map.put(fName,x+n);
                            orderlist.add(map);
                            MyOrder myOrder = new MyOrder(fName,resId, map);
                            socketWrapper.write(myOrder);
                        }

                        System.out.println("Your Order has been placed!");

                        break;


                }

            }

        } catch (Exception e) {
            System.out.println("Exception in customer client class");
        }


    }
}