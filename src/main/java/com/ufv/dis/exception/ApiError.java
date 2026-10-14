package com.ufv.dis.exception;

/** Cuerpo coherente para todas las respuestas de error de la API. */
public record ApiError(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) {}
