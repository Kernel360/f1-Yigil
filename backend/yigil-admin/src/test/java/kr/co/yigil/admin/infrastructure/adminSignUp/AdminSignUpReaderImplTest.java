package kr.co.yigil.admin.infrastructure.adminSignUp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUp;
import kr.co.yigil.global.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@ExtendWith(MockitoExtension.class)
class AdminSignUpReaderImplTest {

    @Mock
    private AdminSignUpRepository adminSignUpRepository;

    @InjectMocks
    private AdminSignUpReaderImpl adminSignUpReader;

    @DisplayName("findAll 메서드가 AdminSignUp 페이지를 잘 반환하는지")
    @Test
    void findAll_ShouldReturnPageOfAdminSignUp() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<AdminSignUp> expectedPage = mock(Page.class);

        when(adminSignUpRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<AdminSignUp> result = adminSignUpReader.findAll(pageable);

        assertEquals(expectedPage, result);
        verify(adminSignUpRepository).findAll(pageable);
    }

    @DisplayName("findById 메서드가 AdminSignUp을 잘 반환하는지")
    @Test
    void findById_ShouldReturnAdminSignUp() {
        Long id = 1L;
        AdminSignUp adminSignUp = mock(AdminSignUp.class);

        when(adminSignUpRepository.findById(id)).thenReturn(Optional.of(adminSignUp));

        AdminSignUp result = adminSignUpReader.findById(id);

        assertEquals(adminSignUp, result);
        verify(adminSignUpRepository).findById(id);
    }

    @DisplayName("findById 메서드가 AdminSignUp이 없을 때 예외를 잘 발생시키는지")
    @Test
    void findById_ShouldThrowException_WhenAdminSignUpNotFound() {
        Long id = 1L;

        when(adminSignUpRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> adminSignUpReader.findById(id));
        verify(adminSignUpRepository).findById(id);
    }
}