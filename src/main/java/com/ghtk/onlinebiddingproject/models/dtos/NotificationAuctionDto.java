package com.ghtk.onlinebiddingproject.models.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ghtk.onlinebiddingproject.models.responses.NotificationAuctionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationAuctionDto {
    private Integer id;

    @JsonIgnore
    private NotificationDto notification;

    private NotificationAuctionResponseDto auction;
}
