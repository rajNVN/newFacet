package itemRanking;

import listClustering.Cluster;
import util.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Sitem {

    public static double calculateS(String item, Cluster cluster,Data data)
    {
        double answer=0;
        for(String site:cluster.getSiteLists().keySet())
        {
            double avg=avg(cluster,item,site,data);
            if(avg!=0.0)
                answer+= 1 / Math.sqrt(avg);
        }
        return answer;
    }

    public static double avg(Cluster cluster,String item,String site,Data data)
    {
        double answer=0;
        ArrayList<Integer> lists=getLists(cluster,item,site,data);
        if(!lists.isEmpty())
            answer=1/lists.size();
        for(Integer key:lists)
        {
            answer += cluster.getWeights().get(key);
        }
        return answer;
    }

    public static ArrayList<Integer> getLists(Cluster cluster, String item, String site, Data data)
    {
        ArrayList<Integer> answer=new ArrayList<>();
        for(Integer key:cluster.getSiteLists().get(site))
            if(data.getList(cluster.qId).get(key).contains(item))
                answer.add(key);
        return answer;
    }
}
