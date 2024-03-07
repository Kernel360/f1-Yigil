package kr.co.yigil.travel.spot.domain;

import kr.co.yigil.comment.domain.CommentReader;
import kr.co.yigil.favor.domain.FavorReader;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.spot.domain.AdminSpotInfoDto.AdminSpotDetailInfo;
import kr.co.yigil.travel.spot.domain.AdminSpotInfoDto.AdminSpotList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSpotServiceImpl implements AdminSpotService {

    private final SpotReader spotReader;
    private final SpotStore spotStore;
    private final FavorReader favorReader;
    private final CommentReader commentReader;


    @Override
    @Transactional(readOnly = true)
    public AdminSpotList getSpots(Pageable pageable) {
        var pageSpots = spotReader.getSpots(pageable);

        var spotList = pageSpots.getContent().stream().map(
            spot -> {
                var spotAdditionalInfo = getAdditionalInfo(spot.getId());
                return new AdminSpotInfoDto.SpotList(spot, spotAdditionalInfo);
            }
        ).toList();

        return new AdminSpotList(spotList, pageSpots.getPageable(), pageSpots.getTotalElements());
    }

    private AdminSpotInfoDto.SpotAdditionalInfo getAdditionalInfo(Long id) {
        int favorCount = favorReader.getFavorCount(id);
        int commentCount = commentReader.getCommentCount(id);
        return new AdminSpotInfoDto.SpotAdditionalInfo(favorCount, commentCount);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminSpotDetailInfo getSpot(Long spotId) {
        Spot spot = spotReader.getSpot(spotId);
        var spotAdditionalInfo = getAdditionalInfo(spotId);
        return new AdminSpotDetailInfo(spot, spotAdditionalInfo);

    }

    @Override
    @Transactional
    public Long deleteSpot(Long spotId) {
        Spot spot = spotReader.getSpot(spotId);
        spotStore.deleteSpot(spot);
        return spot.getMember().getId();
    }
}
