package lovechrono.lovechrono.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_event", schema = "lovechrono")
@NoArgsConstructor

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Event extends AbstractEntity {

    @Getter @Setter
    @JoinColumn(name= "id_user")
    @ManyToOne
    @JsonBackReference
    private User user;

    @Getter @Setter
    @Column(name= "title", length = 100, nullable = false)
    private String title;

    @Getter @Setter
    @Column(name= "description", length = 255, nullable = false)
    private String description;

    @Getter @Setter
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name= "dataAcontecimento", nullable = false)
    private LocalDate dataAcontecimento;

    @Getter @Setter
    @Column(name= "local", length = 30, nullable = false)
    private String local;

    @Getter @Setter
    @Column(name= "music", length = 30, nullable =true)
    private String music;

    @Getter @Setter
    @Column(name= "picture", length = 30, nullable = true)
    private String picture;
}