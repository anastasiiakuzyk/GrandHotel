package ua.kpi.anastasiia.controllers.validators;

import org.springframework.validation.BindingResult;
import ua.kpi.anastasiia.dao.GuestDAO;
import ua.kpi.anastasiia.models.guest.Guest;

public class GuestValidator {

    public static void validate(BindingResult result, Guest guest, GuestDAO guests) {
        Guest guestByPhone = guests.getGuestByPhone(guest.getPhone());
        if (guestByPhone != null && guest.getId() != guestByPhone.getId()) {
            result.rejectValue("phone", "error.phone", "Guest with this phone already exists");
        }
        Guest guestByPassport = guests.getGuestByPassport(guest.getPassport());
        if (guestByPassport != null && guest.getId() != guestByPassport.getId()) {
            result.rejectValue("passport", "error.passport", "Guest with this passport already exists");
        }
        Guest guestByEmail = guests.getGuestByEmail(guest.getEmail());
        if (guestByEmail != null && guest.getId() != guestByEmail.getId()) {
            result.rejectValue("email", "error.email", "Guest with this email already exists");
        }

    }
}
