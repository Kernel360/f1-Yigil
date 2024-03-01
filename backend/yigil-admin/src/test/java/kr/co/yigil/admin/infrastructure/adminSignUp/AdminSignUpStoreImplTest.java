package kr.co.yigil.admin.infrastructure.adminSignUp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import kr.co.yigil.admin.domain.adminSignUp.AdminSignUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class AdminSignUpStoreImplTest {

    @Mock
    private AdminSignUpRepository adminSignUpRepository;

    @InjectMocks
    private AdminSignUpStoreImpl adminSignUpStore;

    @DisplayName("store 메서드가 AdminSignUp을 잘 저장하는지")
    @Test
    void store_ShouldStoreAdminSignUp() {
        AdminSignUp adminSignUp = mock(AdminSignUp.class);

        adminSignUpStore.store(adminSignUp);

        verify(adminSignUpRepository).save(adminSignUp);
    }

    @DisplayName("remove 메서드가 AdminSignUp을 잘 삭제하는지")
    @Test
    void remove_ShouldRemoveAdminSignUp() {
        AdminSignUp adminSignUp = mock(AdminSignUp.class);

        adminSignUpStore.remove(adminSignUp);

        verify(adminSignUpRepository).delete(adminSignUp);
    }
}