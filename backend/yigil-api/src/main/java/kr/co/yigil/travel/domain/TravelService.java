package kr.co.yigil.travel.domain;

public interface TravelService {
    void changeOnPublic(Long travelId, Long memberId);
    void changeOnPrivate(Long travelId, Long memberId);
}
