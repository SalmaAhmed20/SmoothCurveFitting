import java.util.ArrayList;
import java.util.Random;

public class SmthCrvFit {
    private final int Degree;
    ArrayList<Pair<Double,Double>> points;
    private final double LowBound;
    private final double UpBound;

    private final int chromosomeLength;
    private final int PopSize;
    private final ArrayList<Chromosome> Population;
    private ArrayList<Chromosome> new_Population;
    private final double Pc; //crossover [0.4->0.8] e.g. 0.6
    private final double Pm; //mutation [0.001->0.1] e.g. 0.015


    public SmthCrvFit(int Degree, ArrayList<Pair<Double, Double>> points) {
        //proplem parameters
        this.Degree=Degree;
        this.points=points;
        LowBound = -10;
        UpBound = 10;
        //GA parameters

        this.chromosomeLength=Degree+1;
        PopSize=100;
        Population = new ArrayList<>(PopSize);
        Pc = 0.8;
        Pm = 0.015;
        //Algorithm
        intialPopulation(); //worked
//        System.out.println(Degree);
//        for (int i = 0; i < Population.size(); i++) {
//            for (int j = 0; j < Population.get(i).coefficients.size(); j++) {
//                System.out.print(Population.get(i).coefficients.get(j)+ " ");
//            }
//            System.out.println("\n");
//
//        }
        new Chromosome(chromosomeLength);
        Chromosome c;
        for (int t = 0; t < 100  ; t++) {
            //calculate fitness to each chromosome
            for (Chromosome chromosome : Population) {
                chromosome.Chromosomefittness(this.points);
            }
            //selection
            Tournament_Selection();
            var parent1 =new_Population.get(0);
            Tournament_Selection();
            var parent2 =new_Population.get(0);
            //crossover
            var Cr =TwoPointCrossOver(parent1,parent2);

            //mutation
            Non_Uniform_Mutation(Cr.get(0),t,100);


            Non_Uniform_Mutation(Cr.get(1),t,100);

            //Replacment
            Population.add(Cr.get(0));
            Population.add(Cr.get(1));
            c =getbest();

            if (c.fitness<10)
                return;

        }
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
        new Random();
        //Math.floor(Math.random()*(max-min+1)+min)
        int r1 = (int) Math.floor(Math.random() * ((this.Population.size() - 1) + 1) + 0);
        int r2 = (int) Math.floor(Math.random() * ((this.Population.size() - 1) + 1) + 0);
        // we will select two chromosomes
        while(r1==r2) {
             r1 = (int) Math.floor(Math.random() * ((this.Population.size() - 1) + 1) + 0);
             r2 = (int) Math.floor(Math.random() * ((this.Population.size() - 1) + 1) + 0);
        }
        //we selet chromosome with the highest fitness
                if (Population.get(r1).fitness>=Population.get(r2).fitness) {
                    new_Population.add(Population.get(r1));
                    Population.remove(r1);//select it
                } else  {
                    new_Population.add(Population.get(r2));
                    Population.remove(r2);
                }

    }
    private ArrayList<Chromosome> TwoPointCrossOver(Chromosome c1,Chromosome c2)
    {
        ArrayList<Chromosome> CrossOverResult= new ArrayList<>();
        Chromosome OffSpring1 = new Chromosome(chromosomeLength);
        Chromosome OffSpring2= new Chromosome(chromosomeLength);
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
    private void Non_Uniform_Mutation(Chromosome C,int Current_Iteration,int Max_Iteration){
        // looping around genes of one chromosome
           for(int i=0;i<this.chromosomeLength;i++){
               //r random number to see if mutation will occur
               double r = Math.random();//->[0,1[
               if (r <= this.Pm){
                   //we will do mutation
                   double Delta_L =C.coefficients.get(i) - this.LowBound;
                   double Delta_U =this.UpBound - C.coefficients.get(i);
                   double Y;
                   //first we will generate random number->r1 [0,1]
                   double r1 = Math.random();
                   if(r1 < 0.5 || r1 == 0.5){
                       Y = Delta_L;
                   }else{
                       Y = Delta_U;
                   }
                   double r2 = Math.random(); //[0,1]
                   Random rand = new Random();
                   double b = 0.5 +(5-0.5)*rand.nextDouble();
                   //the value of mutation at generation t
                   double partOfValue = Math.pow(1-((double)Current_Iteration/(double) Max_Iteration),b);
                   double Value_Of_Change = Y *(1-Math.pow(r2,partOfValue));
                   if(Y == Delta_L){
                       C.coefficients.set(i,C.coefficients.get(i) - Value_Of_Change);
                   }else{
                       C.coefficients.set(i,C.coefficients.get(i) + Value_Of_Change);
                   }

               }

           }
    }
    public Chromosome getbest()
    {
        double fitness=Population.get(0).fitness;
        int idx=0;
        for(int i=1;i<Population.size();i++)
        {
            if(fitness<Population.get(i).fitness)
            {
                idx=i;
                fitness=Population.get(i).fitness;
            }
        }
        return Population.get(idx);
    }
}
