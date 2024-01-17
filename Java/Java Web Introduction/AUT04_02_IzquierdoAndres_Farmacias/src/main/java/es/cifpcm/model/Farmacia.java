package es.cifpcm.model;

public class Farmacia {

    String WEB;

    String NOMBRE;

    String LUNES;

    String TELEFONO;

    float UTM_X;

    float UTM_Y;

    public Farmacia(String WEB, String NOMBRE, String LUNES, String TELEFONO, float UTM_X, float UTM_Y) {
        this.WEB = WEB;
        this.NOMBRE = NOMBRE;
        this.LUNES = LUNES;
        this.TELEFONO = TELEFONO;
        this.UTM_X = UTM_X;
        this.UTM_Y = UTM_Y;
    }

    @Override
    public String toString() {
        return "Nombre: " + this.NOMBRE +
                "\nWeb: " + this.WEB +
                "\nTeléfono: " + this.TELEFONO +
                "\nHorario L :" + this.LUNES +
                "\nUTM X: " + this.UTM_X +
                "\nUTM Y: " + this.UTM_Y;
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

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
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