package fr.ippon.gwt.server;

import org.springframework.stereotype.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.ippon.gwt.client.GreetingService;
import fr.ippon.gwt.shared.FieldVerifier;

/**
 * The server side implementation of the RPC service.
 */
@Service("greetService")
public class GreetingServiceImpl extends RemoteServiceServlet implements
    GreetingService {

	private static final long serialVersionUID = 588551943990379565L;

	public String greetServer(String input) throws IllegalArgumentException {
	    // Verify that the input is valid.
	    if (!FieldVerifier.isValidName(input)) {
	      // If the input is not valid, throw an IllegalArgumentException back to
	      // the client.
	      throw new IllegalArgumentException(
	          "Name must be at least 4 characters long");
	    }
	
	    String serverInfo = getServletContext().getServerInfo();
	    String userAgent = getThreadLocalRequest().getHeader("User-Agent");
	
	    // Escape data from the client to avoid cross-site script vulnerabilities.
	    input = escapeHtml(input);
	    userAgent = escapeHtml(userAgent);
	
	    return "Hello, " + input + "!<br><br>I am running " + serverInfo
	        + ".<br><br>It looks like you are using:<br>" + userAgent;
  }

  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   *
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
        ">", "&gt;");
  }
}
