package backend.service.oauth;

import backend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class Oauth2ClientDetailsService implements ClientDetailsService {

    private final ClientRepository clientRepository;

    @Autowired
    public Oauth2ClientDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        ClientDetails clientDetails = clientRepository.findOneByClientId(s);
        if (null == clientDetails) {
            throw new ClientRegistrationException("Client not found");
        }
        return clientDetails;
    }

    public void clearCache(String s) {
        System.out.println("ini cache  oauth_client_id=  " + s);
    }

}

