package com.example.quartz.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Entity
@Table(name = "zero_comission")
public class Settings implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private ZonedDateTime start;

    @Column(name = "end_time")
    private ZonedDateTime end;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

        public Settings() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Settings{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", status='" + status + '\'' +
                '}';
    }
}
