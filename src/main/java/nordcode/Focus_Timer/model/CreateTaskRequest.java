package nordcode.Focus_Timer.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class CreateTaskRequest {

    @NotBlank(message = "Назва завдання не може бути порожньою")
    @Size(min = 3, max = 100, message = "Назва має бути від 3 до 100 символів")
    private String taskName;
    @Min(value = 1, message = "Ціль має бути мінімум 1 хвилина")
    private Long targetTime;

    public void setTaskName(String taskName, Long targetTime) {
        this.taskName = taskName;
        this.targetTime = targetTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public Long getTargetTime() {
        return targetTime;
    }
}
