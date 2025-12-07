package nordcode.Focus_Timer.controller;

import nordcode.Focus_Timer.model.FocusSession;
import nordcode.Focus_Timer.service.TimerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimerController{

    private final TimerService timerService;

    public TimerController(TimerService timerService){
        this.timerService = timerService;
    }

@PostMapping
    public void createFocusSession(@RequestBody String taskName){
    timerService.startNewSession(taskName);
}

@GetMapping
    public List<FocusSession> findAll() {
        return timerService.allSession();
}

@PutMapping
    public void UpdateById(@RequestBody Long id){
         timerService.stopSessionById(id);
}

@DeleteMapping
    public void deleteSession(@RequestBody Long id){
        timerService.deleteSession(id);
}

}