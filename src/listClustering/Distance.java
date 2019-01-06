package listClustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Distance {
    public static double getDistance(ArrayList<String> l1, ArrayList<String> l2)
    {
        //System.out.println("get dl for("+l1+","+l2+')');
        double answer=1;
        double itemsCount=0;
        for(String item:l1)
            if(l2.contains(item))
                itemsCount++;
        answer-=itemsCount/Integer.min(l1.size(),l2.size());
        return answer;
    }

    public static double getDistanceByNewMethod(ArrayList<String> l1,ArrayList<String> l2)
    {
        return intersection(l1, l2)/(double)union(l1, l2);
    }

    public static int union(ArrayList<String> l1,ArrayList<String> l2)
    {
        ArrayList<String> answer = new ArrayList<>();
        answer.addAll(l1);
        answer.addAll(l2);
        return answer.size();
    }

    public static int intersection(ArrayList<String> l1,ArrayList<String> l2)
    {
        int count = 0;
        for(String item:l1)
            if(l2.contains(item))
                count++;
        return count;
    }

    public static double getDistance(Cluster cluster1, Cluster cluster2)
    {
        double answer=0;
        /*double ans=0;
        for(ArrayList<String> list1:cluster1)
        {
            for (ArrayList<String> list2:cluster2)
            {
                ans= getDistance(list1,list2);
                if(ans>answer)
                    answer=ans;
            }
        }*/
        return answer;
    }
}
