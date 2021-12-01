package me.luraframework.auth;

import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FlowException.class)
    public String handleFlowException(FlowException e) {
        return e.getRule().toString();
    }
}
