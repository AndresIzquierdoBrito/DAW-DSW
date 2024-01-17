package es.cifpcm.data;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import es.cifpcm.model.Farmacia;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class PersistanceImpl implements Persistance {

    final String FILESTORE_PATH =  System.getProperty("java.io.tmpdir") + "IzquierdoAndres_farmacias.json";

    static ArrayList<Farmacia> memStore = new ArrayList<>();

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void openJson() {
        File file = new File(FILESTORE_PATH);
        if (!file.exists()) {
            System.out.println("No se encontró el archivo JSON. Creando un archivo nuevo con farmacias predefinidas...");
            createAndFillJson();
        }

        try (FileReader fr = new FileReader(file)) {
            Type type = new TypeToken<ArrayList<Farmacia>>() {}.getType();
            memStore = gson.fromJson(fr, type);
        } catch (JsonSyntaxException | JsonIOException e) {
            throw new RuntimeException("Fallo al parsear el archivo JSON en " + FILESTORE_PATH, e);
        } catch (IOException e) {
            throw new RuntimeException("Fallo al abrir el archivo JSON en " + FILESTORE_PATH, e);
        }
    }

    private void createAndFillJson() {
        try (Writer writer = new FileWriter(FILESTORE_PATH)) {
            JsonArray jsonArray = new JsonArray();

            ArrayList<Farmacia>farmaciiasEjemplo = new ArrayList<>();

            Farmacia farmacia1 = new Farmacia("http://www.farmaciacampitosifara.es", "FARMACIA LOS CAMPITOS", "09:00,14:00,17:00,20:30", "922281323", 376481.901533f, 3151614.6634f);
            farmaciiasEjemplo.add(farmacia1);
            Farmacia farmacia2 = new Farmacia("http://www.farmaciatrivino.com", "FARMACIA TRIVIÑO", "08:30, , ,21:30", "922537179", 370786.589416f, 3146161.33643f);
            farmaciiasEjemplo.add(farmacia2);
            Farmacia farmacia3 = new Farmacia("https://farmaciabuenofelipe.es", "FARMACIA BUENO-FELIPE", "09:00, , ,21:00", "922629609", 370645.7078f, 3145768.15526f);
            farmaciiasEjemplo.add(farmacia3);
            Farmacia farmacia4 = new Farmacia("https://farmaciagonzalezarqueros.es", "FARMACIA GONZALEZ ARQUEROS", "08:30,14:00,16:15,21:00", "922213070", 377328.383805f, 3149220.56944f);
            farmaciiasEjemplo.add(farmacia4);
            Farmacia farmacia5 = new Farmacia("http://www.farmacia-monje.com", "FARMACIA MONJE", "09:00,15:00,16:00,21:00", "922221434", 376814.751654f, 3149083.43488f);
            farmaciiasEjemplo.add(farmacia5);

            for (Farmacia farmacia : farmaciiasEjemplo) {
                JsonObject jsonObject = gson.toJsonTree(farmacia).getAsJsonObject();
                jsonArray.add(jsonObject);
            }

            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            throw new RuntimeException("Fallo al crear y rellenar el archivo en " + FILESTORE_PATH, e);
        }
    }

    @Override
    public void closeJson() {
        try (FileWriter fw = new FileWriter(FILESTORE_PATH)) {
            gson.toJson(memStore, fw);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo JSON.");
            e.printStackTrace();
        }
    }

    @Override
    public void add(Farmacia farmacia) {
        if (memStore == null) {
            throw new IllegalStateException("memStore es null. Hubo un error en la ejecución del programa.");
        }
        memStore.add(farmacia);
    }

    @Override
    public void delete(Farmacia farmacia) {
        if (memStore == null) {
            throw new IllegalStateException("memStore es null. Hubo un error en la ejecución del programa.");
        }
        memStore.remove(farmacia);
    }

    @Override
    public ArrayList<Farmacia> list() {
        if (memStore == null) {
            throw new IllegalStateException("memStore es null. Hubo un error en la ejecución del programa.");
        }
        return memStore;
    }
}