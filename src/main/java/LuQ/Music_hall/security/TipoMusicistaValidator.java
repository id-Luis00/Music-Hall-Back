package LuQ.Music_hall.security;

import LuQ.Music_hall.enums.TipoMusicista;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class TipoMusicistaValidator implements ConstraintValidator<ValidTipoMusicista, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && Arrays.stream(TipoMusicista.values())
                .anyMatch(enumValue -> enumValue.name().equals(value));
    }
}
