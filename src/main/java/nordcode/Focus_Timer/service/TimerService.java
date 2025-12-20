package nordcode.Focus_Timer.service;


import nordcode.Focus_Timer.model.FocusSession;
import nordcode.Focus_Timer.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimerService {

    private final SessionRepository repository;

   public TimerService(SessionRepository repository){
       this.repository = repository;
   }

    public FocusSession startNewSession(String taskName){
        var session = new FocusSession(taskName, LocalDateTime.now());
        repository.save(session);
        System.out.println("Session started! " + taskName);
        return session;
    }


    public void stopSessionById(Long id){
       FocusSession session = repository.findById(id);
        repository.updateTimeAndStatus(session.getId(),LocalDateTime.now(), session.getDurationSeconds(), session.getStatus());
        System.out.println("Session ended! " + session.getTaskName());
    }

    public List<FocusSession> allSession(){
       return repository.findALL();
    }

    public void deleteSession(Long id){
       repository.deleteSessionById(id);
    }

}
