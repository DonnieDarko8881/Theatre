package com.crud.theatre.domain;

import com.crud.theatre.exception.SeatsNotFoundException;
import com.crud.theatre.repository.SeatsRepository;
import com.crud.theatre.service.ReservationService;
import com.crud.theatre.service.StageCopyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationTestSuite {

    @Autowired
    private StageCopyService stageCopyService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SeatsRepository seatsRepository;

    @Test
    public void shouldReturnIdReservationWithPlaceNumber5() {
        //Given
        StageCopy stageCopy = stageCopyService.findById(91);
        Seats seats = stageCopy.getSeats().stream()
                .filter(seat -> seat.getNumber() == 4)
                .findFirst().orElseThrow(SeatsNotFoundException::new);
        seats.setStatus(Status.RESERVER.toString());
        Reservation reservation = new Reservation(stageCopy, seats, seats.getNumber());

        //when
        seatsRepository.save(seats);
        reservationService.save(reservation);
        long id = reservation.getId();

        //then
        assertNotEquals(0, id);
        assertEquals(Status.RESERVER.toString(),seats.getStatus());
        assertEquals(Status.RESERVER.toString(),reservation.getSeats().getStatus());

    }
}
