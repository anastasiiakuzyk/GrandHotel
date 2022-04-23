package ua.kpi.anastasiia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.kpi.anastasiia.controllers.utils.DateSerialization;
import ua.kpi.anastasiia.controllers.validators.BookingValidator;
import ua.kpi.anastasiia.dao.FullBookingDAO;
import ua.kpi.anastasiia.dao.GuestDAO;
import ua.kpi.anastasiia.dao.RoomDAO;
import ua.kpi.anastasiia.dao.RoomOptionDAO;
import ua.kpi.anastasiia.models.booking.Booking;
import ua.kpi.anastasiia.models.requests.FinalBookingReq;
import ua.kpi.anastasiia.models.requests.InitBookingReq;
import ua.kpi.anastasiia.models.room.Room;
import ua.kpi.anastasiia.services.RoomService;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    FullBookingDAO bookings;

    @Autowired
    RoomOptionDAO roomOptions;

    @Autowired
    GuestDAO guests;

    @Autowired
    RoomDAO rooms;

    @Autowired
    RoomService roomService;

    @GetMapping()
    String getBookingPage(Model model) {
        model.addAttribute("bookings", bookings.getBookings());
        return "booking/index";
    }

    @GetMapping("/{id}")
    String getbookingPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("booking", bookings.getBooking(id));
        return "booking/book";
    }

    @GetMapping("/book")
    String getbookingCreationPage(@ModelAttribute("booking") InitBookingReq booking, Model model) {
        model.addAttribute("guests", guests.getGuests());
        return "booking/book";
    }

    @PostMapping("/book")
    String processToChoosingRoom(@ModelAttribute("booking") InitBookingReq booking, Model model, BindingResult result) {

        Date arrivingDate = DateSerialization.getDateFromString(booking.getArriving());
        Date leavingDate = DateSerialization.getDateFromString(booking.getLeaving());

        BookingValidator.validate(result, bookings, booking.getGuestId(), arrivingDate, leavingDate);

        Booking newBooking = new Booking();
        newBooking.setArriving(arrivingDate);
        newBooking.setLeaving(leavingDate);
        newBooking.setId(booking.getId());
        newBooking.setGuestId(booking.getGuestId());

        List<Integer> optionsId = guests.getGuestOptionsId(guests.getGuest(newBooking.getGuestId()));
        List<Room> roomsByGuest = roomService.getRooms(arrivingDate, leavingDate, optionsId);

        model.addAttribute("fullBooking", newBooking);
        model.addAttribute("rooms", roomsByGuest);
        model.addAttribute("guests", guests.getGuests());

        if (result.hasErrors()) {
            return "booking/book";
        }
        model.addAttribute("guest", guests.getGuest(booking.getGuestId()));
        return "booking/chooseRoom";
    }


    @PostMapping()
    String setNewbooking(@ModelAttribute("booking") @Valid FinalBookingReq booking, BindingResult bindingResult) {

        Date arrivingDate = DateSerialization.getDateFromStringFormatter(booking.getArriving());
        Date leavingDate = DateSerialization.getDateFromStringFormatter(booking.getLeaving());

        Booking newBooking = new Booking();
        newBooking.setArriving(arrivingDate);
        newBooking.setLeaving(leavingDate);
        newBooking.setId(booking.getId());
        newBooking.setGuestId(booking.getGuestId());
        newBooking.setRoomId(booking.getRoomId());

        if (bindingResult.hasErrors()) {
            return "booking/book";
        }
        bookings.addBooking(newBooking);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    String setbookingDeletingPage(@PathVariable("id") int id) {
        bookings.removeBooking(id);
        return "redirect:/";
    }

    @GetMapping("/search/{id}")
    String getSearchResults(@PathVariable int id, Model model) {
        model.addAttribute("booking", bookings.getBooking(id));
        return "bookings/";
    }
}
