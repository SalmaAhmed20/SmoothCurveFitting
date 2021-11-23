import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<Pair<Double,Double>> points=new ArrayList<>();
        File file=new File("input-2.txt");

        Scanner input =new Scanner(file);
        int testCases=input.nextInt();

        for (int i = 0; i < testCases; i++) {
            int nOfpoint=input.nextInt();
            int Degree=input.nextInt();
            for (int j = 0; j < nOfpoint; j++) {
                double x = input.nextDouble();
                double y = input.nextDouble();
                Pair<Double,Double> pair=new Pair<>(x,y);

            }
            System.out.println("Case "+i);
        }
    }
}
