package MyPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import util.SocketWrapper;

//import static MyPackage.RestaurantDatabase.restaurants;

public class Server {
    private static final String rInput = "restaurant.txt";
    private static final String rOutput = "out.txt";
    private static final String fInput = "menu.txt";
    private static final String fOutput = "out1.txt";

    private ServerSocket serverSocket;
    //RestaurantDatabase RestaurantManagement = new RestaurantDatabase();
    public HashMap<String, SocketWrapper> RestaurantClientMap; // HashMap of client's name and socket information
    public HashMap<String, SocketWrapper> CustomerClientMap; // HashMap of client's name and socket information
    static RestaurantDatabase RestaurantManagement = new RestaurantDatabase();
    static ArrayList<Restaurant> newRestaurant = new ArrayList<Restaurant>();
    static ArrayList<Food> newFood = new ArrayList<Food>();

    Server() throws IOException {
        CustomerClientMap = new HashMap<>();
        RestaurantClientMap = new HashMap<>();

        /*for(Restaurant x: restaurants)
        {
            clientMap.put(x.getName(),null);
        }*/

        new Thread(() -> {
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
                String[] array = line.split(",", -1);
                int id = Integer.parseInt(array[0]);
                String _name = array[1];
                double scr = Double.parseDouble(array[2]);
                String prc = array[3];
                int zip = Integer.parseInt(array[4]);
                ArrayList<String> temp = new ArrayList<String>();
                for (int i = 5; i < array.length; i++) {
                    temp.add(array[i]);
                    RestaurantManagement.set.add(array[i].toUpperCase());
                }

                Restaurant res = new Restaurant(id, _name, scr, prc, zip, temp);
                RestaurantManagement.restaurants.add(res);

            }
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("Exception in server class 6");
            }
        }).start();

        new Thread(() -> {
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
                String[] array = line.split(",", -1);
                int id = Integer.parseInt(array[0]);
                String _cat = array[1];
                String _name = array[2];
                double prc = Double.parseDouble(array[3]);

                Food food = new Food(id, _cat, _name, prc);

                for (int j = 0; j < RestaurantManagement.restaurants.size(); j++) {
                    if (id == RestaurantManagement.restaurants.get(j).getId()) {
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


        try {
            serverSocket = new ServerSocket(55555);
            System.out.println("Server is waiting");
            new WriteThreadServer(CustomerClientMap, "Server");
            new WriteThreadServer(RestaurantClientMap, "Server");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        SocketWrapper socketWrapper = null;
        try {
            socketWrapper = new SocketWrapper(clientSocket);
        } catch (IOException e) {
            System.out.println("hey");
        }
        String clientInfo = null;
        try {
            clientInfo = (String) socketWrapper.read();
        } catch (IOException e) {
            System.out.println("keno error");
        } catch (ClassNotFoundException e) {
            System.out.println("avoid error");
        }
        String[] str = clientInfo.split(",");
        int id = Integer.parseInt(str[1]);
        boolean flag = false;
        if (str[0].equalsIgnoreCase("RestaurantClient")) {
            for (int i = 0; i < RestaurantManagement.restaurants.size(); i++) {
                if (RestaurantManagement.restaurants.get(i).getId() == id) {
                    flag = true;
                    String clientName = RestaurantManagement.restaurants.get(i).getName();
                    //System.out.println(clientName);
                    String resId= Integer.toString(RestaurantManagement.restaurants.get(i).getId());
                    RestaurantClientMap.put(resId, socketWrapper);
                    System.out.println("Server has accepted a restaurant client.");
                    String msg = " Login request accepted!";
                    try {
                        socketWrapper.write((Object) msg);
                    } catch (IOException e) {
                        System.out.println("error dio na pls");
                    }
                    socketWrapper.write((Object) "Here is your menu:\n");

                    ArrayList<Food> GetFood = new ArrayList<>();
                    GetFood = RestaurantManagement.restaurants.get(i).getFood();
                    for (int k = 0; k < GetFood.size(); k++) {
                        socketWrapper.write((Object) GetFood.get(k).getName());
                    }
                }
            }
            if (flag == false) {
                String msg = " Login request failed!Please enter correct id.";
                try {
                    socketWrapper.write((Object) msg);
                } catch (IOException e) {
                    System.out.println("pls bollam to");
                }
            }
        }
        else if (str[0].equalsIgnoreCase("CustomerClient"))
        {
            System.out.println("Server has accepted a customer client.");
            CustomerClientMap.put(str[1], socketWrapper);
            String msg = "Login Successful!Welcome to Foodies!";
            socketWrapper.write((Object) msg);
            //Object o= RestaurantManagement.restaurants;
            socketWrapper.write(RestaurantManagement);
            //Object obj= RestaurantManagement.
        }
        new ReadThreadServer(socketWrapper,RestaurantClientMap);
        /*new Thread(()->
        {
            try {
                while (true) {
                    Object o=socketWrapper.read();
                    if( o instanceof MyOrder)
                    {
                        System.out.println("Customer has placed a order.");

                        MyOrder order= (MyOrder) o;
                        String resName= order.getResName();
                    }
                    else {

                        String s = (String) socketWrapper.read();
                        String[] str = s.split(",");
                        System.out.println(s);
                    }
                }
            } catch (Exception e) {
                System.out.println("hola");
            } finally {
                try {
                    socketWrapper.closeConnection();
                } catch (IOException e) {
                    System.out.println("henlo");
                }
            }
        }
        }).start();*/

    }

    public static void main(String args[]) {
        //System.out.println("Welcome to Restaurant Database Management System!");
        try {
            new Server();
        } catch (Exception e) {
            System.out.println("Exception in server class 1");
        }


    }
}
