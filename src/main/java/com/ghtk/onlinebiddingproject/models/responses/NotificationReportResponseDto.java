package com.ghtk.onlinebiddingproject.models.responses;

import com.ghtk.onlinebiddingproject.models.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationReportResponseDto {

    private Integer id;

    private String description;

    private UserDto userReported;
}
