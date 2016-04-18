package api.v1;
import api.v1.repo.UserRepository;
import api.v1.error.BadEmailException;
import api.v1.error.BadPasswordException;
import api.v1.error.BaseAuthRequestException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * We probably won't use this.
 * @author kennethlyon
 *
 */
public class BaseAuthRequestHandler extends BaseRequestHandler{

	/* Instantiate the user repository here. Only Auth classes need
	 * to access to the userRepository.
	 */
	protected static UserRepository userRepository;
	static{
		userRepository=new UserRepository();
	}
	/**
	 * Validates that an email is well formed. Throws Exception 
	 * if it is not well formed.
	 * @param email
	 * @return
	 */
	protected String parseJsonAsEmail(String email) throws BadEmailException{
		email=email.trim();
		if(!isValidEmail(email)){
			log.error("Supplied email address: {} is not well formed.", email);
			throw new BadEmailException("Email address: " + email + " is not well formed.");
		}
		return email;
	}

	private boolean isValidEmail(String email){
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}


	/**
	 * Validates the password as being well formed. Throws Exception.
	 * @param password
	 * @return
	 * @throws Exception
	 */
	protected String parseJsonAsPassword(String password) throws BadPasswordException{
		try{
		if(!password.matches("(?=^.{8,16}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+}{\":;'?/>.<,])(?!.*\\s).*$"))
			throw new BadPasswordException("Password: '"+ password + "' not strong enough.");
	
		}catch(BadPasswordException e){
			log.error("Exception while parsing request. " + e.getMessage());
			throw new BadPasswordException(e.getMessage());
		}
		return password;
	}
}
