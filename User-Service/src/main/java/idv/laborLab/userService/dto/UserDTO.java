package idv.laborLab.userService.dto;

import idv.laborLab.userService.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {

    private long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public User convertToUserEntity() {

        return User.builder()
                   .id(this.userId)
                   .userName(this.userName)
                   .lastName(this.lastName)
                   .email(this.email)
                   .addressId(0L)
                   .phoneNumber(this.phoneNumber)
                   .dateOfBirth(this.dateOfBirth)
                   .build();
    }
}
