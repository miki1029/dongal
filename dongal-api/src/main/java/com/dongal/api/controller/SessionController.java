package com.dongal.api.controller;

import com.dongal.api.domain.User;
import com.dongal.api.service.SessionService;
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
@RequestMapping("/session")
public class SessionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        LOGGER.info("login(email=" + email + ",password=" + password + ")");

        ResponseEntity<User> entity = null;
        User user = null;
        try {
            user = sessionService.login(email, password);
            entity = new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<String> logout(@RequestParam Long userIdx) {
        LOGGER.info("logout(userIdx=" + userIdx + ")");

        ResponseEntity<String> entity = null;
        try {
            sessionService.logout(userIdx);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public ResponseEntity<User> join(@RequestParam String email, @RequestParam String password,
                                     @RequestParam String name, @RequestParam(required = false) String deviceKey) {
        LOGGER.info("join(email=" + email + ",password=" + password + ",name=" + name + ", deviceKey=" + deviceKey + ")");

        ResponseEntity<User> entity = null;
        User user = null;
        try {
            user = sessionService.join(email, password, name, deviceKey);
            entity = new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/verifyDGU", method = RequestMethod.GET)
    public ResponseEntity<String> verifyDGU(@RequestParam Long userIdx) {
        LOGGER.info("verifyDGU(userIdx=" + userIdx + ")");

        ResponseEntity<String> entity = null;
        try {
            sessionService.verifyDGU(userIdx);
            entity = new ResponseEntity<String>("<script>" +
                    "alert(\"It has been certified.\");" +
                    "window.open(\"about:blank\",\"_self\").close();" +
                    "</script>", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/updateLoginTime", method = RequestMethod.GET)
    public ResponseEntity<String> updateLoginTime(@RequestParam Long userIdx) {
        LOGGER.info("updateLoginTime(userIdx=" + userIdx + ")");

        ResponseEntity<String> entity = null;
        try {
            sessionService.updateLoginTime(userIdx);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

}
