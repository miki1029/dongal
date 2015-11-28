package com.dongal.api.controller;

import com.dongal.api.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Freddi
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/setUserCategories", method = RequestMethod.GET)
    public ResponseEntity<String> setUserCategories(@RequestParam Long userIdx, @RequestParam List<Long> categoryIdxes) {
        LOGGER.info("setUserCategories(userIdx=" + userIdx + ",categoryIdxes=" + categoryIdxes + ")");

        ResponseEntity<String> entity = null;
        try {
            categoryService.setUserCategories(userIdx, categoryIdxes);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "addCategoryToUser", method = RequestMethod.GET)
    public ResponseEntity<String> addCategoryToUser(@RequestParam Long userIdx, @RequestParam Long categoryIdx) {
        LOGGER.info("addCategoryToUser(userIdx=" + userIdx + ",categoryIdx=" + categoryIdx + ")");

        ResponseEntity<String> entity = null;
        try {
            categoryService.addCategoryToUser(userIdx, categoryIdx);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "delCategoryFromUser", method = RequestMethod.GET)
    public ResponseEntity<String> delCategoryFromUser(@RequestParam Long userIdx, @RequestParam Long categoryIdx) {
        LOGGER.info("delCategoryFromUser(userIdx=" + userIdx + ",categoryIdx=" + categoryIdx + ")");

        ResponseEntity<String> entity = null;
        try {
            categoryService.delCategoryFromUser(userIdx, categoryIdx);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
