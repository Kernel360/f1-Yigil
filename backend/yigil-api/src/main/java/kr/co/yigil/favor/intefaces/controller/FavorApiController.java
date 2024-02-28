package kr.co.yigil.favor.intefaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.favor.application.FavorFacade;
import kr.co.yigil.favor.intefaces.dto.mapper.FavorDto;
import kr.co.yigil.favor.intefaces.dto.mapper.FavorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FavorApiController {

    private final FavorFacade favorFacade;
    private final FavorMapper favorMapper;

    @PostMapping("/api/v1/like/{postId}")
    @MemberOnly
    public ResponseEntity<FavorDto.AddFavorResponse> addFavor(@Auth final Accessor accessor,
            @PathVariable("postId") final Long postId) {
        var responseInfo = favorFacade.addFavor(accessor.getMemberId(), postId);
        var response =  favorMapper.of(responseInfo);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/v1/unlike/{postId}")
    @MemberOnly
    public ResponseEntity<FavorDto.DeleteFavorResponse> deleteFavor(@Auth final Accessor accessor,
            @PathVariable("postId") final Long postId) {
        var responseInfo = favorFacade.deleteFavor(accessor.getMemberId(), postId);
        var response = favorMapper.of(responseInfo);
        return ResponseEntity.ok().body(response);
    }
}
