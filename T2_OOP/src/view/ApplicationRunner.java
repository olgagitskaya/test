package view;

import controller.Controller;

import java.util.Scanner;

/**
 * Created by Volha_Hitskaya on 1/20/2017.
 */
public class ApplicationRunner {

    /*
    Use commands:
    goods - to see the list of available sport equipment;
    search itemName - to search goods in the shop;
    rent itemName1 [itemName2] [itemName3] - to rent goods;
    rentlist - to see the list of rented sport equipment;
    exit - to exit the application.
    */

    public static void main(String[] args) throws Exception
    {
        Controller controller = new Controller();
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            String command = sc.nextLine();
            String commandResult = controller.executeCommand(command);
            if(commandResult.equals("exit"))
            {
                break;
            }
            System.out.println(commandResult);

        }
    }
}
