package com.rabo.RaboAssignment.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Records {
    public Records() {
    }

    public Records(List<Record> record) {
        super();
        this.record = record;
    }

    private List<Record> record;

    @XmlElement(name="record")
    public List<Record> getRecord() {
        return record;
    }

    public void setRecord(List<Record> record) {
        this.record = record;
    }
}
