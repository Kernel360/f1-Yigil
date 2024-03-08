package kr.co.yigil.travel.interfaces.dto.mapper;

import kr.co.yigil.travel.domain.TravelCommand;
import kr.co.yigil.travel.interfaces.dto.request.TravelsVisibilityChangeRequest;
import kr.co.yigil.travel.interfaces.dto.response.TravelsVisibilityChangeResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TravelMapper {


    TravelCommand.VisibilityChangeRequest of(TravelsVisibilityChangeRequest request);
    TravelsVisibilityChangeResponse of(String message);

}
