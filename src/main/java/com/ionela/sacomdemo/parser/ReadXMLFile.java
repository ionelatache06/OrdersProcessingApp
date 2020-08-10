
package com.ionela.sacomdemo.parser;

import com.ionela.sacomdemo.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class ReadXMLFile {

    static Logger logger = LoggerFactory.getLogger(ReadXMLFile.class);

    //Do the actual parsing
    public static void process(File xmlFilePath) {
      logger.info("In process method");
        try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFilePath);
                doc.getDocumentElement().normalize();


                //create a map where the key is represented by supplier's name and the values are represented by a list
                // of products
                HashMap<String, List<Product>> prodMap = new HashMap<>();

                //get a list of all the elements that have the order tag
                NodeList allOrders = doc.getElementsByTagName("order");

                //iterate through the allOrders list
                for (int i = 0; i < allOrders.getLength(); i++) {

                    //Obtain a reference to each order
                    Node order = allOrders.item(i);

                    if (order.getNodeType() == Node.ELEMENT_NODE) {

                        NodeList allProducts = doc.getElementsByTagName("product");

                        for (int tempProduct = 0; tempProduct < allProducts.getLength(); tempProduct++) {

                            //Obtain a reference to each product
                            Node product = allProducts.item(tempProduct);
                            if (product.getNodeType() == Node.ELEMENT_NODE) {

                                //check if current product belongs to current order
                                if (product.getParentNode().getAttributes().getNamedItem("ID")
                                        .equals(order.getAttributes().getNamedItem("ID"))) {

                                    Element prodElement = (Element) product;

                                    Product prod = new Product(
                                            prodElement.getElementsByTagName("description").item(0).getTextContent(),
                                            prodElement.getElementsByTagName("gtin").item(0).getTextContent(),
                                            Double.parseDouble(
                                                    prodElement.getElementsByTagName("price").item(0).getTextContent()),
                                            prodElement.getElementsByTagName("price").item(0).getAttributes().item(0)
                                                    .getTextContent(),
                                            product.getParentNode().getAttributes().getNamedItem("ID").getNodeValue()
                                                    .toString());
                                    logger.info("Product was created");
                                    //For each product, get the name of the supplier
                                    String supplierKey = prodElement.getElementsByTagName("supplier").item(0)
                                            .getTextContent();

                                    //If the map already contains the key, then we get the the associated list of products
                                    // and add prod to it. Otherwise, we create a new product list and then put in the map
                                    if (prodMap.containsKey(supplierKey)) {
                                        prodMap.get(supplierKey).add(prod);
                                        List<Product> listOfProducts = prodMap.get(supplierKey);

                                    } else {
                                        ArrayList<Product> newProductList = new ArrayList<Product>();
                                        newProductList.add(prod);

                                        prodMap.put(supplierKey, newProductList);
                                    }

                                }
                            }
                        }
                    }
                }

                new WriteXmlFile().writeFiles(xmlFilePath.getName(), prodMap);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
}
