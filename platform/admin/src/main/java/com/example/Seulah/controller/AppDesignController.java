package com.example.Seulah.controller;

import com.example.Seulah.request.AppDesignRequest;
import com.example.Seulah.request.MessageResponse;
import com.example.Seulah.service.AppDesignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/appDesign")
public class AppDesignController {
    private final AppDesignService appDesignService;

    public AppDesignController(AppDesignService appDesignService) {
        this.appDesignService = appDesignService;
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> saveAppDesign(@RequestBody AppDesignRequest appDesignRequest) {
        log.info("Saving App Design {}", appDesignRequest);
        return appDesignService.saveAppDesign(appDesignRequest);
    }


    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getAppDesignById(@RequestParam Long id) {
        log.info("Get App Design By {}", id);
        return appDesignService.getAppDesignById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getAll() {
        log.info("Get All App Design By");
        return appDesignService.getAll();
    }

    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam Long id) {
        log.info("Delete App Design By {}", id);
        return appDesignService.deleteById(id);
    }

    @PutMapping(value = "/updateById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> updateById(@RequestParam Long id,@RequestBody AppDesignRequest appDesignRequest) {
        log.info("Update App Design {} By Id {}",appDesignRequest, id);
        return appDesignService.updateById(id,appDesignRequest);
    }
}
