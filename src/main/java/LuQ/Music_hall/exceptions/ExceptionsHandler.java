package LuQ.Music_hall.exceptions;

import LuQ.Music_hall.payloads.ErrorPayload;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;



    @RestControllerAdvice
    public class ExceptionsHandler {
        @ExceptionHandler(BadRequestException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorPayload handleBadRequest(BadRequestException ex) {
            return new ErrorPayload(ex.getMessage(), LocalDateTime.now());
        }

        @ExceptionHandler(NotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ErrorPayload handleNotFound(NotFoundException ex) {
            return new ErrorPayload(ex.getMessage(), LocalDateTime.now());
        }

        @ExceptionHandler(UnauthorizedException.class)
        @ResponseStatus(HttpStatus.FORBIDDEN)
        public ErrorPayload handleForbidden(UnauthorizedException ex) {
            return new ErrorPayload(ex.getMessage(), LocalDateTime.now());
        }

        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public ErrorPayload handleException(Exception ex) {
            ex.printStackTrace();
            return new ErrorPayload("Ci scusiamo per il disagio", LocalDateTime.now());
        }
    }

