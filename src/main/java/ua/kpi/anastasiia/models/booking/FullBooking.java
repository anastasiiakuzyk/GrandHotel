package ua.kpi.anastasiia.models.booking;

import java.util.Date;

public class FullBooking {
    int guestId;

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String firstName;
    String lastName;
    String guestPhone;
    String passport;
    String email;
    String personalOptions;
    int roomNumber;
    String roomType;
    int totalPrice;
    Date guestArrive;
    Date guestLeave;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalOptions() {
        return personalOptions;
    }

    public void setPersonalOptions(String personalOptions) {
        this.personalOptions = personalOptions;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getGuestArrive() {
        return guestArrive;
    }

    public void setGuestArrive(Date guestArrive) {
        this.guestArrive = guestArrive;
    }

    public Date getGuestLeave() {
        return guestLeave;
    }

    public void setGuestLeave(Date guestLeave) {
        this.guestLeave = guestLeave;
    }
}
