package library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfig {

    private final SecurityService securityService;

    @Autowired
    public AdminConfig(SecurityService securityService) {
        this.securityService = securityService;
    }
}
