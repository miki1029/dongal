package com.dongal.api.response;

import com.dongal.api.domain.Category;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miki
 */
@Data
public class SettingsData {

    public SettingsData(List<Category> topCategories, List<Category> midCategories,
            List<Category> userCategories, List<Category> officialCategories, List<Category> dyeonCategories) {
        top = setSuperCategoryDatas(topCategories);
        mid = setSuperCategoryDatas(midCategories);

        official = setCategoryDatas(userCategories, officialCategories);
        dyeon = setCategoryDatas(userCategories, dyeonCategories);
    }

    private List<TopCategoryData> setSuperCategoryDatas(List<Category> superCategories) {
        List<TopCategoryData> superCategoryDatas = new ArrayList<>();
        for (Category category : superCategories) {
            TopCategoryData superCategoryData = new TopCategoryData();
            superCategoryData.categoryIdx = category.getIdx();
            superCategoryData.name = category.getName();
            superCategoryDatas.add(superCategoryData);
        }
        return superCategoryDatas;
    }
    private List<CategoryData> setCategoryDatas(List<Category> userCategories, List<Category> categories) {
        List<CategoryData> categoryDatas = new ArrayList<>();
        for (Category category : categories) {
            CategoryData categoryData = new CategoryData();
            categoryData.categoryIdx = category.getIdx();
            categoryData.name = category.getName();
            categoryData.topCategoryIdx = category.getTopCategory().getIdx();
            categoryData.midCategoryIdx = category.getMidCategory().getIdx();
            if (userCategories.contains(category)) {
                categoryData.isAble = true;
            } else {
                categoryData.isAble = false;
            }
            categoryDatas.add(categoryData);
        }
        return categoryDatas;
    }

    private List<TopCategoryData> top;
    private List<TopCategoryData> mid;

    private List<CategoryData> official;
    private List<CategoryData> dyeon;

    private class TopCategoryData {
        @Getter private Long categoryIdx;
        @Getter private String name;
    }

    private class CategoryData {
        @Getter private Long categoryIdx;
        @Getter private String name;
        @Getter private Long topCategoryIdx;
        @Getter private Long midCategoryIdx;
        private boolean isAble;

        public boolean getIsAble() {
            return isAble;
        }
    }
}
