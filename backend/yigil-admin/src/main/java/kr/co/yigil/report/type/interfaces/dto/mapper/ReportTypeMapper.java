package kr.co.yigil.report.type.interfaces.dto.mapper;

import java.util.List;
import kr.co.yigil.report.type.domain.ReportTypeCommand.CreateReportType;
import kr.co.yigil.report.type.domain.ReportType;
import kr.co.yigil.report.type.interfaces.dto.ReportTypeDto.TypeListInfo;
import kr.co.yigil.report.type.interfaces.dto.ReportTypeDto.ReportTypeListResponse;
import kr.co.yigil.report.type.interfaces.dto.ReportTypeDto.ReportTypeCreateRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ReportTypeMapper {

    default ReportTypeListResponse toDto(List<ReportType> reportTypes) {

        List<TypeListInfo> typeListInfos =
            reportTypes.stream().map(this::toDto).toList();
        return new ReportTypeListResponse(typeListInfos);
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TypeListInfo toDto(ReportType type);

    CreateReportType toCommand(ReportTypeCreateRequest request);
}
