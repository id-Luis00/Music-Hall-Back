package LuQ.Music_hall.payloads;

import LuQ.Music_hall.enums.TipoMusicista;
import LuQ.Music_hall.security.ValidTipoMusicista;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


import java.time.LocalDate;

// TODO: validare il tutto

public record NewUtenteDTO(
        @NotEmpty(message = "nome obbligatorio")
        String nome,
        @NotEmpty(message = "cognome obbligatorio")
        String cognome,
        @NotEmpty(message = "username obbligatorio")
        String username,
        @Email @NotEmpty(message = "Email obbliogatoria")
        String email,
        @NotEmpty(message = "Password obbligatoria")
        String password,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataDiNascita,
        @ValidTipoMusicista
        String tipoMusicista
) {
}
