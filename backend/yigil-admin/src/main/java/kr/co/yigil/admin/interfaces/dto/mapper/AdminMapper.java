package kr.co.yigil.admin.interfaces.dto.mapper;

import kr.co.yigil.admin.domain.admin.AdminCommand;
import kr.co.yigil.admin.domain.admin.AdminInfo;
import kr.co.yigil.admin.interfaces.dto.request.LoginRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    AdminCommand.LoginRequest toCommand(LoginRequest loginRequest);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "profileUrl", source = "profileUrl")
    AdminInfoResponse toResponse(AdminInfo.AdminInfoResponse info);

}
