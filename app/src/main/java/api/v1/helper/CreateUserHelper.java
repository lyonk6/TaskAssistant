package api.v1.helper;

import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.model.*;
import api.v1.error.Error;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Created by kennethlyon on 1/10/17.
 */
public class CreateUserHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserHelper.class);

    /**
     * This method verifies that an email is well formed.
     * @param email
     * @throws BusinessException
     */
    public static void verifyEmailIsValid(String email) throws BusinessException{
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            LOGGER.error("Supplied email address: {} is not valid.", email);
            throw  new BusinessException("Email address: " + email + " is invalid.", Error.valueOf("INVALID_EMAIL_ERROR"));
        }
    }

    public static void verifyPasswordIsValid(String password) throws BusinessException{
        if(!password.matches("(?=^.{8,16}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+}{\":;'?/>.<,])(?!.*\\s).*$"))
            throw new BusinessException("Try another password. ", Error.valueOf("INVALID_PASSWORD_ERROR"));
    }

    /**
     * Use to validate the supplied password from a GetUser request.
     * @param fromClient
     * @param fromRepository
     * @return
     */
    public static void validatePassword(User fromClient, User fromRepository) throws BusinessException, SystemException {
        if(fromClient.getPassword().equals(fromRepository.getPassword()))
            return;
        else{
            LOGGER.error(fromClient.toJson());
            LOGGER.error(fromRepository.toJson());
            throw new BusinessException("Incorrect password.", Error.valueOf("INCORRECT_PASSWORD_ERROR"));
        }
    }
}
