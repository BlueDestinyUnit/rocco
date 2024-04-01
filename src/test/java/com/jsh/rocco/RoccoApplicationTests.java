package com.jsh.rocco;

import com.jsh.rocco.domains.entities.*;
import com.jsh.rocco.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class RoccoApplicationTests {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRoomService reservationRoomService;


    @Autowired
    private PaymentService paymentService;

    @Test
    @Transactional
    @Commit
    void testCustomer() {
        Customer customer = new Customer();
        customer.setPhone("01049333657");
        customer.setBirthDate(new Date());
        customer.setFirstName("테스트1");
        customer.setLastName("테스트1");
        customerService.addCustomer(customer);
    }

    @Test
    @Transactional
    @Commit
    void testProvperty() {
        PropertyAddress propertyAddress = new PropertyAddress();
        propertyAddress.setRegion("대구");
        propertyAddress.setStreet1("테스트거리");
        propertyAddress.setStreet2("테스트거리2");
        propertyAddress.setZipCode("11111");

        propertyService.addAddress(propertyAddress);

        Property property = new Property();
        property.setName("4성");
        property.setGrade(4);
        property.setIntro("멋진");
        property.setPropertyAddress(propertyAddress);

        propertyService.addProperty(property);
    }

    @Test
    @Transactional
    @Commit
    void testRoom() {
        Property property = propertyService.getProperty(1001);
        for(int i=1;i<=4; i++) {
            Room room2 = new Room();
            room2.setRoomNum(i);
            room2.setName("방"+i);
            room2.setCapacity(4);
            room2.setPrice(2000);
            room2.setProperty(property);
            roomService.addRoom(room2);
        }
    }

    @Test
    @Transactional
    @Commit
    void testReservation(){
        Reservation reservation = new Reservation();
        Customer customer = customerService.findById(1001);
        reservation.setReservationNum("1001");
        reservation.setCustomer(customer);
        List<ReservationRoom> rooms = new ArrayList<>();
        Room room = new Room();
        room.setId(1001);
        ReservationRoom Room = new ReservationRoom(room,parseDate("2024-03-29 14:00:00"), parseDate("2024-03-30 12:00:00"));
        Room room2 = new Room();
        room2.setId(1001);
        ReservationRoom Room2 = new ReservationRoom(room,parseDate("2024-03-29 14:00:00"), parseDate("2024-03-30 12:00:00"));
        Room room3 = new Room();
        room3.setId(1001);
        ReservationRoom Room3 = new ReservationRoom(room,parseDate("2024-03-27 14:00:00"), parseDate("2024-03-28 12:00:00"));
        rooms.add(Room);
        rooms.add(Room2);
        rooms.add(Room3);

        reservationService.addReservation(reservation,rooms);

        rooms = reservationRoomService.findMyReservationRoom("1001");


    }

    @Test
    @Transactional
    @Commit
    void testfindRooms(){
        List<ReservationRoom> rooms = reservationRoomService.findByRoomAndDate(1001,parseDate("2024-03-29 14:00:00"), parseDate("2024-03-30 12:00:00"));
        rooms.forEach(r -> System.out.println(r));
    }

    @Test
    @Transactional
    @Commit
    void testReservationRooms(){
        List<ReservationRoom> reservationRoomList = reservationRoomService.findRooms(1001,parseDate("2024-03-28 14:00:00"), parseDate("2024-03-30 12:00:00"));
        System.out.println(reservationRoomList.size());;
        reservationRoomList.forEach(r -> System.out.println(r));
    }


    void testPayment() {
        Reservation reservation = reservationService.findByReservationNum("1001");
        Payment payment = new Payment();
        payment.setCardNum("22222");
        payment.setCardType("VIA");
        payment.setReservation(reservation);
        payment.setPaymentNumber("212121");

        paymentService.addPayment(reservation, payment , parseDate("2024-03-28 14:00:00"), parseDate("2024-03-30 12:00:00"));
    }


    private Date parseDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return format.parse(dateStr, pos);
    }
}
