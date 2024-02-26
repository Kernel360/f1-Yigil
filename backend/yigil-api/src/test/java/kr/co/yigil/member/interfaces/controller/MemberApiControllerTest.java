package kr.co.yigil.member.interfaces.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.member.application.MemberFacade;
import kr.co.yigil.member.interfaces.dto.MemberDto;
import kr.co.yigil.member.interfaces.dto.mapper.MemberDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberApiController.class)
@EnableSpringDataWebSupport
class MemberApiControllerTest {

    @MockBean
    private MemberFacade memberFacade;

    @MockBean
    private MemberDtoMapper memberDtoMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getMyInfo_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/members"))
            .andExpect(status().isOk());

        verify(memberFacade).getMemberInfo(anyLong());
    }

    @Test
    void getMyCourseInfo_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/members/courses")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "createdAt")
                .param("sortOrder", "desc")
                .param("visibility", "public")
            )
            .andExpect(status().isOk());
        verify(memberFacade).getMemberCourseInfo(anyLong(), any(PageRequest.class), anyString());
    }

    @Test
    void getMySpotInfo_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/members/spots")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "createdAt")
                .param("sortOrder", "desc")
                .param("visibility", "public")
            )
            .andExpect(status().isOk());
        verify(memberFacade).getMemberSpotInfo(anyLong(), any(PageRequest.class), anyString());
    }

    @Test
    void setTravelsVisibility_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/v1/members/travels/visibility")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"visibility\": \"public\"}")
                .content("{\"travel_ids\": [1], \"is_private\": false}")
            )
            .andExpect(status().isOk());
        verify(memberFacade).setTravelsVisibility(anyLong(), any());
    }

    @Test
    void WhenGetMyFollowerList_ThenShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/members/followers")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
            )
            .andExpect(status().isOk());
        verify(memberFacade).getFollowerList(anyLong(), any(PageRequest.class));
    }

    @Test
    void WhenGetMyFollowingList_ThenShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/members/followings")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
            )
            .andExpect(status().isOk());
        verify(memberFacade).getFollowingList(anyLong(), any(PageRequest.class));
    }

    @Test
    void updateMyInfo_ShouldReturnOk() throws Exception{

        MultipartFile multipartFile = new MockMultipartFile(
            "profileImageFile",          // 필드 이름
            "hello.txt",                  // 원본 파일 이름
            MediaType.IMAGE_JPEG_VALUE,   // 컨텐츠 타입
            "Hello, World!".getBytes()    // 파일 내용
        );
        MemberDto.MemberUpdateRequest request = new MemberDto.MemberUpdateRequest("nickname", "10대", "남성", multipartFile);

        mockMvc.perform(post("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    void WhenWithdraw_ThenShouldReturnOk() throws Exception{
        mockMvc.perform(delete("/api/v1/members"))
            .andExpect(status().isOk());

        verify(memberFacade).withdraw(anyLong());
    }

    @Test
    void WhenGetMemberInfo_ThenShouldReturnOk() throws Exception{
        mockMvc.perform(get("/api/v1/members/{memberId}", 1L))
            .andExpect(status().isOk());

        verify(memberFacade).getMemberInfo(anyLong());
    }

    @Test
    void WhenGetMemberFollowerList_ThenReturnOk() throws Exception{
        mockMvc.perform(get("/api/v1/members/followers/{memberId}", 1L)
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
            )
            .andExpect(status().isOk());

        verify(memberFacade).getFollowerList(anyLong(), any(PageRequest.class));

    }

    @Test
    void WhenGetMemberFollowingList_ThenShouldReturnOk() throws Exception{
        mockMvc.perform(get("/api/v1/members/followings/{memberId}", 1L)
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
            )
            .andExpect(status().isOk());

        verify(memberFacade).getFollowingList(anyLong(), any(PageRequest.class));
    }
}