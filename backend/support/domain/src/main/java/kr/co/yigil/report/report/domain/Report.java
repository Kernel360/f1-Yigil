package kr.co.yigil.report.report.domain;


import jakarta.persistence.*;
import kr.co.yigil.member.Member;
import kr.co.yigil.report.type.domain.ReportType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ReportType reportType;
    private String Content;
    private ProcessStatus processStatus;

    @ManyToOne
    private Member member;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Report(ReportType reportType, String content, Member member) {
        this.reportType = reportType;
        this.Content = content;
        this.member = member;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.processStatus = ProcessStatus.NOT_PROCESSED;
    }

    public void completed() {
        this.processStatus = ProcessStatus.PROCESSED;
    }

    public void rejected(){
        this.processStatus = ProcessStatus.REJECTED;
    }

    public void readByAdmin(){
        this.processStatus = ProcessStatus.PROCESSING;
    }

}
