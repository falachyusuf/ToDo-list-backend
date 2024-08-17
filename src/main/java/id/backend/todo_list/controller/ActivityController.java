package id.backend.todo_list.controller;

import id.backend.todo_list.model.GeneralResponseDto;
import id.backend.todo_list.model.request.ActivityRequestDto;
import id.backend.todo_list.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity<?> doCreateActivity(@RequestBody ActivityRequestDto inMsg) {
        Object response = activityService.doSaveActivity(inMsg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
