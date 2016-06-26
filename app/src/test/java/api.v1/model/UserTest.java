package api.v1.model;

import api.v1.error.BusinessException;
import api.v1.error.Error;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class serves a a container for test case proto-Users.
 * Created by kennethlyon on 6/9/16.
 */
public class UserTest {
    private static Logger LOGGER = LoggerFactory.getLogger(UserTest.class);
    private static ArrayList<String> validUsers;
    private static ArrayList<String> errorUsers;
    private static ArrayList<String> validUpdates;
    private static ArrayList<String> errorUpdates;

    static {
        /* Add valid Users. Users fields are arranged in the order:
         */
        validUsers = new ArrayList<String>();
        validUpdates = new ArrayList<String>();
        errorUsers = new ArrayList<String>();
        errorUpdates = new ArrayList<String>();
        validUsers.add("01`mikehedden@gmail.com`a681wo$dKo");
        validUsers.add("02`kenlyon@gmail.com`Mouwkl87%qo");
        validUsers.add("03`kenlyon@test.com`e-W^2VmQ");
        validUsers.add("04`fatsteaks@gmail.com`+%D5|x%b");
        validUsers.add("05`yannisgreek@gmail.com`sy@UCL0_");
        validUsers.add("06`rustypuppy@gmail.com`3Z^V)xkE");
        validUsers.add("07`yo.momma.so.fat@gmail.com`6PnCK/?8");
        validUsers.add("08`under_scores_rule@gmail.com`6~Zas2R*");
        validUsers.add("09`test@mikehedden.gmail.com`i2@<uMtJ");
        errorUsers.add("10`mike`password1");
        errorUsers.add("11`mike@test.com` ");
        errorUsers.add("12`mike@test@test.com`aHouw8789");
        errorUsers.add("13`houston@wehaveaproblem.com`11111111111111111111");
        errorUsers.add("14`toosimple@password.com`ab1");
    }
        // Creating invalid requests 

    public static ArrayList<JSONObject> getValidTestUsersAsJson() {
        ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<JSONObject>();

        for (String s : validUsers)
            jsonObjectArrayList.add(UserTest.toJson(s));
        return jsonObjectArrayList;
    }

    public static ArrayList<JSONObject> getErrorTestUsersAsJson() {
        ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<JSONObject>();
        for (String s : errorUsers)
            jsonObjectArrayList.add(UserTest.toJson(s));
        return jsonObjectArrayList;
    }

    public static ArrayList<JSONObject> getValidTestUserUpdatesAsJson() {
        ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<JSONObject>();
        for (String s : validUpdates)
            jsonObjectArrayList.add(UserTest.toJson(s));
        return jsonObjectArrayList;
    }

    public static ArrayList<JSONObject> getErrorTestUserUpdatesAsJson() {
        ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<JSONObject>();
        for (String s : errorUpdates)
            jsonObjectArrayList.add(UserTest.toJson(s));
        return jsonObjectArrayList;
    }


    public static ArrayList<User> getValidTestUsersAsUsers() throws Exception{
        ArrayList<User> UserArrayList = new ArrayList<User>();
        for (String s : validUsers) {
            UserArrayList.add(UserTest.toUser(s));
        }
        return UserArrayList;
    }

    public static ArrayList<User> getValidTestUsersUpdatesAsUsers() throws Exception{
        ArrayList<User> UserArrayList = new ArrayList<User>();
        for (String s : validUpdates) {
            UserArrayList.add(UserTest.toUser(s));
        }
        return UserArrayList;
    }


    private static User toUser(String s) throws Exception{
        String[] UserElementArray = s.split("`");
        User User = new User();
        User.setId(Integer.parseInt(UserElementArray[0]));
        User.setEmail(UserElementArray[1]);
        User.setPassword(UserElementArray[2]);
        return User;
    }

    /**
     * Parse a String representing a given date and return a Date object.
     * String must be in the format: yyyy-MM-dd_HH:mm:ss
     *
     * @param stringDate
     * @return
     */
    private static Date parseJsonDateAsDate(String stringDate) throws BusinessException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        df.setLenient(false);
        Date result = null;
        try {
            result = df.parse(stringDate);
        } catch (java.text.ParseException e) {
            LOGGER.error("Exception while parsing date token: " + stringDate);
            throw new BusinessException("Error caused by the String date: " + stringDate, Error.valueOf("PARSE_DATE_ERROR"));
        }
        return result;
    }


    /**
     * Parse string as boolean.
     * @param b
     * @return
     */
    private static boolean parseJsonBooleanAsBoolean(String b) throws BusinessException{
        b = b.trim().toUpperCase();
        if (b.equals("TRUE"))
            return true;
        else if(b.equals("FALSE"))
            return false;
        else
            throw new BusinessException("Invalid boolean value: " + b, Error.valueOf("PARSE_BOOLEAN_ERROR"));
    }

    private static JSONObject toJson(String stringUser) {
        String[] UserElementArray = stringUser.split("`");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("id",       UserElementArray[0]);
        jsonObj.put("email",    UserElementArray[1]);
        jsonObj.put("password", UserElementArray[2]);
        LOGGER.info("Created request {}", jsonObj.toJSONString());
        return jsonObj;
    }

    /**
     * @throws Exception
     */
    @Test
    public void setUp() throws Exception {
        for(String s: validUsers){
            UserTest.toUser(s);
            LOGGER.info("Valid User {}", toJson(s));
        }

        for(String s: validUpdates){
            UserTest.toUser(s);
            LOGGER.info("Valid User {}", toJson(s));
        }

        for(String s: errorUsers){
            validateErrorUser(s);
            LOGGER.info("Error User {}", toJson(s));
        }
        
        for(String s: errorUpdates){
            validateErrorUser(s);
            LOGGER.info("Error User {}", toJson(s));
        }//*/

    }

    public void validateErrorUser(String s){
        boolean error=false;
        try{
            UserTest.toUser(s);
        }catch(Exception e){
            error=true;
            LOGGER.info("Invalid User returned error. " + e.getMessage());
        }
        if(!error){
            fail("Success returned for invalid User: " + s);
        }
    }
}