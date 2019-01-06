package listWeighting;

import util.Data;

import java.util.*;

public class Slist {

    public static void calculateSl(Data data) {
        for (int qId : data.getQueryR().keySet()) {
            data.getQuerySl().put(qId, new HashMap<>());
            //System.out.println("Q Id -> " + qId);
            for (int index : data.getList(qId).keySet()) {
                System.out.println(qId + " . " + index);
                data.getQuerySl().get(qId).put(index, 0.0);
                double sDoc = Sdoc.calculateSdoc(data, qId, index);
                double sIdf = Sidf.calculateIDF(data, qId, index);
                System.out.println("Sdoc :" + sDoc);
                System.out.println("Sidf :" + sIdf);
                data.getQuerySl().get(qId).put(index, sDoc * sIdf);
            }
        }
        List<String> listRemove = new ArrayList<>();
        for (Iterator<Integer> qId = data.getQuerySl().keySet().iterator(); qId.hasNext();) {
            Integer thisQid = qId.next();
            Map<Integer, Double> sl = data.getQuerySl().get(thisQid);
            for (Iterator<Integer> key = sl.keySet().iterator(); key.hasNext();) {
                Integer thisKey = key.next();
                Double weight = sl.get(thisKey);
                //System.out.println(thisQid + " - " + thisKey + " ---------------> " + weight);
                if (weight.isNaN()) {
                    listRemove.add(thisQid + "|" + thisKey);
                }
            }
        }
        for(String remove:listRemove) {
            System.out.println(remove);
            int qId=Integer.parseInt(remove.split("\\|")[0]);
            int key=Integer.parseInt(remove.split("\\|")[1]);
            //System.out.println(qId+"|"+key);
            data.getQuerySl().get(qId).remove(key);
            data.getQueryLists().get(qId).remove(key);
        }
    }
}
