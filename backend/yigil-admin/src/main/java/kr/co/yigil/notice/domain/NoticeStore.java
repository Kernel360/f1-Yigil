package kr.co.yigil.notice.domain;

public interface NoticeStore {

    Notice save(Notice notice);

    void delete(Long id);

}
