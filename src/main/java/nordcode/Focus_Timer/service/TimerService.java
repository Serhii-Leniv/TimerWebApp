package nordcode.Focus_Timer.service;


import nordcode.Focus_Timer.model.FocusSession;
import nordcode.Focus_Timer.model.StatusEnum;
import nordcode.Focus_Timer.repository.SessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimerService {

    private final SessionRepository repository;

    public TimerService(SessionRepository repository) {
        this.repository = repository;
    }

    public FocusSession startNewSession(String taskName, Long targetTime) {
        if (repository.checkIfSessionRunning()) {
            throw new IllegalStateException("Complete first task to star new!");
        }
        var session = new FocusSession(taskName, LocalDateTime.now(), targetTime);
        repository.save(session);
        System.out.println("Session started! " + taskName);
        return session;
    }


    public void stopSessionById(Long id) {
        FocusSession session = repository.findById(id);
        if (session == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Сесію з таким ID не знайдено");
        }
        if(session.getStatus() == StatusEnum.COMPLETE || session.getStatus() == StatusEnum.CANCELLED){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Session already stopped");
        }

        var now = LocalDateTime.now();
        long duration = Duration.between(session.getStartTime(), now).toSeconds();
        session.setDurationSeconds(duration);
        var target = session.getTargetTime();

        if (target != null && duration > 0) {
            if (duration >= target) {
                session.setStatus(StatusEnum.COMPLETE);
            } else {
                session.setStatus(StatusEnum.CANCELLED);
            }
        } else {
            session.setStatus(StatusEnum.COMPLETE);
        }

        repository.updateTimeAndStatus(session.getId(), now, session.getDurationSeconds(), session.getStatus());
        System.out.println("Session ended! " + session.getTaskName());
    }

    public List<FocusSession> allSession() {
        return repository.findALL();
    }

    public void deleteSession(Long id) {
        repository.deleteSessionById(id);
    }

}
