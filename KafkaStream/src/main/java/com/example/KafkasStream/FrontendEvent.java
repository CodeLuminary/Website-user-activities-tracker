package com.example.KafkasStream;

public class FrontendEvent {

    private String type;
    private String page;
    private String element;
    private long ts;

    public FrontendEvent() {}

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPage() { return page; }
    public void setPage(String page) { this.page = page; }

    public String getElement() { return element; }
    public void setElement(String element) { this.element = element; }

    public long getTs() { return ts; }
    public void setTs(long ts) { this.ts = ts; }
}
