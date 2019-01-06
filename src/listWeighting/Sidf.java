package listWeighting;

import util.Data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Sidf {

    public static double calculateIDF(Data data, int qId,int index) {
        double answer;
        ArrayList<String> list = data.getList(qId).get(index);
        double sum = 0;
        for (String element : list)
            sum += idf(element, data.getR(qId));
        if (list.isEmpty())
            answer = 0;
        else
            answer = (double) 1 / list.size();
        answer *= sum;
        return answer;
    }

    public static double idf(String element, Map<Integer, ArrayList<String>> R)
    {
        int nE = 0;
        for (Integer key : R.keySet())
            if (R.get(key).contains(element))
                nE++;
        double result;
        result = Math.log(R.size() - nE + 0.5);
        result -= Math.log(nE + 0.5);
        return result;
    }
}
