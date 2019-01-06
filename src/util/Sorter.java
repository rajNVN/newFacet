package util;

import java.util.*;

public class Sorter {
    public static Map<Integer, Double> sortByValues(Map<Integer, Double> list) {
        Comparator<Map.Entry<Integer, Double>> valueComparator = (o1, o2) -> o2.getValue().compareTo(o1.getValue());
        List<Map.Entry<Integer, Double>> listOfEntries = new ArrayList<>(list.entrySet());
        Collections.sort(listOfEntries, valueComparator);
        LinkedHashMap<Integer, Double> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
        for (Map.Entry<Integer, Double> entry : listOfEntries)
            sortedByValue.put(entry.getKey(), entry.getValue());
        return sortedByValue;
    }

    public static Map<String, Double> sortByValuesS(Map<String, Double> list) {
        Comparator<Map.Entry<String , Double>> valueComparator = (o1, o2) -> o2.getValue().compareTo(o1.getValue());
        List<Map.Entry<String, Double>> listOfEntries = new ArrayList<>(list.entrySet());
        Collections.sort(listOfEntries, valueComparator);
        LinkedHashMap<String, Double> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
        for (Map.Entry<String, Double> entry : listOfEntries)
            sortedByValue.put(entry.getKey(), entry.getValue());
        return sortedByValue;
    }
}
