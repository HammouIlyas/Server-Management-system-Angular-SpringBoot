package com.example.servermanagementsystem_backend.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import java.time.LocalDate;
import java.util.Map;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpResponse {
    protected LocalDate timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;
    protected String message;
    protected String developerMessage;
    protected Map<?,?> data;

}
