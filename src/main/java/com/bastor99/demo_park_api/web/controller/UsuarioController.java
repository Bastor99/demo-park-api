package com.bastor99.demo_park_api.web.controller;

import com.bastor99.demo_park_api.entity.Usuario;
import com.bastor99.demo_park_api.service.UsuarioService;
import com.bastor99.demo_park_api.web.dto.UsuarioCreateDto;
import com.bastor99.demo_park_api.web.dto.UsuarioResponseDto;
import com.bastor99.demo_park_api.web.dto.UsuarioSenhaDto;
import com.bastor99.demo_park_api.web.dto.mapper.UsuarioMapper;
import com.bastor99.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                @ApiResponse(responseCode = "409", description = "Usuario e-mail já cadastrado no sistema",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalídos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto){
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @Operation(summary = "Recuperar um usuário pelo id", description = "Recuperar um usuário pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @Operation(summary = "Atualizar senha", description = "Atualizar senha",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos ou mal formatados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto){
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Recuperar todos usuários", description = "Recuperar todos usuários",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class))
                            ))
            }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
