package kr.co.yigil.member.presentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.dto.response.MemberInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("내 정보가 조회될 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetMyInfo_thenReturns200AndMemberInfoResponse() throws Exception {
        MemberInfoResponse mockResponse = new MemberInfoResponse();
        Accessor accessor = Accessor.member(1L);

        given(memberService.getMemberInfo(accessor.getMemberId())).willReturn(mockResponse);

        mockMvc.perform(get("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
