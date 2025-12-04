package nordcode.Focus_Timer.model;


import java.time.LocalDateTime;

public class FocusSession {

    private Long id;
    private String taskName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long durationSeconds;
    private StatusEnum status;

    public FocusSession() {}
    public FocusSession(String taskName, LocalDateTime startTime){
        this.taskName = taskName;
        this.startTime = startTime;
        this.status = StatusEnum.IN_PROGRESS;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTaskName() { return taskName; }
    public void setTaskName(String tskName) { this.taskName = taskName; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Long getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(Long durationSeconds) { this.durationSeconds = durationSeconds; }

    public StatusEnum getStatus() { return status; }
    public void setStatus(StatusEnum status) { this.status = status; }

    @Override
    public String toString() {
        return "FocusSession{id=" + id + ", task='" + taskName + "', status=" + status + "}";
    }
}