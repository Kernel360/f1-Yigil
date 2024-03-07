package kr.co.yigil.place.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DemographicPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    private Long referenceCount;

    @Enumerated(value = EnumType.STRING)
    private Ages ages = Ages.NONE;

    @Enumerated(value = EnumType.STRING)
    private Gender gender   = Gender.NONE;

    public DemographicPlace(Place place, Long referenceCount, Ages ages, Gender gender) {
        this.place = place;
        this.referenceCount = referenceCount;
        this.ages = ages;
        this.gender = gender;
    }
}
