package io.project.wolfgym.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WorkoutTemplateInUseException extends Exception {
    public WorkoutTemplateInUseException(String message) {
        super(message);
    }
}
