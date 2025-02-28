package MyPackage;

import util.SocketWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import MyPackage.Restaurant;

public class RestaurantClient {

    public ArrayList<HashMap<String,Integer> > orderUpdate= new ArrayList<>();
    //public ArrayList<HashMap<String,Integer>> count= new ArrayList<>();

    public RestaurantClient(String serverAddress , int serverPort)
    {
        try {
            System.out.print("Enter the type of the client(RestaurantClient or CustomerClient),enter your id ");
            Scanner scanner = new Scanner(System.in);
            String clientInfo = scanner.nextLine();
            SocketWrapper socketWrapper = new SocketWrapper(serverAddress, serverPort);
            socketWrapper.write(clientInfo);

            new ReadThreadRestaurentClient(socketWrapper, orderUpdate);


            //new WriteThreadRestaurantClient(socketWrapper, clientInfo);
        } catch (Exception e) {
            System.out.println("Exception in restaurant client class");
        }
        //for(Food f: )
    }

    public static void main(String args[]) {
        String serverAddress = "127.0.0.1";
        int serverPort = 55555;
        new RestaurantClient(serverAddress, serverPort);
    }
}
