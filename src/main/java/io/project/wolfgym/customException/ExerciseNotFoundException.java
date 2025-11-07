package io.project.wolfgym.customException;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ExerciseNotFoundException extends Exception {

    public ExerciseNotFoundException(String message) {
        super(message);
    }

}
