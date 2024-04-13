package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("room")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public void getRooms(){}

    @GetMapping("/detail")
    public void getRoomDetail(Room room, Model model){
        Room dbRoom = roomService.findById(room.getId());
        model.addAttribute("room",dbRoom);
    }
}
