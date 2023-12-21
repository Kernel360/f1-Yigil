package kr.co.yigil.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.travel.domain.Travel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public static Post of(Travel travel, Member member) {
        return Post.builder()
                .travel(travel)
                .member(member)
                .build();
    }

    public static Post of(Long id, Travel travel, Member member){
        var post = Post.of(travel, member);
        post.id = id;
        return post;
    }

    public void updatePost(Travel travel){
        this.travel = travel;
    }

}
