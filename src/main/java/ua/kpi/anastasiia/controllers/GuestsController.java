package ua.kpi.anastasiia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.kpi.anastasiia.controllers.validators.GuestValidator;
import ua.kpi.anastasiia.dao.GuestDAO;
import ua.kpi.anastasiia.models.guest.Guest;

import javax.validation.Valid;

@Controller
@RequestMapping("/guests")
public class GuestsController {

    @Autowired
    GuestDAO guests;

    @GetMapping()
    String getGuestsPage(@RequestParam(required = false) String search, Model model) {
        if (search == null) {
            search = "";
        }
        model.addAttribute("guests", guests.getGuestsWithOptions(search));
        model.addAttribute("search", search);

        return "guests/list";
    }

    @GetMapping("/{id}")
    String getGuestPage(@PathVariable("id") int id, Model model) {
        Guest curGuest = guests.getGuest(id);
        model.addAttribute("guestOptions", String.join(", ", guests.getGuestOptionsName(curGuest)));
        model.addAttribute("guest", curGuest);
        return "guests/guest";
    }

    @GetMapping("/new")
    String getGuestCreationPage(@ModelAttribute("guest") Guest guest, Model model) {
        model.addAttribute("options", guests.getGuestOptions());
        return "guests/new";
    }

    @PostMapping()
    String setNewGuest(@ModelAttribute("guest") @Valid Guest guest, BindingResult bindingResult, Model model) {
        GuestValidator.validate(bindingResult, guest, guests);
        if (bindingResult.hasErrors()) {
            model.addAttribute("options", guests.getGuestOptions());
            model.addAttribute("guestOptions", guest.getOptions());
            return "guests/new";
        }
        guests.addGuest(guest);
        return "redirect:/guests";
    }

    @GetMapping("/{id}/update")
    String getGuestUpdatingPage(@PathVariable("id") int id, Model model) {
        Guest g = guests.getGuest(id);
        g.setOptions(guests.getGuestOptionsId(g));
        model.addAttribute("guest", g);
        model.addAttribute("options", guests.getGuestOptions());
        model.addAttribute("guestOptions", guests.getGuestOptionsId(g));
        return "guests/update";
    }

    @PatchMapping("/{id}")
    String setGuestUpdatingPage(@PathVariable("id") int id,
                                @ModelAttribute("guest") @Valid Guest guest,
                                BindingResult bindingResult, Model model) {
        GuestValidator.validate(bindingResult, guest, guests);
        if (bindingResult.hasErrors()) {
            model.addAttribute("options", guests.getGuestOptions());
            model.addAttribute("guestOptions", guest.getOptions());
            return "guests/update";
        }
        guests.updateGuest(id, guest);
        return "redirect:/guests/{id}";
    }

    @DeleteMapping("/{id}")
    String setGuestDeletingPage(@PathVariable("id") int id) {
        guests.removeGuest(id);
        return "redirect:/guests";
    }
}