package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "/thumbnail", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getThumbnail(@RequestParam("index") long index) {
        System.out.println(index);
        Room room = this.roomService.findById(index);
        if (room == null) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(room.getThumbnailContentType()))
                .contentLength(room.getThumbnail().length)
                .body(room.getThumbnail());
    }
}
