package id.backend.todo_list.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralResponseDto {
    private String responseCode;
    private String responseMessage;
    private Object responseData;
}
