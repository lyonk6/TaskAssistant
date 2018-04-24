package api.v2.auth;

import api.v2.UnitTestHelper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by kennethlyon on 8/8/16.
 */
public class AuthApiHelper extends UnitTestHelper {
    private static Logger LOGGER = LoggerFactory.getLogger(AuthApiHelper.class);

    /**
     * Accept an ArrayList of backtick delimited strings and return an ArrayList of JSONObjects.
     * @param backtickUsers
     * @return
     * @throws Exception
     */
    protected static ArrayList<JSONObject> toJSONObjects(ArrayList<String> backtickUsers) throws Exception{
        ArrayList<JSONObject> myJSONObjects = new ArrayList<JSONObject>();
        for(String s: backtickUsers) {
            String[] elementArray = s.split("`");
            JSONObject jsonObj = new JSONObject();

            jsonObj.put("id", Integer.parseInt(elementArray[0]));
            jsonObj.put("email", elementArray[1].trim());
            jsonObj.put("password", elementArray[2].trim());
            if(elementArray.length>3){
                jsonObj.put("calendarIds", toIntegerArrayList(elementArray[3]));
                jsonObj.put("categoryIds", toIntegerArrayList(elementArray[4]));
                jsonObj.put("scheduleIds", toIntegerArrayList(elementArray[5]));
                jsonObj.put("taskListIds", toIntegerArrayList(elementArray[6]));
            }
            myJSONObjects.add(jsonObj);
        }
        return myJSONObjects;
    }
}
