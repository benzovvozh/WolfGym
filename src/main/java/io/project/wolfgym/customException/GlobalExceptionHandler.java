package io.project.wolfgym.customException;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> notFoundResponse(Exception e) {
        var response = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExerciseNotFoundException(ExerciseNotFoundException e) {
        return notFoundResponse(e);
    }

    @ExceptionHandler(WorkoutTemplateNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWorkoutTemplateNotFoundException(WorkoutTemplateNotFoundException e) {
        return notFoundResponse(e);
    }

    @ExceptionHandler(WorkoutSetNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWorkoutSetNotFoundException(WorkoutSetNotFoundException e) {
        return notFoundResponse(e);
    }

    @ExceptionHandler(WorkoutSessionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWorkoutSessionNotFoundException(WorkoutSessionNotFoundException e) {
        return notFoundResponse(e);
    }


}
