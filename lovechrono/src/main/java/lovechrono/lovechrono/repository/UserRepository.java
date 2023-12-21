package lovechrono.lovechrono.repository;

import lovechrono.lovechrono.entity.Gener;
import lovechrono.lovechrono.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value ="select * from lovechrono.tb_usuario where id=:id", nativeQuery = true)
    public Optional<User> findById(@Param("id") final Long id);

    @Query("SELECT user FROM User user WHERE user.ativo = true")
    public List<User> findByUsuariosAtivos();

    @Query("from User where nome = :nome")
    public List<User> findByNome(@Param("nome") String nome);


    @Query("from User where email = :email")
    public List<User> findByEmail(@Param("email") String email);

    @Query("from User where dataNascimento = :dataNascimento")
    public List<User> findByDataNascimento(@Param("dataNascimento") LocalDate dataNascimento);

    @Query("from User where gener = :genero")
    public List<User> findByGenero(@Param("genero") Gener gener);
}
