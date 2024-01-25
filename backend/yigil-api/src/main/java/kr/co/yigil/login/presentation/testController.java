package kr.co.yigil.login.presentation;

import jakarta.servlet.http.HttpSession;
import kr.co.yigil.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class testController {

    private final MemberRepository repository;

    @PostMapping("/test")
    public ResponseEntity<String> loginTest(HttpSession session) {
        session.setAttribute("memberId", 1L);
        log.error("test-error");
        return ResponseEntity.ok("로그인 성공");
    }
}
