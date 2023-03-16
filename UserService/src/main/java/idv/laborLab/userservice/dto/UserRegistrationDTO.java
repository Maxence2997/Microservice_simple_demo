package idv.laborLab.userservice.dto;

import java.time.LocalDate;

public record UserRegistrationDTO(String userName, String firstName, String lastName, String email, String password,
                                  String phoneNumber, LocalDate dateOfBirth) {
}
