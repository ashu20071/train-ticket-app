package com.example.trainticketapp.pojos.projection;

import java.util.UUID;

/**
 * @author ashutosh
 * date 06/02/25
 */
public interface SeatUserProjection {
    Long getSeatId();

    String getSection();

    Boolean getIsEnabled();

    Integer getSeatNumber();

    Boolean getIsTaken();

    // From TicketEntity
    UUID getTicketId();

    UUID getUserId(); // To correlate with UserEntity

    String getFromLocation();

    String getToLocation();

    Integer getPricePaid();

    // From UserEntity
    String getFirstName();

    String getLastName();

    String getEmail();
}
