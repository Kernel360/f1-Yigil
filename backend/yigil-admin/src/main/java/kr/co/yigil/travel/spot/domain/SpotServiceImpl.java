package kr.co.yigil.travel.spot.domain;

import kr.co.yigil.comment.domain.CommentReader;
import kr.co.yigil.favor.domain.FavorReader;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotDetailInfo;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotListUnit;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotPageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {

    private final SpotReader spotReader;
    private final SpotStore spotStore;
    private final FavorReader favorReader;
    private final CommentReader commentReader;


    @Override
    @Transactional(readOnly = true)
    public SpotPageInfo getSpots(Pageable pageable) {
        var pageSpots = spotReader.getSpots(pageable);

        var spotList = pageSpots.getContent().stream().map(
            spot -> {
                var spotAdditionalInfo = getAdditionalInfo(spot.getId());
                return new SpotListUnit(spot, spotAdditionalInfo);
            }
        ).toList();

        return new SpotPageInfo(spotList, pageSpots.getPageable(), pageSpots.getTotalElements());
    }

    private SpotInfoDto.SpotAdditionalInfo getAdditionalInfo(Long id) {
        int favorCount = favorReader.getFavorCount(id);
        int commentCount = commentReader.getCommentCount(id);
        return new SpotInfoDto.SpotAdditionalInfo(favorCount, commentCount);
    }

    @Override
    @Transactional(readOnly = true)
    public SpotDetailInfo getSpot(Long spotId) {
        Spot spot = spotReader.getSpot(spotId);
        var spotAdditionalInfo = getAdditionalInfo(spotId);
        return new SpotDetailInfo(spot, spotAdditionalInfo);

    }

    @Override
    @Transactional
    public Long deleteSpot(Long spotId) {
        Spot spot = spotReader.getSpot(spotId);
        spotStore.deleteSpot(spot);
        return spot.getMember().getId();
    }
}
