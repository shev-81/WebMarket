package com.webmarket.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The accumulator of error messages.
 */
@NoArgsConstructor
@Data
public class FieldsValidationError {
    private List<String> errorFieldsMessages;

    public FieldsValidationError(List<String> errorFieldsMessages) {
        this.errorFieldsMessages = errorFieldsMessages;
    }
}
