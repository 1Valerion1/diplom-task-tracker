package edu.pet.tasktrackerapi.exception_handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private String message;
}
