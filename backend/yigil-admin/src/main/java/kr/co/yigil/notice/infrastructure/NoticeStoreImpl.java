package kr.co.yigil.notice.infrastructure;

import kr.co.yigil.notice.domain.Notice;
import kr.co.yigil.notice.domain.NoticeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeStoreImpl implements NoticeStore {
    private final NoticeRepository noticeRepository;

    @Override
    public Notice save(Notice notice) {
        return noticeRepository.save(notice);
    }

    @Override
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}
