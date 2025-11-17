package com.example.demo.dto;

import java.util.List;


public class PincodeResponse {
    private String Message;
    private String Status; 
    private List<PostOffice> PostOffice; 

  
    public PincodeResponse() {
    }

    public PincodeResponse(String Message, String Status, List<PostOffice> PostOffice) {
        this.Message = Message;
        this.Status = Status;
        this.PostOffice = PostOffice;
    }

    
    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public List<PostOffice> getPostOffice() {
        return PostOffice;
    }

    public void setPostOffice(List<PostOffice> PostOffice) {
        this.PostOffice = PostOffice;
    }
}
