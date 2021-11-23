import java.util.ArrayList;


public class Chromosome {
    ArrayList<Double> coefficients;
    double fitness =0.0;
    Double fitnessUpperBound;

    Chromosome(int lengthofchromsome) {
        coefficients = new ArrayList<>(lengthofchromsome);
        fitnessUpperBound = 0.0;
    }

    void Chromosomefittness(ArrayList<Pair<Double, Double>> points) {
        fitness = 0.0;
        for (int i = 0; i < points.size(); ++i) {
            // 1 because for example if degree 2 equation equal ax^0+bx^1+cx^2
            //we start x^0 =1
            double calx = 1;
            double caly = 0;

            for (int j = 0; j < coefficients.size(); ++j) {
                //System.out.println(coefficients.get(j));
                //iter 0 y = absolute term
                caly += calx * coefficients.get(j);
                //change x power (multiple x on it self j time )and calc given point
                //i.e)itr 0 calx =(1)(0.2) x power 1
                //i.e)itr 1 calx=(0.2)(0.2) x power 2
                calx = calx* points.get(i).first;

            }
            //fitness is mean squared error so we calc to each point different between given y and calc y
            //small error best fitness
            fitness +=Math.pow((points.get(i).second-caly), 2);
        }
        fitness=points.size()/fitness;
        //System.out.println(fitness);
    }
}
