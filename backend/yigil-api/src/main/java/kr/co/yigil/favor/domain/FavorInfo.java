package kr.co.yigil.favor.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

public class FavorInfo {


    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class AddFavorResponse {
        private String message;
        public AddFavorResponse(String message) {
            this.message = message;
        }
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class DeleteFavorResponse{
        private String message;
        public DeleteFavorResponse(String message) {
            this.message = message;
        }
    }

}
