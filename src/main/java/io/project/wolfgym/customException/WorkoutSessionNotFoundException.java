package io.project.wolfgym.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkoutSessionNotFoundException extends Exception {
    public WorkoutSessionNotFoundException(String message) {
        super(message);
    }
}
