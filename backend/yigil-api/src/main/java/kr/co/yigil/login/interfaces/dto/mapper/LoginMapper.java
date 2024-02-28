package kr.co.yigil.login.interfaces.dto.mapper;

import kr.co.yigil.login.domain.LoginCommand;
import kr.co.yigil.login.interfaces.dto.request.LoginRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "nickname", target = "nickname"),
            @Mapping(source = "profileImageUrl", target = "profileImageUrl"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "provider", target = "provider")
    })
    LoginCommand.LoginRequest toCommandLoginRequest(LoginRequest loginRequest);
}
