package com.dongal.api.controller;

import com.dongal.api.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Freddi
 */
@Controller
@RequestMapping(value = "/subscription", method = RequestMethod.POST)
public class SubscriptionController {
    @RequestMapping(method = RequestMethod.POST)
    public Response getUserSubscription(int userIndex, int recentDay) {
        return null;
    }
}
