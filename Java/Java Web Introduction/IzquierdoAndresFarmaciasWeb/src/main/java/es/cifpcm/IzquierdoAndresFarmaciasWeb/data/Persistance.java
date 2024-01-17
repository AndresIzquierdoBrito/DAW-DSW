package es.cifpcm.IzquierdoAndresFarmaciasWeb.data;

import es.cifpcm.IzquierdoAndresFarmaciasWeb.models.Farmacia;

import java.util.ArrayList;

public interface Persistance {
    void openJson();

    void closeJson();

    ArrayList<Farmacia> findClosestFarmacias(String name);

    ArrayList<Farmacia> list();
}
