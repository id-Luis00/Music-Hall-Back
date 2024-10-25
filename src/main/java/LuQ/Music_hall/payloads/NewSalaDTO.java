package LuQ.Music_hall.payloads;

import jakarta.validation.constraints.NotEmpty;

public record NewSalaDTO(
        @NotEmpty(message = "Nome della sala obbligatorio!")
        String nomeSala,
        @NotEmpty(message = "Regione della sala obbligatorio")
        String regione,
        @NotEmpty(message = "Comune della sala obbligatorio")
        String comune,
        @NotEmpty(message = "Indirizzo della sala obbligatorio")
        String indirizzo,
        @NotEmpty(message = "Capienza della sala obbligatoria")
        Integer capienza,

        String tipoSala, // TODO: da validare tramite annotazione custom

        @NotEmpty(message = "Prezzo orario della sala obbligatorio")
        Double prezzoOrario,

        String imageURL, // per ora non obbligatorio

        @NotEmpty(message = "Cap obbligatorio")
        String cap,
        @NotEmpty(message = "Telefono obbligatorio")
        String telefono,
        @NotEmpty(message = "Sito web obbligatorio")
        String website
) {
}
