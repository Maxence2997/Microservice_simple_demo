package idv.laborLab.userService.dto;

import idv.laborLab.sharedLibrary.objects.UserRegistrationSO;
import idv.laborLab.sharedLibrary.objects.UserSO;
import idv.laborLab.sharedLibrary.objects.UserSecurityInfoSO;
import idv.laborLab.userService.entity.User;
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

    public User convertToUserEntity() {

        return User.builder()
                   .userName(this.userName)
                   .firstName(this.firstName)
                   .lastName(this.lastName)
                   .email(this.email)
                   .phoneNumber(this.phoneNumber)
                   .build();
    }

    public UserRegistrationSO buildUserRegistrationSO(long newId, byte[] encryptedPassword) {

        UserSO userSO = UserSO.builder()
                              .id(newId)
                              .userName(this.userName)
                              .firstName(this.firstName)
                              .lastName(this.lastName)
                              .email(this.email)
                              .phoneNumber(this.phoneNumber)
                              .build();
        UserSecurityInfoSO userSecurityInfoSO = UserSecurityInfoSO.builder()
                                                                  .userId(newId)
                                                                  .passwordByte(encryptedPassword)
                                                                  .build();
        return UserRegistrationSO.builder()
                                 .userSO(userSO)
                                 .userSecurityInfoSO(userSecurityInfoSO)
                                 .build();
    }
}
