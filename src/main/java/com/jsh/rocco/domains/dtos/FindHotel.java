package com.jsh.rocco.domains.dtos;


import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;


@Data
@ToString
public class FindHotel {
    private long hotelId;
    private String hotelRegion;
    private int customers;
    private int roomCount;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureDate;
}
