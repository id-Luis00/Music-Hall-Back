package LuQ.Music_hall.payloads;

import jakarta.validation.constraints.NotEmpty;

public record UtenteLoginDTO(
        @NotEmpty(message = "Email obbligatoria")
        String email,
        @NotEmpty(message = "Password obbligatoria")
        String password
) {
}
