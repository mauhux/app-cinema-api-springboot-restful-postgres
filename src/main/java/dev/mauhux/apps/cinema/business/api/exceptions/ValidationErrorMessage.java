package dev.mauhux.apps.cinema.business.api.exceptions;

import java.util.List;

public record ValidationErrorMessage(String message, List<ValidationError> errors) {
}
