package io.project.wolfgym.customException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().stream()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        var response = new ErrorResponse("Ошибка валидации", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);

    }


}
