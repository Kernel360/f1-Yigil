package kr.co.yigil.favor.interfaces.dto.mapper;

import javax.annotation.processing.Generated;
import kr.co.yigil.favor.domain.FavorInfo;
import kr.co.yigil.favor.interfaces.dto.FavorDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-04T18:48:06+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class FavorMapperImpl implements FavorMapper {

    @Override
    public FavorDto.AddFavorResponse of(FavorInfo.AddFavorResponse response) {
        if ( response == null ) {
            return null;
        }

        FavorDto.AddFavorResponse.AddFavorResponseBuilder addFavorResponse = FavorDto.AddFavorResponse.builder();

        addFavorResponse.message( response.getMessage() );

        return addFavorResponse.build();
    }

    @Override
    public FavorDto.DeleteFavorResponse of(FavorInfo.DeleteFavorResponse response) {
        if ( response == null ) {
            return null;
        }

        FavorDto.DeleteFavorResponse.DeleteFavorResponseBuilder deleteFavorResponse = FavorDto.DeleteFavorResponse.builder();

        deleteFavorResponse.message( response.getMessage() );

        return deleteFavorResponse.build();
    }
}
