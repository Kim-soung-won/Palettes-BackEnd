package com.palette.palettepetsback.carrot.dto;

import com.palette.palettepetsback.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@Data
@ToString
@NoArgsConstructor
public class CarrotRequestDTO {
    private String carrotTitle;
    private String carrotContent;
    private Integer carrotPrice;
    private String carrotTag;
}
