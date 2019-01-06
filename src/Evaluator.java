import listClustering.Cluster;
import util.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Evaluator {

    public static void finalize(Data data) throws FileNotFoundException {
        System.setOut(new PrintStream("finalOutput.txt"));
        for (Integer qId : data.getQueryClusters().keySet()) {
            System.out.println("----------------- " + qId + " ---------------------");
            for (Cluster cluster : data.getQueryClusters().get(qId))
                if (cluster.getFinalItems() == null)
                    System.out.println("NULL");
                else {
                    for (String item : cluster.getFinalItems().keySet())
                        System.out.println(item + " - " + cluster.getFinalItems().get(item));
                    System.out.println();
                }
        }
        clean();
    }

    public static void clean() throws FileNotFoundException {
        Scanner scan=new Scanner(new File("finalOutput.txt"));
        System.setOut(new PrintStream("output.txt"));
        while (scan.hasNextLine())
        {
            String line = scan.nextLine();
            if(line.contains("-"))
            {
                System.out.println(line);
            }
            if(line.equals("NULL"))
            {
                System.out.println();
            }
        }
    }
}
