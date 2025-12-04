package nordcode.Focus_Timer.service;


import nordcode.Focus_Timer.model.FocusSession;
import nordcode.Focus_Timer.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    }

}
