package lovechrono.lovechrono.service;

import lovechrono.lovechrono.entity.Event;
import lovechrono.lovechrono.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    public EventRepository eventRepository;

    public Event findById(Long id){
        return this.eventRepository.findById(id).orElse(new Event());
    }

    public List<Event> findAll(){
        return this.eventRepository.findByEventsAtivos();
    }

    @Transactional
    public Event event(Event event){
        return this.eventRepository.save(event);
    }

    @Transactional
    public void atualizar(final Long id, final Event event){
        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        if(optionalEvent.isPresent() && id.equals(optionalEvent.get().getId())){
            eventRepository.save(event);
//            this.eventRepository.save(event);
        }else{
            throw new RuntimeException("Id não encontrado");
        }
    }

    @ControllerAdvice
    public class RestExceptionHandler extends ResponseEntityExceptionHandler{

        @ExceptionHandler(EntityNotFoundException.class)
        protected ResponseEntity<Object> handleEntityNotFound(
                EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @Transactional
    public void excluir(final Long id){
        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        if(optionalEvent.isPresent()){
            List<Long> idList= Collections.singletonList(id);
            this.eventRepository.deleteAllById(idList);
        }else{
            throw new RuntimeException("Id não encontrao");
        }
    }
}
