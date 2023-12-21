package lovechrono.lovechrono.service;

import lovechrono.lovechrono.entity.Event;
import lovechrono.lovechrono.entity.User;
import lovechrono.lovechrono.repository.EventRepository;
import lovechrono.lovechrono.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public User findById(Long id){
        return this.userRepository.findById(id).orElse(new User());
    }

    public List<User> findAll(){
        return this.userRepository.findByUsuariosAtivos();
    }

    public List<Event> getEventsByUserId(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return eventRepository.findByUserId(user.getId());
        }
        return Collections.emptyList(); //Usuário nao encontrado, retorna uma lista vazia
    }

    @Transactional
    public User save(User user){
        return this.userRepository.save(user);

    }

    @Transactional
    public void atualizar(final Long id, final User user) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent() && id.equals(optionalUser.get().getId())) {
            this.userRepository.save(user);
        } else {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + id);
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
    public void excluir(final Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            List<Long> idList = Collections.singletonList(id);
            this.userRepository.deleteAllById(idList);
        } else {
            throw new RuntimeException("Id não encontrado");
        }
    }
}
