package kr.co.yigil.follow.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.follow.FollowCountDto;
import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class FollowReaderImplTest {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private MemberReader memberReader;

    @InjectMocks
    private FollowReaderImpl followReader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("getFollowCount 메서드가 올바른 FollowCount를 반환하는지")
    @Test
    void whenGetFollowCount_thenReturnsCorrectFollowCount() {
        Long memberId = 1L;
        FollowCountDto followCountDto = new FollowCountDto(10L, 20L);
        FollowCount expectedFollowCount = new FollowCount(memberId, 10, 20);

        when(followRepository.getFollowCounts(memberId)).thenReturn(followCountDto);

        FollowCount actualFollowCount = followReader.getFollowCount(memberId);

        assertEquals(expectedFollowCount, actualFollowCount);
    }

    @DisplayName("isFollowing 메서드가 올바른 결과를 반환하는지")
    @Test
    void whenIsFollowing_thenReturnsCorrectResult() {
        Long followerId = 1L;
        Long followingId = 2L;

        when(followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)).thenReturn(true);

        boolean isFollowing = followReader.isFollowing(followerId, followingId);

        assertTrue(isFollowing);
    }

    @DisplayName("getFollowerSlice 메서드가 올바른 Slice를 반환하는지")
    @Test
    void whenGetFollowerSlice_thenReturnsCorrectSlice() {
        Long memberId = 1L;
        Long anotherMemberId = 2L;
        PageRequest pageRequest = PageRequest.of(0, 10);

        Member followerMember = new Member(anotherMemberId, null, null, null, null, null);
        Member followingMember = new Member(memberId, null, null, null, null, null);

        Follow mockFollow = new Follow(followerMember, followingMember);
        List<Follow> follows = List.of(mockFollow);
        Slice<Follow> expectedSlice = new SliceImpl<>(follows, pageRequest, true);

        when(followRepository.findAllByFollowingId(anyLong(), any(Pageable.class))).thenReturn(expectedSlice);

        Slice<Follow> actualSlice = followReader.getFollowerSlice(memberId, pageRequest);

        assertEquals(expectedSlice, actualSlice);
    }

    @DisplayName("getFollowingSlice 메서드가 올바른 Slice를 반환하는지")
    @Test
    void whenGetFollowingSlice_thenReturnsCorrectSlice() {
        Long memberId = 1L;
        Member member = new Member("kiit0901@gmail.com", "123456", "stone", "profile.jpg", "kakao");
        List<Follow> follows = new ArrayList<>();
        Slice<Follow> expectedSlice = new SliceImpl<>(follows);

        when(memberReader.getMember(memberId)).thenReturn(member);
        when(followRepository.findAllByFollowerId(anyLong(), any(Pageable.class))).thenReturn(expectedSlice);

        Slice<Follow> actualSlice = followReader.getFollowingSlice(memberId, Pageable.unpaged());

        assertEquals(expectedSlice, actualSlice);
    }

}