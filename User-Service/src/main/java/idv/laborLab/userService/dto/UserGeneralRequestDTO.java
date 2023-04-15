package idv.laborLab.userService.dto;

import org.springframework.lang.NonNull;

public record UserGeneralRequestDTO(@NonNull String indexString, @NonNull UserIndex userIndex) {
}
