package kr.co.yigil.member.presentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.dto.request.MemberUpdateRequest;
import kr.co.yigil.member.dto.response.MemberDeleteResponse;
import kr.co.yigil.member.dto.response.MemberInfoResponse;
import kr.co.yigil.member.dto.response.MemberUpdateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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

    @DisplayName("사용자 정보를 조회할 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetMemberInfo_thenReturns200AndMemberInfoResponse() throws Exception {
        MemberInfoResponse mockResponse = new MemberInfoResponse();
        Long memberId = 1L;

        given(memberService.getMemberInfo(memberId)).willReturn(mockResponse);

        mockMvc.perform(get("/api/v1/member/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("내 정보 업데이트 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenUpdateMyInfo_thenReturns200AndMemberUpdateResponse() throws Exception {
        MemberUpdateResponse mockResponse = new MemberUpdateResponse();
        MemberUpdateRequest mockRequest = new MemberUpdateRequest();
        Accessor accessor = Accessor.member(1L);

        given(memberService.updateMemberInfo(accessor.getMemberId(), mockRequest)).willReturn(mockResponse);

        MockMultipartFile file = new MockMultipartFile("file", "filename.jpg", "image/jpeg", new byte[10]);


        mockMvc.perform(multipart("/api/v1/member")
                        .file(file)
                        .param("nickname", "nickname")
                        .sessionAttr("memberId", accessor.getMemberId()))
                .andExpect(status().isOk());
    }

    @DisplayName("회원 탈퇴 요청이 왔을 때 200 응답과 Response가 잘 반환되는지")
    @Test
    void whenWithdraw_thenReturns200AndMemberDeleteResponse() throws Exception {
        MemberDeleteResponse mockResponse = new MemberDeleteResponse();
        Accessor accessor = Accessor.member(1L);

        given(memberService.withdraw(accessor.getMemberId())).willReturn(mockResponse);

        mockMvc.perform(delete("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

