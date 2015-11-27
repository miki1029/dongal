package com.dongal.api.response;

import com.dongal.api.domain.Category;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miki
 */
@Data
public class SettingsData implements Serializable {

    public SettingsData(List<Category> userCategories, List<Category> officialCategories, List<Category> dyeonCategories) {
        official = setCategoryDatas(userCategories, officialCategories);
        dyeon = setCategoryDatas(userCategories, dyeonCategories);
    }

    private List<CategoryData> setCategoryDatas(List<Category> userCategories, List<Category> categories) {
        List<CategoryData> categoryDatas = new ArrayList<>();
        for (Category category : categories) {
            CategoryData categoryData = new CategoryData();
            categoryData.categoryIdx = category.getIdx();
            categoryData.name = category.getName();
            if (userCategories.contains(category)) {
                categoryData.isAble = true;
            } else {
                categoryData.isAble = false;
            }
            categoryDatas.add(categoryData);
        }
        return categoryDatas;
    }


    private List<CategoryData> official;
    private List<CategoryData> dyeon;

    private class CategoryData {
        @Getter private Long categoryIdx;
        @Getter private String name;
        private boolean isAble;

        public boolean getIsAble() {
            return isAble;
        }
    }
}
