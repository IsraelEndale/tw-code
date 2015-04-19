import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;

/**
 * Created by Israel on 9/04/2015.
 */
public class Graph {


    private int size;
    private int[][] routes;
    private List<Town> towns = new ArrayList<Town>();
    private List<Trip> trips = new ArrayList<Trip>();

    public void addTown(String town) {
        towns.add(new Town(town));
    }

    public void addRoute(String from, String to, int distance) {
        size = towns.size();
        if (routes == null)
            routes = new int[size][size];
        //setting the distance between towns
        routes[towns.indexOf(new Town(from))][towns.indexOf(new Town(to))] = distance;
    }


    public List<Town> getAdjacents(Town town) {
        List<Town> adj = new ArrayList<Town>();

        for (int i = 0; i < size; i++) {
//            checking if the route already visited by referring to routesVisited matrix
            if (routes[towns.indexOf(town)][i] > 0) {
                adj.add(towns.get(i));
            }
        }
        return adj;
    }

    // if -1 is for either maxDistance or stopDistance then ignore maximum
    public void dfs(String from, String to, int[][] routesVisited, Town current, Stack<Town> path, int maxStop, int stops, int maxDistance, int distance) {


//        adding the current path to the trips
        if (towns.get(towns.indexOf(new Town(to))) == current) {
            addTrip(path);
        }

        if (current == null) {
            current = towns.get(towns.indexOf(new Town(from)));
            path.add(towns.get(towns.indexOf(new Town(from))));
        }

//        setting the stop/distance based on the level of depth
        for (Town neighbour : getAdjacents(towns.get(towns.indexOf(current)))) {
            path.add(neighbour);
            stops++;
            distance += getDistance(current, neighbour);
            if ((maxStop != -1 && stops <= maxStop) || (maxDistance != -1 && distance <= maxDistance)) {
                dfs(from, to, routesVisited, neighbour, path, maxStop, stops, maxDistance, distance);
            }
            path.pop();
            stops--;
            distance -= getDistance(current, neighbour);
        }
    }

    public void dfs(String from, String to, int[][] routesVisited, Town current, Stack<Town> path) {


//      adding the current path to the trips
        if (towns.get(towns.indexOf(new Town(to))) == current) {
            addTrip(path);
        }

        if (current == null) {
            current = towns.get(towns.indexOf(new Town(from)));
            path.add(towns.get(towns.indexOf(new Town(from))));
        }

        for (Town neighbour : getAdjacents(towns.get(towns.indexOf(current)))) {
            if (routesVisited[towns.indexOf(current)][towns.indexOf(neighbour)] != 1) {
                routesVisited[towns.indexOf(current)][towns.indexOf(neighbour)] = 1;
                path.add(neighbour);
                dfs(from, to, routesVisited, neighbour, path);
                routesVisited[towns.indexOf(current)][towns.indexOf(neighbour)] = 0;
                path.pop();
            }
        }
    }

    public void addTrip(Stack<Town> stack) {
        Trip trip = new Trip();

        Enumeration<Town> elements = stack.elements();
        while (elements.hasMoreElements()) {
            Town town = elements.nextElement();
            trip.towns.add(town);
        }
        trip.distance += getDistance(stack);
        trips.add(trip);
    }

    //looping routes to get the distance of the depth. Can be done also with breadth search
    public int getDistance(String... names) throws PathNotExistedException{
        int distance = 0;
        for (int i = 0; i < names.length - 1; i++) {
            int d = routes[towns.indexOf(new Town(names[i]))][towns.indexOf(new Town(names[i + 1]))];
            if (d > 0) distance += d;
            else throw new PathNotExistedException("NO SUCH ROUTE");
        }
        return distance;
    }

    //get the distance between two towns
    public int getDistance(Town a, Town b) {
        return routes[towns.indexOf(a)][towns.indexOf(b)];
    }

    //    computes all the towns distance on the stack for each depth of field
    public int getDistance(Stack<Town> stack) {
        int distance = 0;
        for (int i = 0; i < stack.size() - 1; i++) {
            distance += getDistance(stack.elementAt(i), stack.elementAt(i + 1));
        }
        return distance;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
