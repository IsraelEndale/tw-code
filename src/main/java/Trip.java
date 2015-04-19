import java.util.ArrayList;
import java.util.List;

/**
 * Created by Israel on 2/04/2015.
 */
public class Trip implements Cloneable{

    List towns = new ArrayList<Town>();
    int distance;

    public Trip() {
    }

    public Trip(List towns) {
        this.towns = towns;
    }

    public List getTowns() {
        return towns;
    }

    public void setTowns(List towns) {
        this.towns = towns;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getStops() {

        return this.towns.size()-1;

    }

}
