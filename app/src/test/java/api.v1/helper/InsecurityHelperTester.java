package api.v1.helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static api.v1.helper.InsecurityHelper.basicTest;

/**
 * This class tests the InsecurityHelper Class.
 * @author kennethlyon
 */
public class InsecurityHelperTester {
    private Logger LOGGER = LoggerFactory.getLogger(InsecurityHelperTester.class);

    @Before
    public void setUp() throws Exception {
        LOGGER.info("Before test.");
    }

    @After
    public void tearDown() throws Exception {
        LOGGER.info("After test.");
    }

    @Test
    public void doPost() throws Exception {

        String message="Hi! My name is... (What?)  My name is... (who?) My name is... [tzichy-ichy] Slim Shady.";
        String myEncryptedText = InsecurityHelper.encryptString(message);
        String myPlainText =     InsecurityHelper.decryptString(myEncryptedText);

        LOGGER.info("Here is the original message : " + message);
        LOGGER.info("Here is the encrypted message: " + myEncryptedText);
        LOGGER.info("Here is the decrypted message: " + myPlainText);
        //*/
        basicTest();
    }
}
