package kr.co.yigil.admin.infrastructure.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.admin.domain.admin.Admin;
import kr.co.yigil.global.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminReaderImplTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminReaderImpl adminReader;

    @DisplayName("existsByEmailOrNickname 메서드가 존재하는 이메일과 닉네임을 잘 반환하는지")
    @Test
    void existsByEmailOrNickname_ShouldReturnTrue_WhenEmailAndNicknameExist() {
        String email = "test@test.com";
        String nickname = "test";

        when(adminRepository.existsByEmailOrNickname(email, nickname)).thenReturn(true);

        boolean result = adminReader.existsByEmailOrNickname(email, nickname);

        assertTrue(result);
        verify(adminRepository).existsByEmailOrNickname(email, nickname);
    }

    @DisplayName("getAdminByEmail 메서드가 Admin을 잘 반환하는지")
    @Test
    void getAdminByEmail_ShouldReturnAdmin() {
        String email = "test@test.com";
        Admin admin = mock(Admin.class);

        when(adminRepository.findByEmail(email)).thenReturn(Optional.of(admin));

        Admin result = adminReader.getAdminByEmail(email);

        assertEquals(admin, result);
        verify(adminRepository).findByEmail(email);
    }

    @DisplayName("getAdminByEmail 메서드가 Admin이 없을 때 예외를 잘 발생시키는지")
    @Test
    void getAdminByEmail_ShouldThrowException_WhenAdminNotFound() {
        String email = "test@test.com";

        when(adminRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> adminReader.getAdminByEmail(email));
        verify(adminRepository).findByEmail(email);
    }

}