package idv.laborLab.userService.dto;

public record ResetUserPasswordDTO(long userId, String previousPassword, String newPassword) {
}
