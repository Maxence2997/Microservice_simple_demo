package idv.laborLab.userService.dto;

import idv.laborLab.sharedLibrary.objects.UserRegistrationSO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDTO {

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public UserRegistrationSO convertToUserRegistrationSO() {

        return UserRegistrationSO.builder()
                                 .userName(this.userName)
                                 .firstName(this.firstName)
                                 .lastName(this.lastName)
                                 .email(this.email)
                                 .phoneNumber(this.phoneNumber)
                                 .password(this.password)
                                 .build();
    }
}
