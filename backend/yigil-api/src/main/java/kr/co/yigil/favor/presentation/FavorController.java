package kr.co.yigil.favor.presentation;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.favor.application.FavorService;
import kr.co.yigil.favor.dto.response.AddFavorResponse;
import kr.co.yigil.favor.dto.response.DeleteFavorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 해당 부분도 /api/v1에 대해서 공통으로 뱨보는것도 생각해보시면 좋을것 같습니다.
 * */
@RestController
@RequiredArgsConstructor
public class FavorController {

    private final FavorService favorService;

    @PostMapping("/api/v1/like/{postId}")
    @MemberOnly
    public ResponseEntity<AddFavorResponse> addFavor(@Auth final Accessor accessor,
            @PathVariable("postId") final Long postId) {
        /**
         * return ResponseEntity.ok().body(
         *   favorService.addFavor(accessor.getMemberId(), postId)
         * );
         * 한번에 넘기는건 어떨까요?
         * 추가 변수를 생성할 필요 없이
         * 바로 반환하는것이 간결하고 가독성이 조금 더 괜찮지 않을까? 하는 생각입니다.
         * */
        AddFavorResponse response = favorService.addFavor(accessor.getMemberId(), postId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/v1/unlike/{postId}")
    @MemberOnly
    public ResponseEntity<DeleteFavorResponse> deleteFavor(@Auth final Accessor accessor,
            @PathVariable("postId") final Long postId) {
        DeleteFavorResponse response = favorService.deleteFavor(accessor.getMemberId(), postId);
        return ResponseEntity.ok().body(response);
    }
}
