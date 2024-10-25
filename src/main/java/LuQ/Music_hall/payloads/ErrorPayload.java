package LuQ.Music_hall.payloads;

import org.aspectj.bridge.IMessage;

import java.time.LocalDateTime;

public record ErrorPayload(
        String message,
        LocalDateTime timestamp
) {
}
