package es.cifpcm.data;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import es.cifpcm.model.Farmacia;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;

public class PersistanceImpl implements Persistance {

    //final String FILESTORE_PATH =  System.getProperty("java.io.tmpdir") + "IzquierdoAndres_farmacias.json";
    final String FILESTORE_PATH =  "src/main/resources/IzquierdoAndres_farmacias.json";

    static ArrayList<Farmacia> memStore = new ArrayList<>();

    Gson gson = new Gson();

    @Override
    public void openJson() {
        try (JsonReader reader = new JsonReader(new FileReader(FILESTORE_PATH))) {
            JsonElement jsonElement = JsonParser.parseReader(reader);

            if (jsonElement.isJsonArray()) {
                for (JsonElement element : jsonElement.getAsJsonArray()) {
                    if (element.isJsonObject()) {
                        Farmacia farmacia = gson.fromJson(element, Farmacia.class);
                        memStore.add(farmacia);
                    }
                }
            } else {
                System.out.println("Invalid JSON format");
            }
        } catch (FileNotFoundException e) {
            System.out.println("JSON file not found. Creating a new file with an example Farmacia.");
            createAndFillJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAndFillJson() {
        try (Writer writer = new FileWriter(FILESTORE_PATH)) {
            JsonArray jsonArray = new JsonArray();

            Farmacia exampleFarmacia = new Farmacia("www.hello.com", "23:23","922 12 23 34", 23.342f,24.123f);
            JsonObject jsonObject = gson.toJsonTree(exampleFarmacia).getAsJsonObject();
            jsonArray.add(jsonObject);

            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeJson() {

    }

    @Override
    public void add(Farmacia farmacia) {

    }

    @Override
    public void delete(Farmacia farmacia) {

    }

    @Override
    public ArrayList<Farmacia> list() {
        return null;
    }
}
