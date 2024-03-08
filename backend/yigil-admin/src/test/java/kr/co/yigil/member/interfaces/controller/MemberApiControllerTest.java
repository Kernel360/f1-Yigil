package kr.co.yigil.member.interfaces.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.member.application.MemberFacade;
import kr.co.yigil.member.interfaces.dto.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberApiController.class)
class MemberApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private MemberFacade memberFacade;

    @MockBean
    private MemberMapper memberMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("회원 목록 조회가 요청되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetMembers_thenReturns200AndMembersResponse() throws Exception {
        mockMvc.perform(get("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("dataCount", "10"))
                .andExpect(status().isOk());

        verify(memberFacade).getMemberPage(any(Pageable.class));
    }

    @DisplayName("회원 정지가 요청되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenBanMembers_thenReturns200AndMemberBanResponse() throws Exception {
        mockMvc.perform(post("/api/v1/members/ban")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ids\": [1, 2, 3]}"))
                .andExpect(status().isOk());

        verify(memberFacade).banMembers(any());
    }

    @DisplayName("회원 정지 해제가 요청되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenUnbanMembers_thenReturns200AndMemberBanResponse() throws Exception {
        mockMvc.perform(post("/api/v1/members/unban")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ids\": [1, 2, 3]}"))
                .andExpect(status().isOk());

        verify(memberFacade).unbanMembers(any());
    }


}