package es.cifpcm.IzquierdoAndresFarmaciasWeb.data;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import es.cifpcm.IzquierdoAndresFarmaciasWeb.models.Farmacia;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class PersitanceImplFarm implements Persistance{

    final String FILESTORE_PATH =  System.getProperty("java.io.tmpdir") + "/IzquierdoAndres_farmacias.json";

    static ArrayList<Farmacia> memStore = new ArrayList<>();

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void openJson() {
        File file = new File(FILESTORE_PATH);
        if (!file.exists()) {
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

            Farmacia farmacia1 = new Farmacia("https://www.farmaciacampitosifara.es", "FARMACIA LOS CAMPITOS", "09:00,14:00,17:00,20:30", "09:00,14:00,17:00,20:30", "09:00,14:00,17:00,20:30","922281323", "09:00,14:00,17:00,20:30", "09:00,14:00,17:00,20:30", "09:00,14:00,17:00,20:30", "922537179", "Calle Ejemplo 1", "38001", "Distrito 1", "Barrio 1", 376481.901533f, 3151614.6634f);
            farmaciiasEjemplo.add(farmacia1);

            Farmacia farmacia2 = new Farmacia("https://www.farmaciaejemplo1.com", "FARMACIA EJEMPLO 1", "08:30,13:00,16:30,19:45", "08:30,13:00,16:30,19:45", "08:30,13:00,16:30,19:45", "922456789", "08:30,13:00,16:30,19:45", "08:30,13:00,16:30,19:45", "08:30,13:00,16:30,19:45", "922345678", "Calle Ejemplo 6", "38001", "Distrito 6", "Barrio 6", 376812.3456f, 3151622.6789f);
            farmaciiasEjemplo.add(farmacia2);

            Farmacia farmacia3 = new Farmacia("https://www.farmaciaotroejemplo.com", "FARMACIA OTRO EJEMPLO", "09:15,14:30,17:45,20:00", "09:15,14:30,17:45,20:00", "09:15,14:30,17:45,20:00", "922111222", "09:15,14:30,17:45,20:00", "09:15,14:30,17:45,20:00", "09:15,14:30,17:45,20:00", "922333444", "Calle Ejemplo 7", "38001", "Distrito 7", "Barrio 7", 376500.9876f, 3151750.4567f);
            farmaciiasEjemplo.add(farmacia3);

            Farmacia farmacia4 = new Farmacia("https://www.otrafarmaciaejemplo.com", "OTRA FARMACIA EJEMPLO", "08:00,12:30,15:45,19:30", "08:00,12:30,15:45,19:30", "08:00,12:30,15:45,19:30", "922555666", "08:00,12:30,15:45,19:30", "08:00,12:30,15:45,19:30", "08:00,12:30,15:45,19:30", "922777888", "Calle Ejemplo 8", "38001", "Distrito 8", "Barrio 8", 377000.1234f, 3149000.5678f);
            farmaciiasEjemplo.add(farmacia4);

            Farmacia farmacia5 = new Farmacia("https://www.ultimofarmaciaejemplo.com", "ÚLTIMO EJEMPLO FARMACIA", "09:30,14:15,18:00,21:15", "09:30,14:15,18:00,21:15", "09:30,14:15,18:00,21:15", "922999000", "09:30,14:15,18:00,21:15", "09:30,14:15,18:00,21:15", "09:30,14:15,18:00,21:15", "922888777", "Calle Ejemplo 9", "38001", "Distrito 9", "Barrio 9", 376800.9876f, 3151000.5678f);
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
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Farmacia> findClosestFarmacias(String name) {
        if (memStore == null) {
            throw new IllegalStateException("memStore es null. Hubo un error en la ejecución del programa.");
        }
        Farmacia targetFarmacia = memStore.stream()
                .filter(farmacia -> farmacia.getNOMBRE().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        if (targetFarmacia == null) {
            return new ArrayList<>();
        }

        return memStore.stream()
                .filter(farmacia -> Math.abs(farmacia.getUTM_X() - targetFarmacia.getUTM_X()) <= 1600 &&
                        Math.abs(farmacia.getUTM_Y() - targetFarmacia.getUTM_Y()) <= 16000)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Farmacia> list() {
         if (memStore == null) {
            throw new IllegalStateException("memStore es null. Hubo un error en la ejecución del programa.");
        }
        return memStore;
    }
}
