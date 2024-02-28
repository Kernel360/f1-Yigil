package kr.co.yigil.admin.domain.adminSignUp;

public interface AdminSignUpStore {

    void store(AdminSignUp entity);

    void remove(AdminSignUp signUp);
}
