package com.lipsoft.postt.controller;

import com.lipsoft.postt.dto.request.PostitDTO;
import com.lipsoft.postt.dto.response.MessageResponseDTO;
import com.lipsoft.postt.exception.PostitNotFoundException;
import com.lipsoft.postt.service.PostitService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "AccessLevel")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso na Operação"),
        @ApiResponse(code = 400, message = "Operação invalida; Geralmente ocorre por algum campo inválido"),
        @ApiResponse(code = 401, message = "Sem autorização; Falta de credenciais de autenticação"),
        @ApiResponse(code = 403, message = "Sem permissão para acessar este recurso"),
        @ApiResponse(code = 404, message = "Não encontrado"),
        @ApiResponse(code = 500, message = "Foi gerada uma 'exception'; Erro interno do servidor"),
})
@RestController
@CrossOrigin
@RequestMapping("/api/postit")
public class PostitController {

    private final String DOC_POST_OPERATION = "Cria um postit e o adiciona no banco de dados.";
    private final String DOC_GET_OPERATION_LIST = "Retorna todas os postits registrados no banco de dados.";
    private final String DOC_PUT_OPERATION = "Edita e salva no banco de dados todas as mudancas do postit de acordo com o seu código (ID)";
    private final String DOC_DELETE_OPERATION = "Exclui um postit do banco de dados de acordo com o seu código (ID)";

    @Autowired
    PostitService postitService;

    @ApiOperation(value = DOC_POST_OPERATION)
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid PostitDTO postitDTO) { return postitService.add(postitDTO); }

    @ApiOperation(value = DOC_GET_OPERATION_LIST)
    @GetMapping("/dashboard")
    @ResponseStatus(HttpStatus.OK)
    public List<PostitDTO> fetchAllPostitByUser() {
        return postitService.findAllByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @ApiOperation(value = DOC_PUT_OPERATION)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody @Valid PostitDTO postitDTO) throws PostitNotFoundException { return postitService.update(id, postitDTO); }

    @ApiOperation(value = DOC_DELETE_OPERATION)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDTO delete(@PathVariable Long id) throws PostitNotFoundException { return postitService.delete(id); }
}
