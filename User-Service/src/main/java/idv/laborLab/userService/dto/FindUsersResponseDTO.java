package idv.laborLab.userService.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FindUsersResponseDTO(List<UserDTO> userDTOList, long totalElements, int totalPages) {
}
