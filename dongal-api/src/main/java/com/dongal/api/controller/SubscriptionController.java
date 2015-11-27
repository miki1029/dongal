package com.dongal.api.controller;

import com.dongal.api.domain.Subscription;
import com.dongal.api.service.SubscriptionService;
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
import java.util.List;

/**
 * @author Freddi
 */
@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private SubscriptionService subscriptionService;

    @RequestMapping(value = "getUserSubscription", method = RequestMethod.GET)
    public ResponseEntity<List<Subscription>> getUserSubscription(@RequestParam Long userIdx,
                                                                  @RequestParam(required = false) Integer recentDay) {
        LOGGER.info("getUserSubscription(userIdx=" + userIdx + ",recentDay=" + recentDay + ")");

        ResponseEntity<List<Subscription>> entity = null;
        List<Subscription> subscriptions = null;
        try {
            if (recentDay == null) {
                subscriptions = subscriptionService.getUserSubscription(userIdx);
            } else {
                Date endTime = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(endTime);
                calendar.add(Calendar.DATE, -1 * recentDay);
                Date startTime = calendar.getTime();

                LOGGER.info("startTime=" + startTime + ",endTime=" + endTime);
                subscriptions = subscriptionService.getUserSubscription(userIdx, startTime, endTime);
            }
            entity = new ResponseEntity<List<Subscription>>(subscriptions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<List<Subscription>>(subscriptions, HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
