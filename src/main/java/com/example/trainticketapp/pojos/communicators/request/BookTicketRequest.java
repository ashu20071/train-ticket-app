package com.example.trainticketapp.pojos.communicators.request;

import com.example.trainticketapp.pojos.context.UserContext;
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
public class BookTicketRequest {
    private UUID userId;
    private String from;
    private String to;
    private Integer pricePaid;
    private UserContext userInfo;
}
