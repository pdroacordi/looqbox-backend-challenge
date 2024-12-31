package io.acordi.looqboxbackendchallenge.entrypoint.handler.response;

public class ExceptionResponse {
    private String message;
    private long timestamp;

    public ExceptionResponse(Builder builder) {
        this.message = builder.message;
        this.timestamp = builder.timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static class Builder{
        private String message;
        private long timestamp;

        public Builder message(String message){
            this.message = message;
            return this;
        }
        public Builder timestamp(long timestamp){
            this.timestamp = timestamp;
            return this;
        }

        public ExceptionResponse build(){
            return new ExceptionResponse(this);
        }
    }
}
