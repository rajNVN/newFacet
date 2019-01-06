package itemRanking;

import listClustering.Cluster;
import util.Data;
import util.Sorter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Item {

    public static void rankItems(Data data) throws IOException {
        for(Integer qId:data.getQueryClusters().keySet())
            for(Cluster cluster:data.getQueryClusters().get(qId))
                cluster.parseItems(data);
        BufferedWriter writer = new BufferedWriter(new FileWriter("valuec.txt"));
        BufferedWriter write = new BufferedWriter(new FileWriter("values.txt"));
        for (Integer qId:data.getQueryClusters().keySet())
        {
            int c=0;
            for (Cluster cluster:data.getQueryClusters().get(qId))
            {
                int aC=0;
                Map<String,Double> temp=new HashMap<>();
                for(String item:cluster.getItemWeight().keySet())
                {
                    double S=Sitem.calculateS(item,cluster,data);
                    cluster.getItemWeight().put(item,S);
                    if(S==0) {
                        String w = qId + " - " + item + " - " + S;
                        writer.write(w + "\n");
                    }
                    //check if facet accepted
                    if(S>1 && S>cluster.getSiteLists().size()/10){
                        write.write(qId + " - " + item + " - " + S+"\n");
                        aC++;
                        temp.put(item,S);
                    }
                }
                if(aC>0)
                    c++;
                //System.out.println(qId + " - count: "+iC+" - fCount: "+fC+ "- acceptCount: "+aC+ " -a%: "+(double)aC/iC*100);
                Map<String,Double> sorted=Sorter.sortByValuesS(temp);
                if(c>0)
                    cluster.setFinalItems(temp);
                else
                    cluster.setFinalItems(null);
            }
            System.out.println("for "+qId+"- "+data.getQueryClusters().get(qId).size()+" -"+c);
        }
        write.close();
        writer.close();
    }
}
