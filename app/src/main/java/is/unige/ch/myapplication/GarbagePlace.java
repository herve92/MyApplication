package is.unige.ch.myapplication;

import java.util.ArrayList;

/**
 * Created by Hervé
 *
 */
public class GarbagePlace {

    private String numero;
    private String address;
    private double latitude;
    private double longitude;
    private ArrayList<GARBAGE_TYPE> garbageSupported = new ArrayList<GARBAGE_TYPE>();

    GarbagePlace(String numero, String address, double latitude, double longitude, ArrayList<GARBAGE_TYPE> garbageSupported) {
        this.numero = numero;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.garbageSupported = garbageSupported;
    }
//  liste de valeurs possibles

    public enum GARBAGE_TYPE { PAPER, GLASS, PET };

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<GARBAGE_TYPE> getGarbageSupported() {
        return garbageSupported;
    }

    public void setGarbageSupported(ArrayList<GARBAGE_TYPE> garbageSupported) {
        this.garbageSupported = garbageSupported;
    }
}
