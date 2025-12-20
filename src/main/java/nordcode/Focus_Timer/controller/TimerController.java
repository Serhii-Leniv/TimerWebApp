package nordcode.Focus_Timer.controller;

import nordcode.Focus_Timer.model.CreateTaskRequest;
import nordcode.Focus_Timer.model.FocusSession;
import nordcode.Focus_Timer.service.TimerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/timer")
public class TimerController{

    private final TimerService timerService;

    public TimerController(TimerService timerService){
        this.timerService = timerService;
    }

@PostMapping("/create")
    public FocusSession createFocusSession(@RequestBody CreateTaskRequest request){
    return timerService.startNewSession(request.getTaskName());
}

@GetMapping
    public List<FocusSession> findAll() {
        return timerService.allSession();
}

@PutMapping("/stop{id}")
    public void UpdateById(@PathVariable Long id){
         timerService.stopSessionById(id);
}

@DeleteMapping("/delete{id}")
    public void deleteSession(@PathVariable Long id){
        timerService.deleteSession(id);
}

}