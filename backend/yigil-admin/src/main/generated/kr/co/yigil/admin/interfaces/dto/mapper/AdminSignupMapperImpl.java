package kr.co.yigil.admin.interfaces.dto.mapper;

import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpCommand;
import kr.co.yigil.admin.interfaces.dto.AdminSignUpInfoDto;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignupRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-06T15:40:25+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class AdminSignupMapperImpl implements AdminSignupMapper {

    private final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168 = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );

    @Override
    public AdminSignUpCommand.AdminSignUpRequest toCommand(AdminSignupRequest adminSignupRequest) {
        if ( adminSignupRequest == null ) {
            return null;
        }

        AdminSignUpCommand.AdminSignUpRequest.AdminSignUpRequestBuilder adminSignUpRequest = AdminSignUpCommand.AdminSignUpRequest.builder();

        adminSignUpRequest.email( adminSignupRequest.getEmail() );
        adminSignUpRequest.nickname( adminSignupRequest.getNickname() );

        return adminSignUpRequest.build();
    }

    @Override
    public AdminSignUpInfoDto toAdminSignUpInfoDto(AdminSignUp adminSignUp) {
        if ( adminSignUp == null ) {
            return null;
        }

        AdminSignUpInfoDto adminSignUpInfoDto = new AdminSignUpInfoDto();

        adminSignUpInfoDto.setId( adminSignUp.getId() );
        adminSignUpInfoDto.setEmail( adminSignUp.getEmail() );
        adminSignUpInfoDto.setNickname( adminSignUp.getNickname() );
        if ( adminSignUp.getCreatedAt() != null ) {
            adminSignUpInfoDto.setRequestDatetime( dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168.format( adminSignUp.getCreatedAt() ) );
        }

        return adminSignUpInfoDto;
    }
}
