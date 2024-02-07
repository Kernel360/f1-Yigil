package kr.co.yigil.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminSignUpListRequest {
    private int page;
    private int dataCount;
}
