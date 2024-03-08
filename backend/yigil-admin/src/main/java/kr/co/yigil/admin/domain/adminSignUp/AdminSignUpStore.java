package kr.co.yigil.admin.domain.adminSignUp;

import kr.co.yigil.admin.domain.AdminSignUp;

public interface AdminSignUpStore {

    void store(AdminSignUp entity);

    void remove(AdminSignUp signUp);
}
