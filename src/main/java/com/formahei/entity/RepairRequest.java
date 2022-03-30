package com.formahei.entity;

/**
 * Repair request entity
 * @author Anastasiia Formahei
 * */

public class RepairRequest extends Entity{

    int id;
    String description;
    String dateTime;
    Integer price;
    String status;
    String state;
    String client;
    String master;
    int feedback;

    public RepairRequest(RequestBuilder requestBuilder) {
        this.description = requestBuilder.description;
        this.dateTime = requestBuilder.dateTime;
        this.price = requestBuilder.price;
        this.status = requestBuilder.status;
        this.state = requestBuilder.state;
        this.client = requestBuilder.client;
        this.master = requestBuilder.master;
        this.feedback = requestBuilder.feedback;
    }
    @Override
    public int getId() {
        return id;
    }
    @Override
    public RepairRequest setId(int id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Integer getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getState() {
        return state;
    }

    public String getClient() {
        return client;
    }

    public String getMaster() {
        return master;
    }
    public int getFeedback() {
        return feedback;
    }

    public RepairRequest setFeedback(int feedback) {
        this.feedback = feedback;
        return this;
    }

    @Override
    public String toString() {
        return "RepairRequest{" +
                "id='" + id + '\'' +
                "description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", state='" + state + '\'' +
                ", client='" + client + '\'' +
                ", master='" + master + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }

    /**
     * RequestBuilder - implementation of builder pattern for RepairRequest
     * */
    public static class RequestBuilder{
        String description;
        String dateTime;
        Integer price;
        String status;
        String state;
        String client;
        String master;
        int feedback;

        public RequestBuilder(){}

        public RequestBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public RequestBuilder withDateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public RequestBuilder withPrice(Integer price) {
            this.price = price;
            return this;
        }

        public RequestBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public RequestBuilder withState(String state) {
            this.state = state;
            return this;
        }

        public RequestBuilder withClient(String client) {
            this.client = client;
            return this;
        }

        public RequestBuilder withMaster(String master) {
            this.master = master;
            return this;
        }

        public RepairRequest build(){
            return new RepairRequest(this);
        }
    }

}
