package com.hustleknight.consumer.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handlerCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(e.errorCode.httpStatus)
            .body(ErrorResponse(e))
    }
}