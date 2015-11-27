package com.dongal.api.controller;

import com.dongal.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Freddi
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public ResponseEntity<String> changePassword(@RequestParam Long userIdx, @RequestParam String password) {
        LOGGER.info("changePassword(userIdx=" + userIdx + ",password=" + password + ")");

        ResponseEntity<String> entity = null;
        try {
            userService.changePassword(userIdx, password);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/attachSNS", method = RequestMethod.GET)
    public ResponseEntity<String> attachSNS(@RequestParam Long userIdx, @RequestParam Long snsIdx, @RequestParam String snsValue) {
        LOGGER.info("attachSNS(userIdx=" + userIdx + ",snsIdx=" + snsIdx + ",snsValue" + snsValue + ")");

        ResponseEntity<String> entity = null;
        try {
            userService.attachSNS(userIdx, snsIdx, snsValue);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/detachSNS", method = RequestMethod.GET)
    public ResponseEntity<String> detachSNS(@RequestParam Long userIdx, @RequestParam Long snsIdx) {
        LOGGER.info("detachSNS(userIdx=" + userIdx + ",snsIdx=" + snsIdx + ")");

        ResponseEntity<String> entity = null;
        try {
            userService.detachSNS(userIdx, snsIdx);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
