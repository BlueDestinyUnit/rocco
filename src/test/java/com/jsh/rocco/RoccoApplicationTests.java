package com.jsh.rocco;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsh.rocco.domains.dtos.AvailableRoom;
import com.jsh.rocco.domains.entities.*;
import com.jsh.rocco.domains.enums.roccouser.TelCompany;
import com.jsh.rocco.domains.enums.roccouser.UserRole;
import com.jsh.rocco.repositories.ReservationRepository;
import com.jsh.rocco.repositories.RoccoUserRepository;
import com.jsh.rocco.services.*;
import com.jsh.rocco.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.DateUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private DateUtil dateUtil;


//    @Autowired
//    private UserService userService;

    @Autowired
    private RoccoUserRepository roccoUserRepository;
    @Autowired
    private ReservationRepository reservationRepository;


    @Test
    @Transactional
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
    void testProperty() {
        Property property = new Property();
        property.setName("테스트3");
        property.setGrade(4);
        property.setIntro("멋진");

        propertyService.addProperty(property);
    }

    @Test
    @Transactional
    @Commit
    void testRoom() {
        Property property = propertyService.getProperty(1002);
        for(int i=1;i<=4; i++) {
            Room room2 = new Room();
            room2.setRoomNum(i);
            room2.setName("방2_"+i);
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
        reservation.setReservationNum("1002");
        reservation.setCustomer(customer);
        List<ReservationRoom> rooms = new ArrayList<>();
        Room room = new Room();
        room.setId(1005);
        ReservationRoom Room = new ReservationRoom(room,parseDate("2024-03-29 14:00:00"), parseDate("2024-03-30 12:00:00"));
        Room room2 = new Room();
        room2.setId(1006);
        ReservationRoom Room2 = new ReservationRoom(room,parseDate("2024-03-29 14:00:00"), parseDate("2024-03-30 12:00:00"));
        Room room3 = new Room();
        room3.setId(1007);
        ReservationRoom Room3 = new ReservationRoom(room,parseDate("2024-03-29 14:00:00"), parseDate("2024-03-30 12:00:00"));
        rooms.add(Room);
        rooms.add(Room2);
        rooms.add(Room3);

        reservationService.addReservation(reservation,rooms);

        rooms = reservationRoomService.findMyReservationRoom("1001");

    }

    @Test
    @Transactional
    @Commit
    void testReservation2(){
        Reservation reservation = new Reservation();
        Customer customer = customerService.findById(1001);
        reservation.setReservationNum("1003");
        reservation.setCustomer(customer);
        reservationRepository.save(reservation);

    }



    @Test
    @Transactional
    @Commit
    void testFindRooms(){
        List<ReservationRoom> rooms = reservationRoomService.findByRoomAndDate(1001,parseDate("2024-03-29 14:00:00"), parseDate("2024-03-30 12:00:00"));
        rooms.forEach(r -> System.out.println(r));
    }

    @Test
    @Transactional
    @Commit
    void testReservationRooms(){
        List<ReservationRoom> reservationRoomList = reservationRoomService.findByRoomAndDate(1001,parseDate("2024-03-28 14:00:00"), parseDate("2024-03-30 12:00:00"));
        System.out.println(reservationRoomList.size());;
        reservationRoomList.forEach(r -> System.out.println(r));
    }

//    @Test
//    @Transactional
//    @Commit
//    void testHotel(){
//        List<Room> reservationRoomList = reservationRoomService.findAvailableRooms("대구",parseDate("2024-03-29 14:00:00"), parseDate("2024-03-30 12:00:00"));
//        reservationRoomList.forEach(r -> System.out.println(r.getProperty().getRooms()));
//    }

    
    @Test
    @Transactional
    void testPayment() {
    	List<Room> rooms = new ArrayList<>();
    	Customer customer = customerService.findById(1001);
    	 Room room = new Room();
         room.setId(1001);     
         Room room2 = new Room();
         room2.setId(1001);
         Room room3 = new Room();
         room3.setId(1001);
         rooms.add(room);
         rooms.add(room2);
         rooms.add(room3);

    	reservationService.addReservation2(rooms, customer,parseDate("2024-04-10 14:00:00"), parseDate("2024-04-11 12:00:00"));

        Reservation reservation = reservationService.findByReservationNum("R10001");
        Payment payment = new Payment();
        payment.setCardNum("22222");
        payment.setCardType("VIA");
        payment.setReservation(reservation);
        payment.setPaymentNumber("212121");

        paymentService.addPayment2(reservation, payment , parseDate("2024-04-10 14:00:00"), parseDate("2024-04-11 12:00:00"));
    }

    @Test
    @Transactional
    @Commit
    public void userRegister(){
        RoccoUser roccoUser = new RoccoUser();
        roccoUser.setEmail("test1@test.com");
        roccoUser.setPassword(new BCryptPasswordEncoder().encode("1234"));
        roccoUser.setBirthDate(parseDate("2024-04-10 14:00:00"));
        roccoUser.setTelCompany(TelCompany.SKT);
        roccoUser.setPhone("01011112222");
        roccoUser.setAddressPostal("11111");
        roccoUser.setAddressPrimary("대구 중앙대로 226");
        roccoUser.setAddressSecondary("테스트중입니다.");
        roccoUser.setUserRole(UserRole.USER);

        roccoUserRepository.save(roccoUser);
    }


    private Date parseDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return format.parse(dateStr, pos);
    }

    @Test
    public void date(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = dateFormat.format(parseDate("2024-04-10 14:00:00"));
        System.out.println(formattedDate.substring(1,8));
        System.out.println(String.format("P%s-1", formattedDate));


    }
}
