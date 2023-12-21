package lovechrono.lovechrono.repository;

import lovechrono.lovechrono.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    @Query("SELECT event FROM Event event WHERE event.user.id = :userId AND event.ativo = true")
    public List<Event> findByUserId(@Param("userId") Long userId);

    @Query("SELECT event FROM Event event WHERE event.ativo = true")
    public List<Event> findByEventsAtivos();

    @Query("FROM Event WHERE title =:title")
    public List<Event> findByNome(@Param("title") String title);
}