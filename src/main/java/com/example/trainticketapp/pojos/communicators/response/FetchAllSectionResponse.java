package com.example.trainticketapp.pojos.communicators.response;

import com.example.trainticketapp.pojos.context.SeatContext;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

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
public class FetchAllSectionResponse {
    List<SeatContext> seats;
}
