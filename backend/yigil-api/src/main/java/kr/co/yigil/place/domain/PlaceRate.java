package kr.co.yigil.place.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash("placeRate")
public class PlaceRate {
    @Id
    private Long placeId;

    private double totalRate;

    public void addRate(double spotRate) {
        totalRate += spotRate;
    }

    public void removeRate(double spotRate) {
        totalRate -= spotRate;
    }

}
