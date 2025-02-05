package com.example.trainticketapp.pojos.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

/**
 * @author ashutosh
 * date 06/02/25
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatContext {
    private Long seatId;
    private String section;
    private Integer seatNumber;
    private UUID ticketId;
    private String from;
    private String to;
    private UserContext userInfo;
    private Boolean isTaken = false;
}
