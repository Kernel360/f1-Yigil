package kr.co.yigil.login.interfaces.dto.mapper;

import javax.annotation.processing.Generated;
import kr.co.yigil.login.domain.LoginCommand;
import kr.co.yigil.login.interfaces.dto.request.LoginRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-11T15:27:23+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class LoginMapperImpl implements LoginMapper {

    @Override
    public LoginCommand.LoginRequest toCommandLoginRequest(LoginRequest loginRequest) {
        if ( loginRequest == null ) {
            return null;
        }

        LoginCommand.LoginRequest.LoginRequestBuilder loginRequest1 = LoginCommand.LoginRequest.builder();

        loginRequest1.id( loginRequest.getId() );
        loginRequest1.nickname( loginRequest.getNickname() );
        loginRequest1.profileImageUrl( loginRequest.getProfileImageUrl() );
        loginRequest1.email( loginRequest.getEmail() );
        loginRequest1.provider( loginRequest.getProvider() );

        return loginRequest1.build();
    }
}
