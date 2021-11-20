/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package baseline;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.stage.FileChooser;
import models.Inventory;
import models.Item;
import models.ItemLight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    // extensions we support
    public static final FileChooser.ExtensionFilter TSV = new FileChooser.ExtensionFilter("TSV", "*.txt");
    public static final FileChooser.ExtensionFilter HTML = new FileChooser.ExtensionFilter("HTML", "*.html");
    public static final FileChooser.ExtensionFilter JSON = new FileChooser.ExtensionFilter("JSON", "*.json");

    public void saveInventoryToTSV(Inventory inventory, File file) throws IOException {
        StringBuilder fileBuilder = new StringBuilder();

        // add the header row
        fileBuilder.append("Serial Number\tName\tValue\n");

        // get the observable list
        // for each of the items, format a line with
        // A-XXX-XXX-XXX    Xbox    $1499.00
        for (Item item : inventory.getInventoryList()) {
            fileBuilder.append(item.toStringTSV());
        }
        Path p = file.toPath();

        try {
            Files.writeString(p, fileBuilder.toString());
        } catch (Exception e) {
            throw new IOException();
        }
    }

    public Inventory loadInventoryFromTSV(File file) throws IOException {
        // takes in the file
        // if invalid, throw exception

        Inventory inventory;
        try (Scanner input = new Scanner(file)) {
            inventory = new Inventory();
            input.useDelimiter("[\t\n]");

            // for the header...
            input.nextLine();

            // parses file, examining the items in the line
            while (input.hasNextLine()) {
                // create and build the inventory using these items
                String serial = input.nextLine();
                String[] parameters = serial.split("\t");

                inventory.addItem(new Item(parameters[1], parameters[0], parameters[2].replaceAll("[,$]", "")));
            }
        }

        // return the inventory
        return inventory;
    }

    public void saveInventoryToJSON(Inventory inventory, File file) throws IOException {
        // use GSON to save the file
        ArrayList<ItemLight> list = new ArrayList<>();

        for (Item i : inventory.getInventoryList()) {
            list.add(i.getAsItemLight());
        }

        // go to the location the user specified and save as JSON
        try (Writer writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(list, writer);
        } catch (JsonIOException e) {
            throw new IOException();
        }
    }

    public Inventory loadInventoryFromJSON(File file) throws InvalidParameterException {
        // use GSON to load the file
        Gson gson = new Gson();
        // go to the location the user specified and load as String
        String json = getInventoryFileAsString(file);

        Inventory inventory = new Inventory();

        ArrayList<ItemLight> list;

        // parse the json String
        try {
            list = gson.fromJson(json, new TypeToken<ArrayList<ItemLight>>(){}.getType());
        } catch (Exception e) {
            throw new InvalidParameterException();
        }

        for (ItemLight i : list) {
            // if invalid, throw exception
            inventory.addItem(new Item(i.getName(), i.getSerial(), i.getValue()));
        }

        return inventory;
    }

    private String getInventoryFileAsString(File file) throws InvalidParameterException {
        // return the text of the inventory file
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            return fileElement.toString();

        } catch (FileNotFoundException e) {
            throw new InvalidParameterException();
        }
    }

    public void saveInventoryToHTML(Inventory inventory, File file) throws IOException {
        // format as html with basic wrap
        StringBuilder fileBuilder = new StringBuilder();

        // open table header

        fileBuilder.append("<!DOCTYPE html\n\n>");
        fileBuilder.append("<html>\n");
        fileBuilder.append("<body>\n");

        fileBuilder.append("<table>\n");

        // get the observable list
        // for each of the items, format a table with
        // A-XXX-XXX-XXX    Xbox    $1499.00

        // table header row
        fileBuilder.append("<tr>\n<th>Serial Number</th>\n<th>Name</th>\n<th>Value</th>\n</tr>");

        // go item by item, adding them to a String for HTML table
        for (Item item : inventory.getInventoryList()) {
            fileBuilder.append(getTableRowFormatted(item));
        }


        // close table header
        fileBuilder.append("\n</table>\n");
        fileBuilder.append("</body>\n");
        fileBuilder.append("</html>\n");


        Path p = file.toPath();
        // go to location and save the HTML
        try {
            Files.writeString(p, fileBuilder.toString());
        } catch (Exception e) {
            throw new IOException();
        }

    }

    private String getTableRowFormatted(Item item) {
        String row = "<tr>\n";

        row += getTableDataFormatted(item.getSerial());
        row += getTableDataFormatted(item.getName());
        row += getTableDataFormatted(item.getValueFormatted());

        row += "</tr>\n";

        return row;
    }

    private String getTableDataFormatted(String data) {
        return "<td>\n" + data + "\n</td>\n";
    }

    public Inventory loadInventoryFromHTML(File file) throws InvalidParameterException {
        // go to the location the user specified and get the content as a string
        Inventory inventory = new Inventory();

        String content;

        try {
            content = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new InvalidParameterException();
        }

        Document doc = Jsoup.parse(content);

        // .next() will skip the header row
        Elements rows = doc.getElementsByTag("tr").next();


        // parse through the HTML table, adding items to an Inventory
        for (Element row : rows) {
            String serial = row.getElementsByTag("td").get(0).text();
            String name = row.getElementsByTag("td").get(1).text();
            String value = row.getElementsByTag("td").get(2).text();

            Item item = new Item(name, serial, value);
            inventory.addItem(item);
        }

        // return the inventory
        return inventory;
    }
}