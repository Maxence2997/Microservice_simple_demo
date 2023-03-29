package idv.laborLab.userService.dto;

import java.time.LocalDate;

public record UserDTO(String userName, String firstName, String lastName, String email, String address,
                      String phoneNumber, LocalDate dateOfBirth) {
}
