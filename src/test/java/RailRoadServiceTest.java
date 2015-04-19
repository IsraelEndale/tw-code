import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

/**
 * Created by Israel on 30/03/2015.
 */

//        1. The distance of the route A-B-C.
//        2. The distance of the route A-D.
//        3. The distance of the route A-D-C.
//        4. The distance of the route A-E-B-C-D.
//        5. The distance of the route A-E-D.
//        6. The number of trips starting at C and ending at C with a maximum of 3 stops.  In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
//        7. The number of trips starting at A and ending at C with exactly 4 stops.  In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
//        8. The length of the shortest route (in terms of distance to travel) from A to C.
//        9. The length of the shortest route (in terms of distance to travel) from B to B.
//        10.The number of different routes from C to C with a distance of less than 30.  In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.


//Test Input:
//        For the test input, the towns are named using the first few letters of the alphabet from A to D.
// A route between two towns (A to B) with a distance of 5 is represented as AB5.
//        Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
//        Expected Output:
//        Output #1: 9
//        Output #2: 5
//        Output #3: 13
//        Output #4: 22
//        Output #5: NO SUCH ROUTE
//        Output #6: 2
//        Output #7: 3
//        Output #8: 9
//        Output #9: 9
//        Output #10: 7

public class RailRoadServiceTest {

    Graph graph = new Graph();

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void test() {

        graph.addTown("A");
        graph.addTown("B");
        graph.addTown("C");
        graph.addTown("D");
        graph.addTown("E");

        graph.addRoute("A", "B", 5);
        graph.addRoute("B", "C", 4);
        graph.addRoute("C", "D", 8);
        graph.addRoute("D", "C", 8);
        graph.addRoute("D", "E", 6);
        graph.addRoute("A", "D", 5);
        graph.addRoute("C", "E", 2);
        graph.addRoute("E", "B", 3);
        graph.addRoute("A", "E", 7);

//        #1
        assertEquals("Nodes A-B-C", 9, graph.getDistance("A", "B", "C"));

//        #2
        assertEquals("Nodes A-D", 5, graph.getDistance("A", "D"));

//        #3
        assertEquals("Nodes A-D-C", 13, graph.getDistance("A", "D", "C"));

//        #4
        assertEquals("Nodes A-E-B-C-D", 22, graph.getDistance("A", "E", "B", "C", "D"));

//        #5
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("NO SUCH ROUTE");
        graph.getDistance("A", "E", "D");


//      #6
        int counter = 0;
        graph.dfs("C", "C", new int[graph.getSize()][graph.getSize()], null, new Stack<Town>(), 3, 0, -1, 0);
        for (Trip t : graph.getTrips()) {
            if (t.getStops() <= 3) counter++;
        }
        assertEquals("A maximum of 3 stops", 2, counter);

//      #7
        counter = 0;
        graph.dfs("A", "C", new int[graph.getSize()][graph.getSize()], null, new Stack<Town>(), 4, 0, -1, 0);

        for (Trip t : graph.getTrips()) {
            if (t.getStops() == 4) counter++;
        }
        assertEquals("Exactly 4 stops", 3, counter);
//      #8
        int dist = -1;
        graph.dfs("A", "C", new int[graph.getSize()][graph.getSize()], null, new Stack<Town>());

        for (Trip t : graph.getTrips()) {
            if (dist < 0) dist = t.getDistance();
            else if (t.getDistance() < dist) dist = t.getDistance();
        }
        assertEquals("shortest routes", 9, dist);

//      #9
        dist = -1;
        graph.dfs("B", "B", new int[graph.getSize()][graph.getSize()], null, new Stack<Town>());
        for (Trip t : graph.getTrips()) {
            if (dist < 0) dist = t.getDistance();
            else if (t.getDistance() < dist) dist = t.getDistance();
        }
        assertEquals("shortest route", 9, dist);

//      #10
        counter = 0;
        graph.dfs("B", "B", new int[graph.getSize()][graph.getSize()], null, new Stack<Town>(), -1, 0, 30, 0);

        for (Trip t : graph.getTrips()) {
            counter++;
        }
//        assertEquals("shortest route", 9, graph.getTrips().size());

    }
}