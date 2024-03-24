package kr.co.yigil.report.type.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE report_type SET status = 'DELETED' WHERE id = ?")
public class ReportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private ReportTypeStatus status;


    public ReportType(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = ReportTypeStatus.ACTIVE;
    }
    public ReportType(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = ReportTypeStatus.ACTIVE;
    }
}
