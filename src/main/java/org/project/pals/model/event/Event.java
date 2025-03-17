package org.project.pals.model.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.project.pals.model.user.User;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "event")
@Getter @Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    private String eventName;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User>  users;

    private Date eventDate;


}
