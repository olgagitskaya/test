package controller;

import bean.RentUnit;
import service.Shop;
import bean.SportEquipment;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by user on 26.01.2017.
 */
public class Controller
{
    private Shop shop;
    public Controller()throws Exception
    {
        this.shop = new Shop();
     }

    public String executeCommand(String command){
        if(command.equals("exit"))
        {
            return "exit";
        }
        else if(command.equals("goods"))
        {
            String result="";
            Map sportEquipmentList = shop.getListOfSportEquipment();
            for (Object key : sportEquipmentList.keySet())
            {
                // had problems with type conversion as Map key had to be of bean.SportEquipment type
                int numberOfUnits = (Integer)sportEquipmentList.get(key);
                SportEquipment se = (SportEquipment)key;
                result += se.toString()+ ", Number of units: " + numberOfUnits +"\n";
            }
            return result;
        }
        else if(command.startsWith("search "))
        {
            String[] parts = command.split(" ");
            String title = parts[1];
            Map.Entry<SportEquipment, Integer> goodsEntry = shop.searchSportEquipment(title);
            if(goodsEntry != null)
            {
                return goodsEntry.getKey().toString()+ ", Number of units: " + goodsEntry.getValue();
            }
            else
            {
                return "No such equipment available.";
            }
        }
        else if(command.startsWith("rent "))
        {
            String[] parts = command.split(" ");
            if(parts.length > 4)
            {
                return "You cannot rent more than 3 items at once.";
            }
            ArrayList<String> titles = new ArrayList<String>();
            for(int i=1; i<parts.length;i++)
            {
                String title = parts[i];
                titles.add(title);
            }
            RentUnit rentUnit = shop.createRent(titles);
            if (rentUnit == null)
            {
                return "No equipment was ordered.";
            }
            else
            {
                return rentUnit.toString();
            }

        }
        else if(command.equals("rentlist"))
        {
            ArrayList<RentUnit> rentUnitsList = shop.getListOfRentUnits();
            for (RentUnit rentUnit: rentUnitsList)
            {
                return rentUnit.toString();
            }

        }
        return "No such command.";
    }


}
