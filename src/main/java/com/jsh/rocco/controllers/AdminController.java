package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.entities.Hotel;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.services.HotelService;
import com.jsh.rocco.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RoomService roomService;

    private final HotelService hotelService;

    @Autowired
    public AdminController(RoomService roomService
            , HotelService hotelService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
    }


    @GetMapping("room")
    public String getAddRoom() {
        return "admin/room";
    }

    ;

    @PostMapping("room")
    public String postAddRoom(@RequestParam("_thumbnail") MultipartFile thumbnail,
                              @RequestParam("hotelId") long hotelId,
                              Room room) throws IOException {
        room.setThumbnail(thumbnail.getBytes());
        room.setThumbnailContentType(thumbnail.getContentType());
        roomService.addRoom2(room, hotelId);
        return "admin/room";
    }

    ;

    @GetMapping("hotel")
    public String getAddHotel() {
        return "admin/hotel";
    }

    ;

    @PostMapping("hotel")
    public String postAddHotel(@RequestParam("_thumbnail") MultipartFile thumbnail,
                               Hotel hotel) throws IOException {
        hotelService.addHotel(hotel);
        return "admin/room";
    }

}
