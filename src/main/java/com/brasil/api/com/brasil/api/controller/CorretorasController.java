package com.brasil.api.com.brasil.api.controller;

import com.brasil.api.com.brasil.api.model.PagedResponse;
import com.brasil.api.com.brasil.api.service.CorretorasService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.brasil.api.com.brasil.api.constants.Constants.MAX_PAGE_SIZE;

@RestController
@RequestMapping("/api")
public class CorretorasController {
    private final CorretorasService corretoras;

    public CorretorasController(CorretorasService corretoras) {
        this.corretoras = corretoras;
    }

    @ApiOperation(value = "Obter corretoras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso", response = PagedResponse.class),
            @ApiResponse(code = 400, message = "Erro de solicitação"),
            @ApiResponse(code = 401, message = "Não autorizado - Usuário não autenticado"),
            @ApiResponse(code = 403, message = "Proibido - Usuário não tem permissão"),
            @ApiResponse(code = 404, message = "Não encontrado - A URL solicitada não foi encontrada"),
            @ApiResponse(code = 500, message = "Erro interno do servidor - Erro inesperado"),
            @ApiResponse(code = 503, message = "Serviço Indisponível - O serviço está temporariamente indisponível")
    })
    @GetMapping(value = "/corretoras", produces = "application/json")
    public PagedResponse<Object> getUsers(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "30") int size) {
        if (size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("O número máximo de itens por página é " + MAX_PAGE_SIZE);
        }

        size = Math.min(size, MAX_PAGE_SIZE);
        PagedResponse<Object> pagedResponse = this.corretoras.getAllCorretoras(page, size);

        if (page > pagedResponse.getTotalPages()) {
            throw new IllegalArgumentException(
                    String.format("A página solicitada (%d) excede o número total de páginas disponíveis".concat(String.valueOf(pagedResponse.getTotalPages())),
                            page)
            );
        }

        return pagedResponse;
    }
}


