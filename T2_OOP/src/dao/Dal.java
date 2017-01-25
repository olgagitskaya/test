package dao;

import bean.Category;
import bean.SportEquipment;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 26.01.2017.
 */
public class Dal
{
    private Map<String, Category> categories = new HashMap<String, Category>();
    private Map<SportEquipment, Integer> goods = new HashMap<SportEquipment, Integer>();

    public Dal()throws Exception
    {
        loadInitFile();
    }

    private void loadInitFile()throws Exception
    {
        String filePath = new File("src/init.xml").getAbsolutePath();
        File file = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        NodeList categoryList = document.getElementsByTagName("category");
        for(int i =0; i<categoryList.getLength();i++)
        {
            String categoryName = categoryList.item(i).getTextContent();
            //This class is used here only because it was indicated in the task as a property of the bean.SportEquipment Class.
            Category category = new Category(categoryName);
            this.categories.put(categoryName,category);
        }
        NodeList equipmentList = document.getElementsByTagName("equipment");
        for(int i =0; i<equipmentList.getLength();i++)
        {
            String categoryName = equipmentList.item(i).getAttributes().getNamedItem("category").getNodeValue();
            String title = equipmentList.item(i).getAttributes().getNamedItem("title").getNodeValue();
            int price = Integer.parseInt(equipmentList.item(i).getAttributes().getNamedItem("price").getNodeValue());
            int numberOfUnits = Integer.parseInt(equipmentList.item(i).getAttributes().getNamedItem("numberOfUnits").getNodeValue());
            SportEquipment unit = new SportEquipment(this.categories.get(categoryName),title, price);
            this.goods.put(unit, numberOfUnits);
        }
    }


    public Map<SportEquipment, Integer> getGoods()
    {
        return this.goods;
    }

    public Map<String, Category> getCategories()
    {
        return this.categories;
    }
}
