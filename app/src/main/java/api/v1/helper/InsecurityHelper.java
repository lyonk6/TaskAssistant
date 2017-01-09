package api.v1.helper;

import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Use a simple Symmetric Authorization to encrypt/decrypt a String.
 *
 * Created by kennethlyon on 1/8/17.
 */
public class InsecurityHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsecurityHelper.class);
    private static boolean require_symmetric_authorization;
    private static BasicTextEncryptor textEncryptor;

    static {
        textEncryptor = new BasicTextEncryptor();
        String securityToken="";
        try{
            BufferedReader reader=new BufferedReader(new FileReader("/security/token"));
            securityToken= reader.readLine().trim();
            LOGGER.info("Here is the security token: " + securityToken);
            textEncryptor.setPassword(securityToken);
            require_symmetric_authorization=true;
        }catch (IOException ioe){
            require_symmetric_authorization=false;
            LOGGER.info("Warning! This security not enabled for this session! " +
                    "The file security token could not be read from source.");
        }
    }


    public static String decryptString(String s){
        if (require_symmetric_authorization==true)
            return textEncryptor.decrypt(s);
        else
            return s;
    }

    public static String encryptString(String s){
        if (require_symmetric_authorization==true)
            return textEncryptor.encrypt(s);
        else
            return s;
    }
}
