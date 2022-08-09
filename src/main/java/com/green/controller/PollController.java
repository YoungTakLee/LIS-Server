package com.green.controller;

import com.green.service.PollService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/poll")
public class PollController {
    private final PollService pollService;

    @PostMapping
    public ResponseEntity<?> registPoll(@RequestBody Map<String, Object> paramMap) {
        try {
            pollService.insertPoll();
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("message", e);
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PostMapping(value = "/delete")
    public ResponseEntity<?> deletePoll(@RequestBody Map<String, Object> paramMap) {
        try {

            return new ResponseEntity<>(pollService.deletePoll(paramMap), HttpStatus.OK);

        } catch (Exception e) {
            log.error("message", e);
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/index")
    public ResponseEntity<?> getPollIndex(@RequestParam Map<String, Object> paramMap) {
        try {

            return new ResponseEntity<>(pollService.getPollIndex(paramMap), HttpStatus.OK);

        } catch (Exception e) {
            log.error("message", e);
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/indexForClient")
    public ResponseEntity<?> getPollIndexForClient(@RequestParam Map<String, Object> paramMap) {
        try {

            return new ResponseEntity<>(pollService.getPollIndexForClient(paramMap), HttpStatus.OK);

        } catch (Exception e) {
            log.error("message", e);
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/indexForClient_V2")
    public ResponseEntity<?> getPollIndexForClientV2(@RequestParam(required = false) Map<String, Object> paramMap) {
        try {
            return new ResponseEntity<>(pollService.getPollIndexForClientV2(paramMap), HttpStatus.OK);

        } catch (Exception e) {
            log.error("message : {}", e.getMessage());
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/detail")
    public ResponseEntity<?> getPollDetail(@RequestParam Map<String, Object> paramMap) {
        try {

            return new ResponseEntity<>(pollService.getPollDetail(paramMap), HttpStatus.OK);

        } catch (Exception e) {
            log.error("message", e);
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{master_id}")
    public ResponseEntity<?> getPollDetail(@PathVariable("master_id") String masterId) {
        try {

            return new ResponseEntity<>(pollService.getPollDetail(masterId), HttpStatus.OK);

        } catch (Exception e) {
            log.error("error message : {}", e.getMessage());
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/submit")
    public ResponseEntity<?> submitPoll(@RequestBody Map<String, Object> paramMap) {
        try {

            return new ResponseEntity<>(pollService.submitPollResult(paramMap), HttpStatus.OK);

        } catch (Exception e) {
            log.error("message", e);
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/result")
    public ResponseEntity<?> getResult(@RequestParam Map<String, Object> paramMap) {
        try {
            return new ResponseEntity<>(pollService.getPollResult(paramMap), HttpStatus.OK);

        } catch (Exception e) {
            log.error("message", e);
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/useYn")
    public ResponseEntity<?> modifyUserYn(@RequestBody Map<String, Object> paramMap) {
        try {
            return new ResponseEntity<>(pollService.modifyUseYn(paramMap), HttpStatus.OK);

        } catch (Exception e) {
            log.error("message", e);
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
