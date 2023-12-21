package lovechrono.lovechrono.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "tb_usuario", schema = "lovechrono")
@NoArgsConstructor
public class User extends AbstractEntity {

    @Getter @Setter
    @Column(name= "nome", length = 30, nullable = false)
    private String nome;

    @Getter @Setter
    @Column(name= "email", length = 30, nullable = false, unique = true)
    private String email;

    @Getter @Setter
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name= "dataNascimento", nullable = false)
    private LocalDate dataNascimento;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name="gener", nullable = false)
    private Gener gener;

    @Getter @Setter
    @Column(name= "senha", length = 30, nullable = false)
    private String senha;

    @Getter @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Event> event;
}