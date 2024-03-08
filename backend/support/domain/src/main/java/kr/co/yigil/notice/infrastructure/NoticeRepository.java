package kr.co.yigil.notice.infrastructure;

import kr.co.yigil.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
