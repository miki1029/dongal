package com.dongal.api.controller;

import com.dongal.api.config.DatabaseConfig;
import com.dongal.api.config.Initializer;
import com.dongal.api.config.WebAppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author miki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabaseConfig.class, WebAppConfig.class, Initializer.class })
@WebAppConfiguration
public class CategoryControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryControllerTest.class);

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        LOGGER.info("setUp.....");
    }

    @Test
    public void testSetUserCategories() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/category/setUserCategories?userIdx=2&categoryIdexes"));
    }
}