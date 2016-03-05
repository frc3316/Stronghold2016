import java.util.ArrayList;

/**
 * Created by Leon on 05/03/2016.
 */
public class Try {

    public static void main(String[] args) {
        ArrayList<Double> l = new ArrayList<Double>();
        l.add(350.0);
        l.add(350.0);
        l.add(350.0);
        l.add(350.0);
        l.add(410.0);
        System.out.println(getCorrectDistance(l, 30.0));
    }

    private static double getCorrectDistance(ArrayList<Double> distances, Double thresh) {
        Double distancesSum = 0.0;
        for ( Double d : distances) { distancesSum += d; }
        Double distancesAvg = distancesSum/distances.size();

        ArrayList<Double> elementsPassed = new ArrayList<>(); // the elements that passed the delta avg distance < thresh.

        for (int i = 0; i < distances.size(); i ++)
        {
            if (Math.abs(distancesAvg - distances.get(i)) < thresh) {
                elementsPassed.add(distances.get(i));
            }
        }

        if (elementsPassed.size() > 0)
        {
            Double newDistancesSum = 0.0;
            for ( Double d : elementsPassed) { newDistancesSum += d; }
            return newDistancesSum/elementsPassed.size();
        }
        else {
            return distances.get(distances.size()-1);
        }

    }
}
