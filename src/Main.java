import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<Pair<Double,Double>> points=new ArrayList<>();
        File file=new File("input-2.txt");
        FileWriter myWriter = new FileWriter("Output.txt");
        Scanner input =new Scanner(file);
        int testCases=input.nextInt();

        for (int i = 0; i < testCases; i++) {
            int nOfpoint=input.nextInt();
            int Degree=input.nextInt();
            for (int j = 0; j < nOfpoint; j++) {
                double x = input.nextDouble();
                double y = input.nextDouble();
                Pair<Double,Double> pair=new Pair<>(x,y);
                points.add(pair);

            }
            SmthCrvFit _smthCrvfit=new SmthCrvFit(Degree,points,nOfpoint);
            Chromosome _c=_smthCrvfit.getbest();
            myWriter.append("Case"+(i+1)+":");
            System.out.println("Case"+(i+1)+":");
            for (int j = 0; j < _c.coefficients.size(); j++) {
                myWriter.append(_c.coefficients.get(j).toString()+",");
                System.out.print(_c.coefficients.get(j).toString()+",");
            }
            System.out.println(_c.fitness);
            myWriter.append(" Error"+(1/_c.fitness)+"\n");
            System.out.println("\n Error "+(1/_c.fitness)+"\n");
        }
        myWriter.close();
    }
}
