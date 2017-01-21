package api.v1.helper;

import api.v1.error.BusinessException;
import api.v1.error.Error;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Use a simple Symmetric Authorization to encrypt/decrypt a String. This class
 * is designed to only work if an encryption key is provided at startup. If no
 * key is provided, no security decryption/encryption attempts will be made.
 *
 * Created by kennethlyon on 1/8/17.
 */
public class InsecurityHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsecurityHelper.class);
    private static boolean require_symmetric_authorization;
    private static BasicTextEncryptor textEncryptor;

    static {
        textEncryptor=new BasicTextEncryptor();
        String securityToken="";
        try{
            BufferedReader reader=new BufferedReader(new FileReader("/security/token"));
            securityToken= reader.readLine().trim();
            //LOGGER.info("Here is the security token: " + securityToken);
            textEncryptor.setPassword(securityToken);
            require_symmetric_authorization=true;
        }catch (IOException ioe){
            require_symmetric_authorization=false;
            LOGGER.info("Warning! This security not enabled for this session! " +
                    "The file security token could not be read from source.");
        }//*/
    }


    public static String decryptString(String s) throws BusinessException{
        try {
            if (require_symmetric_authorization == true)
                return textEncryptor.decrypt(s);
            else
                return s;
        }catch (Exception e){
            LOGGER.error("Error. Could not decrypt string. Did you forget to encrypt the input? {}", s);
            LOGGER.debug("Here is the string that broke it: {}", s);
            throw new BusinessException("The String provided could not be decrypted.", Error.valueOf("DECRYPTION_FAILURE_ERROR"));
        }

    }
    public static String encryptString(String s){
        if (require_symmetric_authorization==true)
            return textEncryptor.encrypt(s);
        else
            return s;
    }

    public static void basicTest(){
        BasicTextEncryptor myEncryptor = new BasicTextEncryptor();
        myEncryptor.setPassword("yAx`Q2eNCEOe:QQmWXLI3|21(>gLe9lJX<83C");

        String message = "In a world with only 8 Jan Michael Vincents ...";
        String myEncryptedText = myEncryptor.encrypt(message);
        String myPlainText =     myEncryptor.decrypt(myEncryptedText);
        LOGGER.info("Here is the original message : " + message);
        LOGGER.info("Here is the encrypted message: " + myEncryptedText);
        LOGGER.info("Here is the decrypted message: " + myPlainText);

    }
}
