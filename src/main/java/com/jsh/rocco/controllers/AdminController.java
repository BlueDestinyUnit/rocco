package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.domains.entities.Room;
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

    @Autowired
    public AdminController(RoomService roomService) {
        this.roomService = roomService;
    }



    @GetMapping("room")
    public String getAddRoom() {
      return "admin/room";
    };

    @PostMapping("room")
    public String postAddRoom(@RequestParam("_thumbnail") MultipartFile thumbnail,
                              @RequestParam("propertyId") long propertyId,
                              Room room) throws IOException {
        System.out.println(room);

        System.out.println("진입은 했니?");
        room.setThumbnail(thumbnail.getBytes());
        room.setThumbnailContentType(thumbnail.getContentType());
        roomService.addRoom2(room,propertyId);
        return "admin/room";
    };

    @GetMapping("hotel")
    public String getAddHotel() {
        return "admin/hotel";
    };

    @PostMapping("hotel")
    public String postAddHotel(@RequestParam("_thumbnail") MultipartFile thumbnail,
                               Property property) throws IOException {


//        System.out.println("진입은 했니?");
//        property.setThumbnail(thumbnail.getBytes());
//        room.setThumbnailContentType(thumbnail.getContentType());
//        roomService.addRoom2(room,propertyId);
        return "admin/room";
    };

}
