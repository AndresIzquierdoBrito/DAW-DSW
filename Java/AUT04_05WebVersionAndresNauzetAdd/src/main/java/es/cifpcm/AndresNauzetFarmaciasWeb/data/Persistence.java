package es.cifpcm.AndresNauzetFarmaciasWeb.data;


import es.cifpcm.AndresNauzetFarmaciasWeb.models.Farmacia;

import java.util.List;

public interface Persistence {

    void openJSON();
    void closeJSON();
    void add(Farmacia farmacia);
    Farmacia searchName(String nombreFarmacia);
    List<Farmacia> showNear(Farmacia farmacia);
    List<Farmacia> list();
}
