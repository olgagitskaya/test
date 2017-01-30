package service;

import bean.Category;
import bean.RentUnit;
import bean.SportEquipment;
import dao.Dal;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * Created by Volha_Hitskaya on 1/20/2017.
 */
public class Shop {
    private Map<String, Category> categories = new HashMap<String, Category>();
    private Map<SportEquipment, Integer> goods = new HashMap<SportEquipment, Integer>();
    private ArrayList<RentUnit> rentUnits = new ArrayList<RentUnit>();

    public Shop()throws Exception
    {
        initialize();
    }

    public Map<SportEquipment, Integer> getListOfSportEquipment()
    {
        return goods;

    }

    public Map.Entry<SportEquipment, Integer> searchSportEquipment(String title)
    {
        for (Map.Entry<SportEquipment, Integer> entry : this.goods.entrySet())
        {
            if (entry.getKey().getTitle().equals(title))
            {
                return (entry);
            }
        }
        return null;
    }

    public void initialize()throws Exception
    {
        Dal dal = Dal.getInstance();
        this.goods = dal.getGoods();
        this.categories = dal.getCategories();
    }

    public RentUnit createRent(ArrayList<String> titles)
    {
        ArrayList<SportEquipment> equipmentList = new ArrayList<SportEquipment>();
        String notOrdered = "";
        for(String title:titles)
        {
            Map.Entry<SportEquipment,Integer> searchResult = searchSportEquipment(title);
            if (searchResult != null)
            {
                int numberOfUnits = searchResult.getValue();
                if(numberOfUnits>0)
                {
                    searchResult.setValue(numberOfUnits-1);
                    equipmentList.add(searchResult.getKey());
                    continue;
                }
            }
            notOrdered += title + ",";
        }
        Date dateOfRent = new Date();
        RentUnit rentUnit = new RentUnit(equipmentList, dateOfRent);
        if (!notOrdered.isEmpty())
        {
            notOrdered = notOrdered.substring(0, notOrdered.length() - 1);
        }
        rentUnit.setEquipmentNotOrdered(notOrdered);
        if (equipmentList.size()>0)
        {
            rentUnits.add(rentUnit);
            return rentUnit;
        }
        return null;
    }

    public ArrayList<RentUnit>  getListOfRentUnits()
    {
        return this.rentUnits;
    }


}
