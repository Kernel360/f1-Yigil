package kr.co.yigil.region.domain;

import io.micrometer.common.util.StringUtils;
import java.util.List;
import kr.co.yigil.member.Member;
import lombok.Getter;
import lombok.ToString;

public class RegionInfo {

    @Getter
    @ToString
    public static class Main {
        private final String name;
        private final Long id;

        public Main(Region region) {
            id = region.getId();
            name = StringUtils.isEmpty(region.getName2()) ? region.getName1() : region.getName1() + " | " + region.getName2();
        }
    }

    @Getter
    @ToString
    public static class Category {
        private final String name;
        private final List<CategoryItem> items;

        public Category(RegionCategory category, Member member) {
            name = category.getName();
            this.items = category.getRegions().stream()
                .map(region -> new CategoryItem(region, member.isFavoriteRegion(region)))
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
            name = StringUtils.isEmpty(region.getName2()) ? region.getName1() : region.getName1() + " | " + region.getName2();
            this.selected = selected;
        }
    }

}
