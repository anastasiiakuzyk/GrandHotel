package ua.kpi.anastasiia.controllers.validators;

import org.springframework.validation.BindingResult;
import ua.kpi.anastasiia.dao.FullBookingDAO;

import java.util.Date;

public class BookingValidator {
    public static void validate(BindingResult result, FullBookingDAO bookings, int guestId, Date arrivingDate, Date leavingDate) {
        if (arrivingDate.after(leavingDate) || arrivingDate.equals(leavingDate)) {
            result.rejectValue("leaving", "error.leaving", "Arriving date should before living");
        }

        if (arrivingDate.before(new Date())) {
            result.rejectValue("leaving", "error.leaving", "Arriving date should be after today");
        }

        if (bookings.isGuestAlreadyBooked(guestId, arrivingDate, leavingDate)) {
            result.rejectValue("leaving", "error.leaving", "Guest already booked this days");
        }
    }
}