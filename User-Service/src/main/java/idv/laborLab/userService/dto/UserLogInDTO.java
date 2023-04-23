package idv.laborLab.userService.dto;

import org.springframework.lang.NonNull;

public record UserLogInDTO(@NonNull String IndexString, @NonNull UserIndex userIndex, @NonNull String password) {
}
