package api.v1.helper;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;

/**
 * Created by kennethlyon on 8/27/16.
 */
public class ModelHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelHelper.class);
    /**
     * Create a deep copy of an ArrayList of Integers. This method is used
     * frequently to copy reference arrays to
     * @param integerArrayList
     * @return
     */
    public static ArrayList<Integer> copyIntegerArrayList(ArrayList<Integer> integerArrayList){
        ArrayList<Integer> copyOf_integerArrayList=new ArrayList<Integer>();
        if(integerArrayList==null)
            return null;
        else {
            for (Integer i : integerArrayList)
                copyOf_integerArrayList.add(new Integer(i));
            return copyOf_integerArrayList;
        }
    }

    public static ArrayList<Integer> mergeIntegerArrayList(ArrayList<Integer> arrayList1, ArrayList<Integer> arrayList2){
        if(arrayList1==null && arrayList2==null)
            return null;
        //First make a copy of arrayList1.
        arrayList1=copyIntegerArrayList(arrayList1);
        //Second, remove duplicates:
        arrayList1.removeAll(arrayList2);
        //Finally merge the two arrayList:
        arrayList1.addAll(arrayList2);
        return arrayList1;
    }
}
