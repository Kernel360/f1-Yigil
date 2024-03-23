package kr.co.yigil.admin.infrastructure.admin;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.infrastructure.AdminRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminStoreImplTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminStoreImpl adminStore;


    @DisplayName("store 메서드가 Admin을 잘 저장하는지")
    @Test
    void store_ShouldStoreAdmin() {
        Admin admin = mock(Admin.class);
        adminStore.store(admin);

        verify(adminRepository).save(admin);
    }
}