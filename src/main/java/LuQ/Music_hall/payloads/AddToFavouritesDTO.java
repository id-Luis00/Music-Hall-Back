package LuQ.Music_hall.payloads;

import java.util.UUID;

public record AddToFavouritesDTO(
        UUID idUtente,
        UUID idSala
) {
}
