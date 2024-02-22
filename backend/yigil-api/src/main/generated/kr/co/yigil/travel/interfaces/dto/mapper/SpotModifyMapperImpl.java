package kr.co.yigil.travel.interfaces.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.travel.domain.spot.SpotCommand;
import kr.co.yigil.travel.interfaces.dto.request.SpotUpdateRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-22T16:09:55+0900",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class SpotModifyMapperImpl implements SpotModifyMapper {

    @Override
    public SpotCommand.ModifySpotRequest toModifySpotRequest(SpotUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        SpotCommand.ModifySpotRequest.ModifySpotRequestBuilder modifySpotRequest = SpotCommand.ModifySpotRequest.builder();

        modifySpotRequest.id( request.getId() );
        modifySpotRequest.rate( request.getRate() );
        modifySpotRequest.description( request.getDescription() );
        modifySpotRequest.originalImages( originalSpotImageListToOriginalSpotImageList( request.getOriginalSpotImages() ) );
        modifySpotRequest.updatedImages( updateSpotImageListToUpdateSpotImageList( request.getUpdateSpotImages() ) );

        return modifySpotRequest.build();
    }

    protected SpotCommand.OriginalSpotImage originalSpotImageToOriginalSpotImage(SpotUpdateRequest.OriginalSpotImage originalSpotImage) {
        if ( originalSpotImage == null ) {
            return null;
        }

        SpotCommand.OriginalSpotImage.OriginalSpotImageBuilder originalSpotImage1 = SpotCommand.OriginalSpotImage.builder();

        originalSpotImage1.imageUrl( originalSpotImage.getImageUrl() );
        originalSpotImage1.index( originalSpotImage.getIndex() );

        return originalSpotImage1.build();
    }

    protected List<SpotCommand.OriginalSpotImage> originalSpotImageListToOriginalSpotImageList(List<SpotUpdateRequest.OriginalSpotImage> list) {
        if ( list == null ) {
            return null;
        }

        List<SpotCommand.OriginalSpotImage> list1 = new ArrayList<SpotCommand.OriginalSpotImage>( list.size() );
        for ( SpotUpdateRequest.OriginalSpotImage originalSpotImage : list ) {
            list1.add( originalSpotImageToOriginalSpotImage( originalSpotImage ) );
        }

        return list1;
    }

    protected SpotCommand.UpdateSpotImage updateSpotImageToUpdateSpotImage(SpotUpdateRequest.UpdateSpotImage updateSpotImage) {
        if ( updateSpotImage == null ) {
            return null;
        }

        SpotCommand.UpdateSpotImage.UpdateSpotImageBuilder updateSpotImage1 = SpotCommand.UpdateSpotImage.builder();

        updateSpotImage1.imageFile( updateSpotImage.getImageFile() );
        updateSpotImage1.index( updateSpotImage.getIndex() );

        return updateSpotImage1.build();
    }

    protected List<SpotCommand.UpdateSpotImage> updateSpotImageListToUpdateSpotImageList(List<SpotUpdateRequest.UpdateSpotImage> list) {
        if ( list == null ) {
            return null;
        }

        List<SpotCommand.UpdateSpotImage> list1 = new ArrayList<SpotCommand.UpdateSpotImage>( list.size() );
        for ( SpotUpdateRequest.UpdateSpotImage updateSpotImage : list ) {
            list1.add( updateSpotImageToUpdateSpotImage( updateSpotImage ) );
        }

        return list1;
    }
}
