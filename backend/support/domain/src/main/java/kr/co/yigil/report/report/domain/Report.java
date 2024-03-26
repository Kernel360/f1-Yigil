package kr.co.yigil.report.report.domain;


import jakarta.persistence.*;
import kr.co.yigil.member.Member;
import kr.co.yigil.report.type.domain.ReportType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    private String content;

    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    @ManyToOne
    private Member reporter;

    private Long targetId;

    @Enumerated(EnumType.STRING)
    private ReportTargetType reportTargetType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Report(ReportType reportType, String content, Long targetId, Member reporter) {
        this.reportType = reportType;
        this.content = content;
        this.targetId = targetId;
        this.reporter = reporter;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = ProcessStatus.NOT_PROCESSED;
    }

    public void completed() {
        this.status = ProcessStatus.PROCESSED;
    }

    public void rejected(){
        this.status = ProcessStatus.REJECTED;
    }

    public void readByAdmin(){
        this.status = ProcessStatus.PROCESSING;
    }

}
