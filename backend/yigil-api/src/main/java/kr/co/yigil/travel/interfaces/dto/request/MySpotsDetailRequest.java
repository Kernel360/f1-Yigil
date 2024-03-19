package kr.co.yigil.travel.interfaces.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class MySpotsDetailRequest {
    List<Long> spotIds;

}
