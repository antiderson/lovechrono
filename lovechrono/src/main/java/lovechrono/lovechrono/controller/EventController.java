package lovechrono.lovechrono.controller;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lovechrono.lovechrono.entity.Event;
import lovechrono.lovechrono.repository.EventRepository;
import lovechrono.lovechrono.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
@RequestMapping(value = "/api/event", produces = {"application/json"})
@Tag(name = "Eventos")
public class EventController {

    @Autowired
    public EventService eventService;

    @Autowired
    public EventRepository eventRepository;

    @Operation(summary = "Realiza a busca de todos os eventos cadastrados", method = "GET")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Dados retorndaos com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida")
    })
    @GetMapping
    @CrossOrigin(origins = "http://127.0.0.1:5173")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(this.eventRepository.findByEventsAtivos());
    }

    @Operation(summary = "Realiza a busca de eventos cadastrados por ID", method = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(
            @PathVariable final Long id
    ){
        return ResponseEntity.ok().body(this.eventRepository.findById(id).orElse(new Event()));
    }

    @Operation(summary = "Cadastra um novo evento", method = "POST")
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Event event) {
        this.eventRepository.save(event);
        return ResponseEntity.ok().body("Evento cadastrado com sucesso");
    }

    @Operation(summary = "Atualiza Evento por ID", method = "PUT")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable final Long id,
            @RequestBody Event updatedEvent
    ) {
        try {
            Event existingEvent = eventService.findById(id);

            if (existingEvent != null) {
                existingEvent.setTitle(updatedEvent.getTitle());
                existingEvent.setDescription(updatedEvent.getDescription());
                existingEvent.setDataAcontecimento(updatedEvent.getDataAcontecimento());
                existingEvent.setLocal(updatedEvent.getLocal());
                existingEvent.setMusic(updatedEvent.getMusic());
                existingEvent.setPicture(updatedEvent.getPicture());

                existingEvent.setUser(existingEvent.getUser());

                eventService.atualizar(id, existingEvent);
                return ResponseEntity.ok().body("Evento atualizado com sucesso");
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
//            updatedEvent.setId(id);  // Garanta que o ID esteja definido corretamente
//            this.eventRepository.save(updatedEvent);
//            return ResponseEntity.ok().body("Evento atualizado com sucesso");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
    }

    @Operation(summary = "Deleta evento por ID", method = "DELETE")
    @DeleteMapping("/deleta/{id}")
    public ResponseEntity<?> excluir(
            @PathVariable final Long id
    ) {
        try {
            this.eventService.excluir(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Evento deletado com sucesso");
    }
}