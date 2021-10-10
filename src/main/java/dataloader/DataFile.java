package dataloader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataFile {
    public static final String jsonFilePath = "C:\\Users\\Zahari Mikov\\IdeaProjects\\Car_Maintenance_backup\\";
    private final String jsonFileName = "car_data.json";

    private final List<String> properties;
    private final JSONParser parser;
    private JSONObject jsonFile;
    protected File data;

    public DataFile() {
        properties = new ArrayList<>();
        parser = new JSONParser();
        try {
            Object objectFile = parser.parse(new FileReader(jsonFilePath + jsonFileName));
            jsonFile = (JSONObject) objectFile;
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<String> loadBrand() {
        properties.clear();
        JSONObject jsonBrandObject = (JSONObject) jsonFile.get("Brands");
        properties.addAll(jsonBrandObject.keySet());
        return properties;
    }

    public List<String> loadYear() {
        properties.clear();
        JSONArray jsonYear = (JSONArray) jsonFile.get("Year");
        properties.addAll(jsonYear);
        return properties;
    }

    public List<String> loadModel(String brand) {
        properties.clear();

        JSONObject jsonBrandObject = (JSONObject) jsonFile.get("Brands");

        JSONObject jsonModelsObject = (JSONObject) jsonBrandObject.get(brand);
        if (jsonModelsObject != null) {
            List<String> models = new ArrayList<String>(jsonModelsObject.keySet());
            properties.addAll(models);
        }
        return properties;
    }

    public List<String> loadModification(String brand, String model) {
        properties.clear();

        JSONObject jsonBrandObject = (JSONObject) jsonFile.get("Brands");

        JSONObject jsonModelsObject = (JSONObject) jsonBrandObject.get(brand);
        if (jsonModelsObject != null) {
            JSONObject jsonModelObject = (JSONObject) jsonModelsObject.get(model);
            if (jsonModelObject != null) {
                JSONArray jsonModificationObject = (JSONArray) jsonModelObject.get("Modification");
                properties.addAll(jsonModificationObject);
            }
        }
        return properties;
    }
}
