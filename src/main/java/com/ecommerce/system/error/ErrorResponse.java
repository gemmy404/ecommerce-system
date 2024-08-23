package com.ecommerce.system.error;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
public class ErrorResponse {

    private Boolean success;

    private String timestamp;

    private String message;

    public ErrorResponse(String message) {
        this.success = Boolean.FALSE;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.message = message;
    }

}
