package com.example.trainticketapp.pojos.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author ashutosh
 * date 06/02/25
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "seat")
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_seq_generator")
    @SequenceGenerator(name = "entity_seq_generator", sequenceName = "entity_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long seatId;

    @Column(name = "section", nullable = false)
    private String section;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @Column(name = "is_taken", nullable = false)
    private Boolean isTaken = false;
}
