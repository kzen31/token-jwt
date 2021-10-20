package backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class RestRepositoryConfiguration implements RepositoryRestConfigurer {

    private WebApplicationContext webApplicationContext;

    @Autowired
    public RestRepositoryConfiguration(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

}

