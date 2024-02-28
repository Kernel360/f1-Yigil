package kr.co.yigil.favor.intefaces.dto.mapper;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class FavorDto {
    @Getter
    @Builder
    @ToString
    public class AddFavorResponse {
        private String message;
    }

    @Getter
    @Builder
    @ToString
    public class DeleteFavorResponse {
        private String message;
    }


}
