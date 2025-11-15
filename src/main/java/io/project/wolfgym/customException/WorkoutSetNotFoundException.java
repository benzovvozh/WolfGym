package io.project.wolfgym.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkoutSetNotFoundException extends Exception {
    public WorkoutSetNotFoundException(String message) {
        super(message);
    }
}
