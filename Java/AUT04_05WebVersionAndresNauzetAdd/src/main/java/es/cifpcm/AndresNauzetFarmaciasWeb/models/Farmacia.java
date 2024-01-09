package es.cifpcm.AndresNauzetFarmaciasWeb.models;

public class Farmacia {

    String WEB;
    String NOMBRE;
    String LUNES;
    String MARTES;
    String MIERCOLES;
    String JUEVES;
    String VIERNES;
    String SABADO;
    String DOMINGO;
    String TELEFONO;
    String DIRECCION;
    String DISTRITO;
    String BARRIO;
    String COD_POSTAL;
    float UTM_X;
    float UTM_Y;

    public Farmacia(String WEB, String NOMBRE, String LUNES, String MARTES, String MIERCOLES, String JUEVES, String VIERNES, String SABADO, String DOMINGO, String TELEFONO, String DIRECCION, String COD_POSTAL, String DISTRITO, String BARRIO, float UTM_X, float UTM_Y) {
        this.WEB = WEB;
        this.NOMBRE = NOMBRE;
        this.LUNES = LUNES;
        this.MARTES = MARTES;
        this.MIERCOLES = MIERCOLES;
        this.JUEVES = JUEVES;
        this.VIERNES = VIERNES;
        this.SABADO = SABADO;
        this.DOMINGO = DOMINGO;
        this.TELEFONO = TELEFONO;
        this.DIRECCION = DIRECCION;
        this.COD_POSTAL = COD_POSTAL;
        this.DISTRITO = DISTRITO;
        this.BARRIO = BARRIO;
        this.UTM_X = UTM_X;
        this.UTM_Y = UTM_Y;
    }

    public Farmacia() {

    }

    public String getWEB() {
        return WEB;
    }

    public void setWEB(String WEB) {
        this.WEB = WEB;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getLUNES() {
        return LUNES;
    }

    public void setLUNES(String LUNES) {
        this.LUNES = LUNES;
    }

    public String getMARTES() {
        return MARTES;
    }

    public void setMARTES(String MARTES) {
        this.MARTES = MARTES;
    }

    public String getMIERCOLES() {
        return MIERCOLES;
    }

    public void setMIERCOLES(String MIERCOLES) {
        this.MIERCOLES = MIERCOLES;
    }

    public String getJUEVES() {
        return JUEVES;
    }

    public void setJUEVES(String JUEVES) {
        this.JUEVES = JUEVES;
    }

    public String getVIERNES() {
        return VIERNES;
    }

    public void setVIERNES(String VIERNES) {
        this.VIERNES = VIERNES;
    }

    public String getSABADO() {
        return SABADO;
    }

    public void setSABADO(String SABADO) {
        this.SABADO = SABADO;
    }

    public String getDOMINGO() {
        return DOMINGO;
    }

    public void setDOMINGO(String DOMINGO) {
        this.DOMINGO = DOMINGO;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getDIRECCION() {
        return DIRECCION;
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }

    public String getCOD_POSTAL() {
        return COD_POSTAL;
    }

    public void setCOD_POSTAL(String COD_POSTAL) {
        this.COD_POSTAL = COD_POSTAL;
    }

    public String getDISTRITO() {
        return DISTRITO;
    }

    public void setDISTRITO(String DISTRITO) {
        this.DISTRITO = DISTRITO;
    }

    public String getBARRIO() {
        return BARRIO;
    }

    public void setBARRIO(String BARRIO) {
        this.BARRIO = BARRIO;
    }

    public float getUTM_X() {
        return UTM_X;
    }

    public void setUTM_X(float UTM_X) {
        this.UTM_X = UTM_X;
    }

    public float getUTM_Y() {
        return UTM_Y;
    }

    public void setUTM_Y(float UTM_Y) {
        this.UTM_Y = UTM_Y;
    }
}