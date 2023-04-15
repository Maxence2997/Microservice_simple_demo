package idv.laborLab.userService.dto;

import lombok.Builder;
import org.springframework.lang.NonNull;

@Builder
public record UserGeneralRequestDTO(@NonNull String indexString, @NonNull UserIndex userIndex) {
}
