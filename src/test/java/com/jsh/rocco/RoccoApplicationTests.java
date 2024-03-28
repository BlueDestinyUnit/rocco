package com.jsh.rocco;

import com.jsh.rocco.domains.entities.*;
import com.jsh.rocco.services.CustomerService;
import com.jsh.rocco.services.PropertyService;
import com.jsh.rocco.services.ReservationService;
import com.jsh.rocco.services.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    @Transactional
    @Commit
    void testCustomer() {
        Address address = new Address();
        address.setRegion("대구");
        address.setStreet1("테스트1");
        address.setStreet2("테스트2");
        address.setZipCode("11111");
        customerService.addAddress(address);

        Customer customer = new Customer();
        customer.setPhone("01049333657");
        customer.setBirthDate(new Date());
        customer.setFirstName("테스트1");
        customer.setLastName("테스트1");
        customer.setAddress(address);
        customerService.addCustomer(customer);
    }

    @Test
    @Transactional
    @Commit
    void testProvperty() {
        Property property = new Property();
        property.setAddress("대구");
        property.setName("4성");
        property.setGrade(4);
        property.setIntro("멋진");

        propertyService.addProperty(property);

//        Property property = propertyService.getProperty(1001);



    }

    @Test
    @Transactional
    @Commit
    void testRoom() {

        Property property = propertyService.getProperty(1001);
        Room room = roomService.findUniqueRoomNumber(property.getId(),5);
        if(room != null){
            return;
        }
        room = new Room();
        room.setRoomNum(5);
        room.setName("방5");
        room.setCapacity(100);
        room.setPrice(2000);
        room.setProperty(property);
        roomService.addRoom(room);

        Room room2 = roomService.findUniqueRoomNumber(property.getId(),6);
        if(room2 != null){
            return;
        }
        room2 = new Room();
        room2.setName("방6");
        room2.setRoomNum(6);
        room2.setCapacity(200);
        room2.setPrice(3000);
        room2.setProperty(property);
        roomService.addRoom(room2);

    }

    @Test
    @Transactional
    @Commit
    void testReservation(){
        Reservation reservation = new Reservation();
        Customer customer = customerService.findById(1001);
        reservation.setReservationNum("1001");
        reservation.setCustomer(customer);
        reservationService.addReservation(reservation,1001);
    }

    @Test
    @Transactional
    @Commit
    void testReservationRoom(){
        List<ReservationRoom> rooms = reservationService.findMyReservationRoom("1001");
        System.out.println(rooms.size());;
        rooms.forEach(r -> System.out.println(r));
    }

    @Test
    @Transactional
    @Commit
    void testReservationRooms(){
        List<ReservationRoom> reservationRoomList = roomService.reservationRoomList(1001,3);
        System.out.println(reservationRoomList.size());;
        reservationRoomList.forEach(r -> System.out.println(r));
    }
}
