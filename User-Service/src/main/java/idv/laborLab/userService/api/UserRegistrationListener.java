package idv.laborLab.userService.api;

import idv.laborLab.sharedLibrary.miscellaneous.objects.UserRegistrationSO;
import idv.laborLab.userService.domain.UserDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationListener {

    private final UserDomainService userDomainService;

    @RabbitListener(queues = "userRegistration")
    public void userRegistrationHandler(UserRegistrationSO userRegistrationSO) {

        try {
            userDomainService.registerUserPostProcess(userRegistrationSO);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
