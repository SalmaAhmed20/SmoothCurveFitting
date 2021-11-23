import java.util.ArrayList;
import java.util.Random;

public class SmthCrvFit {
    private int Degree;
    ArrayList<Pair<Double,Double>> points=new ArrayList<>();
    private int numOfpoints;
    private final double LowBound;
    private final double UpBound;

    private int chromosomeLength;
    private final int PopSize;
    private final ArrayList<Chromosome> Population;
    private ArrayList<Chromosome> new_Population;
    private final double Pc; //crossover [0.4->0.8] e.g. 0.6
    private final double Pm; //mutation [0.001->0.1] e.g. 0.015


    public SmthCrvFit(int Degree, ArrayList<Pair<Double,Double>> points,int numOfpoints) {
        //proplem parameters
        this.Degree=Degree;
        this.points=points;
        this.numOfpoints=numOfpoints;
        LowBound = -10;
        UpBound = 10;
        //GA parameters

        this.chromosomeLength=Degree+1;
        PopSize=100;
        Population =new ArrayList<Chromosome>(PopSize);
        Pc = 0.8;
        Pm = 0.015;

    }
    private void intialPopulation(){
        for (int i = 0; i < this.PopSize; i++) {
            Chromosome _chromosome=new Chromosome(this.Degree+1);
            for (int j = 0; j < (this.Degree+1); j++) {
                double random = LowBound + Math.random() * (UpBound - LowBound);
                _chromosome.coefficients.add(random);
            }
            Population.add(_chromosome);
        }
    }
    //Selection
    private void Tournament_Selection() {
        new_Population = new ArrayList<>();
        Random rand = new Random();
        //Math.floor(Math.random()*(max-min+1)+min)
        int r1 = (int) Math.floor(Math.random() * ((this. Population.size() - 1) - 0 + 1) + 0);
        int r2 = (int) Math.floor(Math.random() * ((this. Population.size() - 1) - 0 + 1) + 0);
        // we will select two chromosomes
        while(r1==r2) {
             r1 = (int) Math.floor(Math.random() * ((this. Population.size() - 1) - 0 + 1) + 0);
             r2 = (int) Math.floor(Math.random() * ((this. Population.size() - 1) - 0 + 1) + 0);
        }
        //we selet chromosome with the highest fitness
                if (Population.get(r1).fitness>=Population.get(r2).fitness) {
                    new_Population.add(Population.get(r1));
                    Population.remove(r1);//select it
                    return;
                } else  {
                    new_Population.add(Population.get(r2));
                    Population.remove(r2);
                    return;
        }
    }
    private ArrayList<Chromosome> TwoPointCrossOver(Chromosome c1,Chromosome c2)
    {
        ArrayList<Chromosome> CrossOverResult=new ArrayList<Chromosome>();
        Chromosome OffSpring1=c2;
        Chromosome OffSpring2=c2;
        double r2 = Math.random();
        if(r2<=Pc)
        {
            int Copoint1,Copoint2;
            do {
                //Math.floor(Math.random()*(max-min)+min)
                Copoint1 = (int) Math.floor(Math.random() * ((chromosomeLength - 1)) + 1);
                Copoint2 = (int) Math.floor(Math.random() * ((chromosomeLength -1)) + 1);
            }while (Copoint1==Copoint2);
            if(Copoint1 > Copoint2)
            {
                var temp = Copoint1;
                Copoint1= Copoint2;
                Copoint2=temp;
            }
            int i=0;
            for (; i <Copoint1 ; i++) {
                OffSpring1.coefficients.add(c1.coefficients.get(i));
                OffSpring2.coefficients.add(c2.coefficients.get(i));
            }
            for (; i <Copoint2 ; i++) {
                OffSpring1.coefficients.add(c2.coefficients.get(i));
                OffSpring2.coefficients.add(c1.coefficients.get(i));
            }
            for (; i <chromosomeLength ; i++) {
                OffSpring1.coefficients.add(c1.coefficients.get(i));
                OffSpring2.coefficients.add(c2.coefficients.get(i));
            }

            CrossOverResult.add(OffSpring1);
            CrossOverResult.add(OffSpring2);
            return CrossOverResult;
        }
        CrossOverResult.add(c1);
        CrossOverResult.add(c2);
        return CrossOverResult;
    }
}
