package kr.co.yigil.admin.interfaces.dto.mapper;

import kr.co.yigil.admin.domain.admin.AdminCommand;
import kr.co.yigil.admin.domain.admin.AdminInfo;
import kr.co.yigil.admin.interfaces.dto.request.AdminUpdateRequest;
import kr.co.yigil.admin.interfaces.dto.request.LoginRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminDetailInfoResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    AdminCommand.LoginRequest toCommand(LoginRequest loginRequest);

    @Mapping(target = "nickname", source = "nickname")
    @Mapping(target = "profileImageFile", source = "profileImageFile")
    @Mapping(target = "password", source = "password")
    AdminCommand.AdminUpdateRequest toCommand(AdminUpdateRequest updateRequest);

    @Mapping(target = "nickname", source = "nickname")
    @Mapping(target = "profileUrl", source = "profileUrl")
    AdminInfoResponse toResponse(AdminInfo.AdminInfoResponse info);

    @Mapping(target = "nickname", source = "nickname")
    @Mapping(target = "profileUrl", source = "profileUrl")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    AdminDetailInfoResponse toResponse(AdminInfo.AdminDetailInfoResponse info);
}
