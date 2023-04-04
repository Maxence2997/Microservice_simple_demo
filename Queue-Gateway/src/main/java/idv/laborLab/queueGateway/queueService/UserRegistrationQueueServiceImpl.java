package idv.laborLab.queueGateway.queueService;

import idv.laborLab.sharedLibrary.objects.UserRegistrationSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static idv.laborLab.queueGateway.config.UserRegistrationConfiguration.USER_REGISTRATION;


@Slf4j
@Service
public class UserRegistrationQueueServiceImpl implements UserRegistrationQueueService{


    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserRegistrationQueueServiceImpl(RabbitTemplate rabbitTemplate){
        log.info("Setting exchange: {}, routingKey: {}", USER_REGISTRATION, USER_REGISTRATION);
        rabbitTemplate.setExchange(USER_REGISTRATION);
        rabbitTemplate.setRoutingKey(USER_REGISTRATION);
        this.rabbitTemplate = rabbitTemplate;
    }

    public void convertAndSend(UserRegistrationSO userRegistrationSO) {
        rabbitTemplate.convertAndSend(userRegistrationSO);
    }

}
