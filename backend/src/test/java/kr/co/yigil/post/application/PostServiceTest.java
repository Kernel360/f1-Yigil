package kr.co.yigil.post.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.post.dto.response.PostDeleteResponse;
import kr.co.yigil.post.dto.response.PostListResponse;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.repository.TravelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Spy
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private TravelRepository travelRepository;

    @DisplayName("When finding all posts, then return PostListResponse")
    @Test
    void whenFindAllPosts_thenReturnPostListResponse() {
        // Given
        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint1 = geometryFactory.createPoint(new Coordinate(0,0));
        Point mockPoint2 = geometryFactory.createPoint(new Coordinate(1,1));
        Point mockPoint3 = geometryFactory.createPoint(new Coordinate(2,2));
        Point mockPoint4 = geometryFactory.createPoint(new Coordinate(3,3));

        Spot mockSpot1 = new Spot(mockPoint1,"spot title1", "spot file url1","spot description1");
        Spot mockSpot2 = new Spot(mockPoint2,"spot title2", "spot file url2","spot description2");
        Spot mockSpot3 = new Spot(mockPoint3,"spot title3", "spot file url3","spot description3");
        Spot mockSpot4 = new Spot(mockPoint4,"spot title4", "spot file url4","spot description4");

        List<Coordinate> coordinates = List.of(
            new Coordinate(2, 2),
            new Coordinate(3, 3)
        );

        LineString lineString = geometryFactory.createLineString(coordinates.toArray(new Coordinate[0]));
        Course mockCourse1 = new Course(lineString, List.of(mockSpot3, mockSpot4), 1, "coursetitle");

        Member mockMember = new Member(1L, "kiit0901@gmail.com", "123456", "stone", "profile.jpg", SocialLoginType.KAKAO);

        Post mockPost1 = new Post(1L, mockSpot1, mockMember);
        Post mockPost2 = new Post(2L, mockSpot2, mockMember);
        Post mockPost3 = new Post(3L, mockCourse1, mockMember);

        List<Post> posts = List.of(mockPost1, mockPost2, mockPost3);
        when(postRepository.findAll()).thenReturn(posts);

        // When
        PostListResponse postListResponse = postService.findAllPosts();
        // Then
        assertNotNull(postListResponse);
        assertThat(postService.findAllPosts()).isInstanceOf(PostListResponse.class);
        assertEquals(posts.size(), postListResponse.getPosts().size());
    }

    @DisplayName("When finding post by ID, then return Post")
    @Test
    void whenFindPostById_thenReturnPost() {
        // Given
        Long postId = 1L;

        Member mockMember = new Member(1L, "kiit0901@gmail.com", "123456", "stone", "profile.jpg", SocialLoginType.KAKAO);
        Travel mockTravel = new Travel(1L);
        Post mockPost = new Post(mockTravel ,mockMember);
        when(postRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockPost));

        // When
        Post resultPost = postService.findPostById(postId);

        // Then
        assertNotNull(resultPost);
        assertEquals(mockPost, resultPost);
    }

    @DisplayName("When finding post by ID, then return Post")
    @Test
    void GivenInvalidPostId_whenFindPostById_thenThrowException() {
        // Given

        // When
        when(postRepository.findById(anyLong())).thenThrow(BadRequestException.class);

        // Then
        assertThrows(BadRequestException.class, ()-> postService.findPostById(anyLong()));
    }

    @DisplayName("When creating post, then invoke save method")
    @Test
    void whenCreatePost_thenInvokeSaveMethod() {
        // Given

        Member mockMember = new Member(1L, "kiit0901@gmail.com", "123456", "stone", "profile.jpg", SocialLoginType.KAKAO);
        Travel mockTravel = new Travel(1L);

        // When
        assertDoesNotThrow(() -> postService.createPost(mockTravel, mockMember));

        // Then: Verify that the save method was called
        verify(postRepository, times(1)).save(any());
    }
    @DisplayName("When updating post, then invoke save method")
    @Test
    void whenUpdatePost_thenInvokeSaveMethod() {
        // Given
        Long postId = 1L;
        Member mockMember = new Member(1L, "kiit0901@gmail.com", "123456", "stone", "profile.jpg", SocialLoginType.KAKAO);
        Travel mockTravel = new Travel(1L);

        // When
        assertDoesNotThrow(() -> postService.updatePost(postId, mockTravel, mockMember));

        // Then: Verify that the save method was called
        verify(postRepository, times(1)).save(any());
    }

    @DisplayName("When deleting post, then invoke delete methods and return PostDeleteResponse")
    @Test
    void whenDeletePost_thenInvokeDeleteMethodsAndReturnPostDeleteResponse() {
        // Given
        Long memberId = 1L;
        Long postId = 1L;
        Travel mockTravel = new Travel(1L);
        Member mockMember = new Member(1L, "kiit0901@gmail.com", "123456", "stone", "profile.jpg", SocialLoginType.KAKAO);
        Post mockPost = new Post(postId, mockTravel, mockMember);

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(mockPost));
        doNothing().when(postService).validatePostWriter(memberId, postId);
        doNothing().when(travelRepository).delete(any());
        doNothing().when(postRepository).delete(any());

        // When
        PostDeleteResponse postDeleteResponse = postService.deletePost(memberId, postId);

        // Then: Verify that the delete methods were called
        verify(travelRepository, times(1)).delete(any());
        verify(postRepository, times(1)).delete(any());
        assertNotNull(postDeleteResponse);
        assertEquals("post 삭제 성공", postDeleteResponse.getMessage());
    }


    @DisplayName("When deleting only post, then invoke deleteByTravelIdAndMemberId method")
    @Test
    void whenDeleteOnlyPost_thenInvokeDeleteByTravelIdAndMemberIdMethod() {
        // Given
        Long memberId = 1L;
        Long travelId = 1L;

        // When
        assertDoesNotThrow(() -> postService.deleteOnlyPost(memberId, travelId));

        // Then: Verify that deleteByTravelIdAndMemberId method was called
        verify(postRepository, times(1)).deleteByTravelIdAndMemberId(memberId, travelId);
    }

    @DisplayName("When validating post writer with invalid authority, then throw BadRequestException")
    @Test
    void whenValidatePostWriterWithInvalidAuthority_thenThrowBadRequestException() {
        // Given
        Long invalidMemberId = 1L;
        Long postId = 1L;
        when(postRepository.existsByMemberIdAndId(invalidMemberId, postId)).thenReturn(false);

        // When, Then
        assertThrows(BadRequestException.class, () -> postService.validatePostWriter(invalidMemberId, postId));
    }

    @DisplayName("When validating post writer with valid authority, then do nothing")
    @Test
    void whenValidatePostWriterWithValidAuthority_thenDoNothing() {
        // Given
        Long validMemberId = 1L;
        Long postId = 1L;
        when(postRepository.existsByMemberIdAndId(validMemberId, postId)).thenReturn(true);

        // When, Then: No exception should be thrown
        assertDoesNotThrow(() -> postService.validatePostWriter(validMemberId, postId));
    }

}
