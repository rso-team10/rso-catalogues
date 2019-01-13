package si.fri.rso.team10.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import si.fri.rso.team10.configuration.ConfigurationProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class ConfigHealthCheck implements HealthCheck {

    @Inject
    private ConfigurationProperties configProps;

    @Override
    public HealthCheckResponse call() {
        var builder = HealthCheckResponse.named(ConfigHealthCheck.class.getName());

        String configValue;
        try {
            configValue = configProps.getStringProperty();
        } catch (Exception e) {
            return builder.down().build();
        }

        if ("kill-me".equals(configValue)) {
            return builder.down().build();
        } else {
            return builder.up().build();
        }
    }
}
