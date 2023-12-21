package lovechrono.lovechrono.controller;

import lovechrono.lovechrono.entity.User;
import org.springframework.http.ResponseEntity;
import lovechrono.lovechrono.repository.UserRepository;
import lovechrono.lovechrono.service.UserService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public UserRepository userRepository;

    @GetMapping
    @CrossOrigin(origins = "http://127.0.0.1:5173")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(this.userRepository.findByUsuariosAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(
            @PathVariable final Long id
    ) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        return ResponseEntity.ok().body(this.userRepository.findById(id).orElse(new User()));
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final User user) {
        this.userRepository.save(user);
        return ResponseEntity.ok().body("Usuário cadastrado com sucesso");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable final Long id,
            @RequestBody User updatedUser
    ) {
        try {
            updatedUser.setId(id);  // Garanta que o ID esteja definido corretamente
            this.userRepository.save(updatedUser);
            return ResponseEntity.ok().body("Usuário atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleta/{id}")
    public ResponseEntity<?> excluir(
            @PathVariable final Long id
    ) {
        try {
            this.userService.excluir(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Usuário deletado com sucesso");
    }
}