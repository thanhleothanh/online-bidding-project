package com.ghtk.onlinebiddingproject.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ghtk.onlinebiddingproject.constants.ReportResultConstants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReportResultDto {
    private Integer id;

    @NotBlank(message = "thiếu thông tin kết quả (REJECTED/ACCEPTED)!")
    private ReportResultConstants result;

    @JsonIgnore
    @NotNull(message = "thiếu thông tin phiếu báo cáo!")
    private ReportDto report;

    @NotNull(message = "thiếu thông tin admin!")
    private AdminDto admin;
}
