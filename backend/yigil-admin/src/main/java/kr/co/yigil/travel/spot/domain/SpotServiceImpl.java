package kr.co.yigil.travel.spot.domain;

import kr.co.yigil.comment.domain.CommentReader;
import kr.co.yigil.favor.domain.FavorReader;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotDetailInfo;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotListUnit;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotPageInfo;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
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
        Page<Spot> pageSpots = spotReader.getSpots(pageable);

        var spotList = pageSpots.getContent().stream().map(this::getSpotListUnit).toList();

        return new SpotPageInfo(spotList, pageSpots.getPageable(), pageSpots.getTotalElements());
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

    private SpotInfoDto.SpotAdditionalInfo getAdditionalInfo(Long id) {
        int favorCount = favorReader.getFavorCount(id);
        int commentCount = commentReader.getCommentCount(id);
        return new SpotInfoDto.SpotAdditionalInfo(favorCount, commentCount);
    }

    @NotNull
    private SpotListUnit getSpotListUnit(Spot spot) {
        var spotAdditionalInfo = getAdditionalInfo(spot.getId());
        return new SpotListUnit(spot, spotAdditionalInfo);
    }
}
