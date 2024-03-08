package kr.co.yigil.admin.interfaces.dto.mapper;


import java.time.format.DateTimeFormatter;
import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpCommand;
import kr.co.yigil.admin.interfaces.dto.AdminSignUpInfoDto;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignupRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminSignUpsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface AdminSignupMapper {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mapping(target = "email", source = "email")
    @Mapping(target = "nickname", source = "nickname")
    AdminSignUpCommand.AdminSignUpRequest toCommand(AdminSignupRequest adminSignupRequest);

    default AdminSignUpsResponse toResponse(Page<AdminSignUp> adminSignUps) {
        Page<AdminSignUpInfoDto> signUpInfoDtoPage = adminSignUps.map(this::toAdminSignUpInfoDto);
        return new AdminSignUpsResponse(signUpInfoDtoPage);
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "nickname", source = "nickname")
    @Mapping(target = "requestDatetime", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
//    @Mapping(target = "requestDateTime", expression = "java(formatter.format(adminSignUp.getCreatedAt()))")
    AdminSignUpInfoDto toAdminSignUpInfoDto(AdminSignUp adminSignUp);



}
