package id.backend.todo_list.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityRequestDto {
    private String name;
    private String description;
    private String startDate;
    private String endDate;
}
