package kr.co.yigil.admin.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    @GetMapping("/api/v1/admin")
    public void test() {
        log.error("admin test");
    }
}
