package listWeighting;

import util.Data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Sdoc {

    public static double calculateSdoc(Data data, int qId, int index) {
        double sDoc = 0;
        Map<Integer, ArrayList<String>> R = data.getR(qId);
        ArrayList<String> list = data.getList(qId).get(index);
        for (int docId : R.keySet()) {
            double sdm = getSDM(R.get(docId), list);
            double sdr = getSDR(docId);
            sDoc += sdm * sdr;
        }
        return sDoc;
    }

    private static double getSDM(ArrayList<String> document, ArrayList<String> list) {
        double size_of_list = list.size();
        double no_of_elements = 0;
        for (String element : list)
            if (document.contains(element))
                no_of_elements++;
        double sdm = no_of_elements / size_of_list;
        //System.out.print(" SDM : " + sdm);
        return sdm;
    }

    private static double getSDR(double docRank) {
        double srm = 1 / Math.sqrt(docRank + 1);
        //System.out.println(" SRM : " + srm);
        return srm;
    }
}
