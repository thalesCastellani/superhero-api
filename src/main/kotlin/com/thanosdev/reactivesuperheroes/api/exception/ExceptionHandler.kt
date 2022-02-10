package com.thanosdev.reactivesuperheroes.api.exception

import com.thanosdev.reactivesuperheroes.api.dto.ErrorView
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import reactor.netty.http.server.HttpServerRequest

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(WebExchangeBindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationError(
        exception: MethodArgumentNotValidException,
        request: HttpServerRequest
    ): ErrorView {
        val errorMessage = HashMap<String, String?>()

        exception.bindingResult.fieldErrors.forEach {
            e -> errorMessage.put(e.field, e.defaultMessage)
        }

        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = errorMessage.toString(),
            path = request.path(),
            trace = "Something"
        )
    }

 /*   @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalServerError(
        exception: Exception,
        request: HttpServerRequest
    ): ErrorView =
        ErrorView(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.name,
            message = "Some message",
            path = request.path(),
            trace = "Something"
        )*/
}