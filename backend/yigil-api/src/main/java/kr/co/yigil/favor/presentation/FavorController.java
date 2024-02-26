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

@RestController
@RequiredArgsConstructor
public class FavorController {

    private final FavorService favorService;

//    @PostMapping("/api/v1/like/{postId}")
//    @MemberOnly
//    public ResponseEntity<AddFavorResponse> addFavor(@Auth final Accessor accessor,
//            @PathVariable("postId") final Long postId) {
//        AddFavorResponse response = favorService.addFavor(accessor.getMemberId(), postId);
//        return ResponseEntity.ok().body(response);
//    }
//
//    @PostMapping("/api/v1/unlike/{postId}")
//    @MemberOnly
//    public ResponseEntity<DeleteFavorResponse> deleteFavor(@Auth final Accessor accessor,
//            @PathVariable("postId") final Long postId) {
//        DeleteFavorResponse response = favorService.deleteFavor(accessor.getMemberId(), postId);
//        return ResponseEntity.ok().body(response);
//    }
}
