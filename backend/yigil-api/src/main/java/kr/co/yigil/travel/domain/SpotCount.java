package kr.co.yigil.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash("spotCount")
public class SpotCount {
    @Id
    private Long placeId;

    private int spotCount;

    public void incrementSpotCount() {
        spotCount++;
    }
    public void decrementSpotCount() {
        spotCount--;
    }

}
