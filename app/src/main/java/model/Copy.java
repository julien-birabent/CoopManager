package model;

import java.util.Date;

/**
 * Created by Julien on 22/10/2016.
 */

public class Copy {

    private String copyId;
    private String bookId;
    private Date depositeDate;
    private String availability;
    private String physicalState;


    public Copy(String copyId, String bookId, String availability, String physicalState) {
        this.copyId = copyId;
        this.bookId = bookId;
        this.availability = availability;
        this.physicalState = physicalState;
    }

    public Copy(String availability, String physicalState) {
        this.availability = availability;
        this.physicalState = physicalState;
    }

    public Copy(){

    }

    public Date getDepositeDate() {
        return depositeDate;
    }

    public void setDepositeDate(Date depositeDate) {
        this.depositeDate = depositeDate;
    }

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPhysicalState() {
        return physicalState;
    }

    public void setPhysicalState(String physicalState) {
        this.physicalState = physicalState;
    }

    @Override
    public String toString() {
        return "Copy{" +
                "physicalState='" + physicalState + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }
}
