package com.dongal.api.controller;

import com.dongal.api.response.ListData;
import com.dongal.api.response.SettingsData;
import com.dongal.api.service.RestViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

/**
 * @author miki
 */
@RestController
@RequestMapping("/view")
public class RestViewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestViewController.class);

    @Autowired
    private RestViewService restViewService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ResponseEntity<ListData> home(@RequestParam Long userIdx, @RequestParam(required = false) Long timestamp) {
        Date lastLoginTime = null;
        if (timestamp == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            lastLoginTime = calendar.getTime();
        }
        else
            lastLoginTime = new Date(timestamp);
        LOGGER.info("home(userIdx=" + userIdx + ",timestamp=" + timestamp + ",lastLoginTime=" + lastLoginTime + ")");

        ResponseEntity<ListData> entity = null;
        ListData listData = null;
        try {
            listData = restViewService.home(userIdx, lastLoginTime);
            entity = new ResponseEntity<ListData>(listData, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<ListData>(listData, HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<ListData> list(@RequestParam Long userIdx) {
        LOGGER.info("list(userIdx=" + userIdx + ")");

        ResponseEntity<ListData> entity = null;
        ListData listData = null;
        try {
            listData = restViewService.list(userIdx);
            entity = new ResponseEntity<ListData>(listData, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<ListData>(listData, HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    public ResponseEntity<ListData> favorite(@RequestParam Long userIdx) {
        LOGGER.info("favorite(userIdx=" + userIdx + ")");

        ResponseEntity<ListData> entity = null;
        ListData listData = null;
        try {
            listData = restViewService.favorite(userIdx);
            entity = new ResponseEntity<ListData>(listData, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<ListData>(listData, HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ResponseEntity<SettingsData> settings(@RequestParam Long userIdx) {
        LOGGER.info("settings(userIdx=" + userIdx + ")");

        ResponseEntity<SettingsData> entity = null;
        SettingsData listData = null;
        try {
            listData = restViewService.settings(userIdx);
            entity = new ResponseEntity<SettingsData>(listData, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<SettingsData>(listData, HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

}
