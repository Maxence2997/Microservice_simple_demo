package idv.laborLab.userService.dto;

public record UpdateUserInfoDTO(
        long userId,
        String indexString,
        UserIndex userIndex,
        String email,

        String phoneNumber) {
}
