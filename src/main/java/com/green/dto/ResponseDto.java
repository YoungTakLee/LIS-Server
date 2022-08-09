package com.green.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.enums.ResponseStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto<T> {

    @JsonProperty("description")
    private String message;
    @JsonProperty("status_code")
    private int code;
    @JsonProperty("response_body")
    private T responseBody;

    public ResponseDto(ResponseStatus status) {
        this.message = status.getMessage();
        this.code = status.getCode();
    }

    public void setStatus(ResponseStatus status) {
        this.message = status.getMessage();
        this.code = status.getCode();
    }

}
