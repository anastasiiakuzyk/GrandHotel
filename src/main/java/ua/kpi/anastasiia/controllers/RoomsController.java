package ua.kpi.anastasiia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.kpi.anastasiia.dao.RoomDAO;
import ua.kpi.anastasiia.dao.RoomOptionDAO;
import ua.kpi.anastasiia.dao.RoomTypeDAO;
import ua.kpi.anastasiia.models.room.Room;
import ua.kpi.anastasiia.models.room.RoomOptions;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rooms")
public class RoomsController {

    @Autowired
    RoomDAO rooms;

    @Autowired
    RoomTypeDAO roomTypeDAO;

    @Autowired
    RoomOptionDAO roomOptionDAO;

    @GetMapping()
    String getRoomsPage(Model model) {
        model.addAttribute("rooms", rooms.getRoomWithOptions());
        return "rooms/list";
    }

    @GetMapping("/{id}")
    String getRoomPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("room", rooms.getRoom(id));
        List<String> options = roomOptionDAO.getRoomOptionsByRoomId(id).stream().map(RoomOptions::getName).collect(Collectors.toList());
        model.addAttribute("roomOptions", String.join(", ", options));
        return "rooms/room";
    }

    @GetMapping("/new")
    String getRoomCreationPage(@ModelAttribute("room") Room room, Model model) {
        model.addAttribute("roomsNumber", rooms.getRooms().size());
        model.addAttribute("types", roomTypeDAO.getRoomTypes());
        model.addAttribute("options", roomOptionDAO.getAllRoomOptions());
        return "rooms/new";
    }

    @PostMapping()
    String setNewRoom(@ModelAttribute("room") @Valid Room room, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", roomTypeDAO.getRoomTypes());
            model.addAttribute("options", roomOptionDAO.getAllRoomOptions());
            model.addAttribute("roomOptions", room.getOptions());
            return "rooms/new";
        }
        rooms.addRoom(room);
        return "redirect:/rooms";
    }

    @GetMapping("/{id}/update")
    String getRoomUpdatingPage(@PathVariable("id") int id, Model model) {
        Room currentRoom = rooms.getRoom(id);
        List<Integer> options = roomOptionDAO.getRoomOptionsByRoomId(id).stream().map(RoomOptions::getId).collect(Collectors.toList());
        currentRoom.setOptions(options);
        model.addAttribute("room", rooms.getRoom(id));
        model.addAttribute("types", roomTypeDAO.getRoomTypes());
        model.addAttribute("options", roomOptionDAO.getAllRoomOptions());
        model.addAttribute("roomOptions", options);

        return "rooms/update";
    }

    @PatchMapping("/{id}")
    String setRoomUpdatingPage(@PathVariable("id") int id,
                               @ModelAttribute("room") @Valid Room room,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Integer> options = roomOptionDAO.getRoomOptionsByRoomId(id).stream().map((ops) -> ops.getId()).collect(Collectors.toList());
            model.addAttribute("types", roomTypeDAO.getRoomTypes());
            model.addAttribute("options", roomOptionDAO.getAllRoomOptions());
            model.addAttribute("roomOptions", options);
            return "rooms/update";
        }
        rooms.updateRoom(id, room);
        return "redirect:/rooms/{id}";
    }

    @DeleteMapping("/{id}")
    String serRoomDeletingPage(@PathVariable("id") int id) {
        rooms.removeRoom(id);
        return "redirect:/rooms";
    }
}
