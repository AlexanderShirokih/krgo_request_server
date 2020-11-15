package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "request_set")
public class RequestSetDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 24, nullable = false)
    private String name;

    @Column
    private Date date;

    @OneToMany(mappedBy = "requestSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestDTO> requests;
}
