package library.security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfig {
    private final SecurityService securityService;

    public AdminConfig(SecurityService securityService) {
        this.securityService = securityService;
    }
}
