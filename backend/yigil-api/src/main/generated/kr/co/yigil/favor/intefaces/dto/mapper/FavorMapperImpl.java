package kr.co.yigil.favor.intefaces.dto.mapper;

import javax.annotation.processing.Generated;
import kr.co.yigil.favor.domain.FavorInfo;
import kr.co.yigil.favor.intefaces.dto.FavorDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-29T18:10:40+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
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
