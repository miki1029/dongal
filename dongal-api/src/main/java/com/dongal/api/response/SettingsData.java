package com.dongal.api.response;

import lombok.Data;

import java.util.List;

/**
 * @author miki
 */
@Data
public class SettingsData {
    private List<Category> categories;

    @Data
    private class Category {
        private String name;
        private boolean isAble;
    }
}
