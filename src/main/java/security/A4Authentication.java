package security;

import java.security.Principal;
import java.util.Base64;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.glassfish.soteria.WrappingCallerPrincipal;
@ApplicationScoped
public class A4Authentication implements HttpAuthenticationMechanism {
    
    @Inject
    private IdentityStore iStore;
    
    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response,
            HttpMessageContext httpMessageContext) throws AuthenticationException {
        AuthenticationStatus result = httpMessageContext.doNothing();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null) {
            result =httpMessageContext.responseUnauthorized();
        }else {
            System.out.println("In validateRequest");
            String name = null;
            String pw = null;
            boolean basic = authHeader.toLowerCase().startsWith(request.BASIC_AUTH.toLowerCase());
            String b64Token = authHeader.substring(request.BASIC_AUTH.length() + 1,authHeader.length());
            byte[] token = Base64.getDecoder().decode(b64Token);
            String tmp = new String (token);
            String[] tokenFields = tmp.split(":");
            if(tokenFields.length == 2) {
                name = tokenFields[0];
                pw=tokenFields[1];
            }
          if(name!= null && pw!= null) {
              CredentialValidationResult vRes = iStore.validate(new UsernamePasswordCredential(name,pw)); 
              if(vRes.getStatus() == CredentialValidationResult.Status.VALID) {
                  Principal p = vRes.getCallerPrincipal();
                  if(p instanceof WrappingCallerPrincipal ) {
                      p = ((WrappingCallerPrincipal) p).getWrapped();
                  }
                  result = httpMessageContext.notifyContainerAboutLogin(p, vRes.getCallerGroups());
              }
          }else {
              result = httpMessageContext.responseUnauthorized();
          }
        }
        return result;
    }

}
