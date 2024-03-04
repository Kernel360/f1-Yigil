package kr.co.yigil.admin.interfaces.dto.mapper;

import javax.annotation.processing.Generated;
import kr.co.yigil.admin.domain.admin.AdminCommand;
import kr.co.yigil.admin.domain.admin.AdminInfo;
import kr.co.yigil.admin.interfaces.dto.request.LoginRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminInfoResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-03T21:05:09+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class AdminMapperImpl implements AdminMapper {

    @Override
    public AdminCommand.LoginRequest toCommand(LoginRequest loginRequest) {
        if ( loginRequest == null ) {
            return null;
        }

        AdminCommand.LoginRequest.LoginRequestBuilder loginRequest1 = AdminCommand.LoginRequest.builder();

        loginRequest1.email( loginRequest.getEmail() );
        loginRequest1.password( loginRequest.getPassword() );

        return loginRequest1.build();
    }

    @Override
    public AdminInfoResponse toResponse(AdminInfo.AdminInfoResponse info) {
        if ( info == null ) {
            return null;
        }

        AdminInfoResponse adminInfoResponse = new AdminInfoResponse();

        adminInfoResponse.setUsername( info.getUsername() );
        adminInfoResponse.setProfileUrl( info.getProfileUrl() );

        return adminInfoResponse;
    }
}
