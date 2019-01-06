package util;

import java.util.ArrayList;
import java.util.Set;

public class ListHandler {

    public static boolean compareLists(ArrayList<String> list1, ArrayList<String> list2)
    {
        for(String item:list1)
            if(!list2.contains(item))
                return false;
        return true;
    }
}
