package com.egabi.tls.event;

import org.springframework.context.ApplicationEvent;
public class TestEvent extends ApplicationEvent {
    private String att1;
    public TestEvent(Object source, String att1) {
        super(source);
        this.att1 = att1;
    }

    public String getAtt1() {
        return att1;
    }

    public void setAtt1(String att1) {
        this.att1 = att1;
    }
}
