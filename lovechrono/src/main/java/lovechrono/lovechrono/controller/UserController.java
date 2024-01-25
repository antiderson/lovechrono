package lovechrono.lovechrono.controller;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
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
@RequestMapping(value = "/api/users", produces = {"application/json"})
@Tag(name = "Usuários")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public UserRepository userRepository;

    @Operation(summary = "Realiza a busca de  todos os usuários cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retorndaos com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida")
    })
    @GetMapping
    @CrossOrigin(origins = "http://127.0.0.1:5173")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(this.userRepository.findByUsuariosAtivos());
    }

    @Operation(summary = "Realiza a busca de usuários por ID", method = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(
            @PathVariable final Long id
    ) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        return ResponseEntity.ok().body(this.userRepository.findById(id).orElse(new User()));
    }

    @Operation(summary = "Cadastra um novo usuário", method = "POST")
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final User user) {
        this.userRepository.save(user);
        return ResponseEntity.ok().body("Usuário cadastrado com sucesso");
    }

    @Operation(summary = "Atualiza um usuário por ID", method = "PUT")
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

    @Operation(summary = "Deleta um usuário por ID", method = "DELETE")
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