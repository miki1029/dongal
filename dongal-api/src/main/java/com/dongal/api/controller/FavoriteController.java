package com.dongal.api.controller;

import com.dongal.api.service.FavoriteService;
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
@RequestMapping("/favorite")
public class FavoriteController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteController.class);

    @Autowired
    private FavoriteService favoriteService;

    @RequestMapping(value = "/addFavoriteToUser", method = RequestMethod.GET)
    public ResponseEntity<String> addFavoriteToUser(@RequestParam Long userIdx, @RequestParam Long subscriptionIdx) {
        LOGGER.info("addFavoriteToUser(userIdx=" + userIdx + ",subscriptionIdx=" + subscriptionIdx + ")");

        ResponseEntity<String> entity = null;
        try {
            favoriteService.addFavoriteToUser(userIdx, subscriptionIdx);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/delFavoriteFromUser", method = RequestMethod.GET)
    public ResponseEntity<String> delFavoriteFromUser(@RequestParam Long userIdx, @RequestParam Long subscriptionIdx) {
        LOGGER.info("delFavoriteFromUser(userIdx=" + userIdx + ",subscriptionIdx=" + subscriptionIdx + ")");

        ResponseEntity<String> entity = null;
        try {
            favoriteService.delFavoriteFromUser(userIdx, subscriptionIdx);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
