package LuQ.Music_hall.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record NewOwnerDTO(
        @NotEmpty(message = "Nome obbligatorio")
        String nome,
        @NotEmpty(message = "Cognome obbligatorio")
        String cognome,
        @NotEmpty(message = "Email obbligatoria")
        String email,
        @NotEmpty(message = "Password obbligatoria")
        String password,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataDiNascita
) {
}
