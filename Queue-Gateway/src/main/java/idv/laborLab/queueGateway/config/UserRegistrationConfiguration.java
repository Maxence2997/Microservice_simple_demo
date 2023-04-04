package idv.laborLab.queueGateway.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRegistrationConfiguration {
    public static final String USER_REGISTRATION = "userRegistration";
//    public static final String USER_REGISTRATION_EXCHANGE = "userRegistration";
//    public static final String USER_REGISTRATION_ROUTING_KEY = "userRegistration";

    @Bean
    public Queue userRegistrationQueue() {

        return new Queue(USER_REGISTRATION);
    }

    @Bean
    public DirectExchange userRegistrationExchange(){
        return new DirectExchange(USER_REGISTRATION);
    }

    @Bean
    public Binding userRegistrationBinding(Queue userRegistrationQueue, DirectExchange userRegistrationExchange) {

        return BindingBuilder.bind(userRegistrationQueue).to(userRegistrationExchange).with(USER_REGISTRATION);
    }
}
