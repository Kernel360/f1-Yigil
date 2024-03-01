package kr.co.yigil.region.domain;

import java.util.List;
import lombok.Getter;
import lombok.ToString;

public class RegionInfo {

    @Getter
    @ToString
    public static class Category {
        private final String name;
        private final List<CategoryItem> items;

        public Category(RegionCategory category) {
            name = category.getName();
            this.items = category.getRegions().stream()
                .map(region -> new CategoryItem(region, false))
                .toList();
        }
    }

    @Getter
    @ToString
    public static class CategoryItem {
        private final Long id;
        private final String name;
        private final boolean selected;

        public CategoryItem(Region region, boolean selected) {
            id = region.getId();
            name = region.getName1() + " | " + region.getName2();
            this.selected = selected;
        }
    }

}
