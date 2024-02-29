package kr.co.yigil.admin.domain.adminSignUp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import kr.co.yigil.admin.infrastructure.adminSignUp.AdminPasswordGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AdminPasswordGeneratorTest {

    @DisplayName("생성된 비밀번호가 길이 제한을 만족하는지")
    @Test
    void generateRandomPassword_LengthCheck() {
        AdminPasswordGenerator generator = new AdminPasswordGenerator();
        String password = generator.generateRandomPassword();

        assertEquals(10, password.length());
    }


}
