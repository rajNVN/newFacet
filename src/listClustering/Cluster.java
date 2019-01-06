package listClustering;

import util.Data;

import java.util.*;

public class Cluster{
    public Integer qId;
    ArrayList<Integer> lists;
    //Map<Integer,Set<String>> lists;
    Map<Integer,Double> weights;

    public Map<String, Double> getFinalItems() {
        return finalItems;
    }

    public void setFinalItems(Map<String, Double> finalItems) {
        this.finalItems = finalItems;
    }

    Map<String,Double> finalItems;



    public Map<Integer, String> getItems() {
        return items;
    }

    public Map<String, Double> getItemWeight() {
        return itemWeight;
    }

    Map<Integer,String> items;
    Map<String,Double> itemWeight;
    double s;

    public ArrayList<Integer> getLists() {
        return lists;
    }

    public Map<Integer, Double> getWeights() {
        return weights;
    }

    public Map<String, ArrayList<Integer>> getSiteLists() {
        return siteLists;
    }

    Map<String,ArrayList<Integer>> siteLists;
    Integer centerKey;

    public Cluster(Integer qId, Integer centerKey)
    {
        this.qId=qId;
        this.centerKey=centerKey;
        lists=new ArrayList<>();
        weights=new HashMap<>();
        siteLists=new HashMap<>();
        items=new HashMap<>();
        itemWeight=new HashMap<>();
    }


    public void addList(String identities, Data data)
    {
        int qId=Integer.parseInt(identities.split("\\|")[0]);
        int key=Integer.parseInt(identities.split("\\|")[1]);
        //System.out.println(qId+"."+key);
        try {
            weights.put(key, data.getSl(qId).get(key));
            lists.add(key);
        }catch (NullPointerException e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public int getSitesCount(Data data)
    {
        ArrayList<String> siteList=new ArrayList<>();
        for(int key:lists)
            siteList.add(data.getSite(qId).get(key));
        return siteList.size();
    }

    public void parseSiteLists(Data data)
    {
        for(Integer key: lists)
        {
            String site= data.getSite(qId).get(key);
            if(siteLists.containsKey(site))
                siteLists.get(site).add(key);
            else
            {
                siteLists.put(site,new ArrayList<>());
                siteLists.get(site).add(key);
            }
        }
    }

    public Integer getCenterKey() {
        return centerKey;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public void parseItems(Data data)
    {
        for (int key:lists)
            for(String item:data.getList(qId).get(key))
                itemWeight.put(item,null);
    }

    public long getMaxSize(Data data)
    {
        long max=0;
        for(ArrayList<String> list:data.getList(qId).values())
            if(max<list.size())
                max=list.size();
        return max;
    }

    public long getTotalSize(Data data)
    {
        long sum=0;
        for(ArrayList<String> list:data.getList(qId).values())
            sum+=list.size();
        return sum;
    }

    public void showDensity(Data data)
    {
        for(String site:siteLists.keySet())
        {
            System.out.println("for Site : "+site+" -> "+siteLists.get(site).size());
            calcAndDisplayInnerDensity(site,data);
        }
    }

    public void calcAndDisplayInnerDensity(String site,Data data)
    {
        double density=0L;
        for(Integer key:siteLists.get(site))
            density+=data.getSl(qId).get(key);
        System.out.println("\t\t\t\t\tInner density : "+density);
    }
}
