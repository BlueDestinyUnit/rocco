package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.dtos.AvailableRoom;
import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.domains.enums.results.CommonResult;
import com.jsh.rocco.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PatchMapping("availableRooms")
    @ResponseBody
    public ResponseEntity<?> postRooms(@RequestBody FindHotel findHotel){
        List<AvailableRoom> rooms = this.roomService.findAvailableHotelAndRooms(findHotel);
        Map<String, Object> response = new HashMap<>();
        if(rooms == null){
            response.put("result", CommonResult.FAILURE.name().toLowerCase());
        }else{
            response.put("result", CommonResult.SUCCESS.name().toLowerCase());
        }
        response.put("list",rooms);
        response.put("rooms",rooms);
        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/detail")
    public void getRoomDetail(Room room, Model model){
        Room dbRoom = roomService.findById(room.getId());
        model.addAttribute("room",dbRoom);
    }

    @RequestMapping(value = "/thumbnail", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getThumbnail(@RequestParam("index") long index) {
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
