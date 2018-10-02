package api.v2;
import api.v2.error.BusinessException;
import api.v2.error.CriticalException;
import api.v2.error.SystemException;
import api.v2.model.*;
import api.v2.error.Error;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * AuthRequestHandler is a subclass of BaseRequestHandler and provides functionality
 * for APIs that require Authentication. This class verifies that email and password
 * strings are valid, and verifies that an incoming model object does not reference
 * any objects it does not have permission to access.
 *
 * @author kennethlyon
 *
 */
public class AuthRequestHandler extends BaseRequestHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRequestHandler.class);

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
    public static void validatePassword(User fromClient, User fromRepository) throws BusinessException {
        if(fromClient.getPassword().equals(fromRepository.getPassword()))
            return;
        else{
            LOGGER.error(fromClient.toJson());
            LOGGER.error(fromRepository.toJson());
            throw new BusinessException("Incorrect password.", Error.valueOf("INCORRECT_PASSWORD_ERROR"));
        }
    }

    /**
     * Verifies that an client model object is valid and only references
     * objects owned by the owner of said object.
     * @param modelObject
     */
    protected void verifyPrivileges(TaskAssistantModel modelObject) throws BusinessException, SystemException{
        int ownerId=getOwnerId(modelObject);


        try {
            verifyPrivileges(modelObject.getCalendarIds(), ownerId);
        }catch (CriticalException ce){
            // do nothing
        }

        try {
            verifyPrivileges(modelObject.getCategoryIds(), ownerId);
        }catch (CriticalException ce){
            // do nothing
        }

        try {
            verifyPrivileges(modelObject.getTaskListIds(), ownerId);
        }catch (CriticalException ce){
            // do nothing
        }

        try {
            verifyPrivileges(modelObject.getTaskIds(), ownerId);
        }catch (CriticalException ce){
        // do nothing
        }

        try {
            verifyPrivileges(modelObject.getScheduleIds(), ownerId);
        }catch (CriticalException ce){
            // do nothing
        }

        try {
            verifyPrivileges(modelObject.getReminderIds(), ownerId);
        }catch (CriticalException ce){
            // do nothing
        }

        try {
            verifyPrivileges(modelObject.getTimeBlockIds(), ownerId);
        }catch (CriticalException ce){
            // do nothing
        }
    }

    /**
     * Verify that an ArrayList of model object IDs belong to the given
     * owner ID.
     * @param modelIds
     * @param ownerId
     * @throws BusinessException
     * @throws SystemException
     */
    protected void verifyPrivileges(ArrayList<Integer> modelIds, int ownerId)
            throws BusinessException, SystemException {
        for(int i: modelIds)
            if(ownerId==getOwnerId(modelRepository.get(i)))
                continue;
            else
                throwObjectOwnershipError(ownerId, "The object with ID=" + i +
                 " does not belong to this user! ID=" + ownerId);

    }


    /**
     * Return the ID of the User that owns this object.
     * @param modelObject
     * @return
     */
    protected int getOwnerId(TaskAssistantModel modelObject) throws BusinessException, SystemException{
        if(modelObject.getParent()==modelObject.getId())
            return modelObject.getId();
        else
            return getOwnerId(modelRepository.get(modelObject.getId()));
    }


    private void throwObjectOwnershipError(int userId, String message) throws BusinessException, SystemException{
        User user = new User();
        user.setId(userId);
        user=(User) modelRepository.get(user);
        message+=" {id: " + userId +", email: " + user.getEmail() + "}";
        throw new BusinessException(message, Error.valueOf("OBJECT_OWNERSHIP_ERROR"));
    }

}
