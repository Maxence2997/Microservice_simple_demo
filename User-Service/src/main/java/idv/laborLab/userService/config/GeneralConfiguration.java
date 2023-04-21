package idv.laborLab.userService.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("idv.labor-lab")
public class GeneralConfiguration {

    private String idCounterKey;
    @Bean
    public String idCounterKey(){
        return this.idCounterKey;
    }
}
