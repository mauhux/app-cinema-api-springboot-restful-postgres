package dev.mauhux.apps.cinema.business.api.exceptions;

public record ValidationError(String field, String message) {}