package kr.co.yigil.report.type.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    public ReportType(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public ReportType(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
