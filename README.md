Aqui está um exemplo de documentação para o seu projeto `com.brasil.api`, focado na implementação de paginação com Java 17, que pode ser usado para consulta e exemplo de uso da API:

---

# com.brasil.api

Este é um exemplo de aplicação desenvolvida em **Java 17** para realizar consultas a uma API pública com suporte à **paginaçã**o.

### Tecnologias Utilizadas:
- **Java 17** para desenvolvimento do backend.
- **Spring Boot** para construir a API REST.
- **Swagger** para documentação e testes da API.
- **Feign Client** para integração com APIs externas.

## Descrição

A API foi construída para fazer o consumo de dados da **Brasil API**, utilizando a **paginacão** para facilitar a consulta de grandes volumes de dados. Ela permite que você faça consultas paginadas a partir de parâmetros como `page` e `size`, com um controle de limite para evitar sobrecarga nos servidores.

### Funcionalidades:

1. **Consulta Paginada de Corretoras**: A API permite consultar informações sobre corretoras através de paginação.
2. **Limite de Itens por Página**: Para garantir a estabilidade da aplicação, o número de itens por página está limitado.
3. **Verificação de Páginas**: A API verifica se a página solicitada está dentro dos limites válidos.

## Como Funciona a Paginação

A lógica de paginação é implementada através de um conjunto de parâmetros:

- **page** (padrão: `0`): Define o número da página a ser retornada.
- **size** (padrão: `30`): Define a quantidade de itens por página.

O sistema calcula as páginas disponíveis com base na quantidade total de itens e divide-os conforme o número de itens por página.

### Exemplo de Requisição

A requisição para consultar as corretoras é feita através do endpoint `/api/corretoras`.

```http
GET /api/corretoras?page=1&size=30
```

### Resposta:

A resposta para a consulta paginada vem no formato:

```json
{
  "totalItems": 100,
  "totalPages": 4,
  "page": 1,
  "size": 30,
  "items": [
    { "id": 1, "name": "Corretora 1" },
    { "id": 2, "name": "Corretora 2" },
    ...
  ]
}
```

### Detalhes dos Campos:

- `totalItems`: Total de itens disponíveis.
- `totalPages`: Total de páginas calculadas com base no número de itens.
- `page`: A página atual solicitada.
- `size`: Número de itens por página.
- `items`: Lista de corretoras retornadas.

## Implementação

### Dependências no `pom.xml`

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

### Classe `CorretorasService`

O serviço que faz a lógica de paginação para as corretoras é implementado da seguinte forma:

```java
@Service
public class CorretorasService {

    private final CorretorasFeignClient corretorasFeignClient;
    private List<Object> cachedCorretoras;
    private boolean isDataLoaded = false;

    public CorretorasService(CorretorasFeignClient corretorasFeignClient) {
        this.corretorasFeignClient = corretorasFeignClient;
    }

    public PagedResponse<Object> getAllCorretoras(int page, int size) {
        try {
            if (!isDataLoaded) {
                cachedCorretoras = corretorasFeignClient.getCorretoras();
                isDataLoaded = true;
            }

            if (cachedCorretoras == null || cachedCorretoras.isEmpty()) {
                return new PagedResponse<>(0, 0, page, size, Collections.emptyList());
            }

            int totalItems = cachedCorretoras.size();
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, totalItems);
            List<Object> paginatedCorretoras = cachedCorretoras.subList(startIndex, endIndex);
            int totalPages = totalItems / size;

            return new PagedResponse<>(totalItems, totalPages, page, size, paginatedCorretoras);
        } catch (Exception e) {
            logger.error("Erro ao buscar corretoras: " + e.getMessage());
            return new PagedResponse<>(0, 0, page, size, Collections.emptyList());
        }
    }
}
```

### Classe `CorretorasController`

No controlador, a lógica de validação de parâmetros e paginação é realizada da seguinte forma:

```java
@RestController
@RequestMapping("/api")
public class CorretorasController {

    private final CorretorasService corretorasService;
    private final int MAX_PAGE_SIZE = 30;

    public CorretorasController(CorretorasService corretorasService) {
        this.corretorasService = corretorasService;
    }

    @ApiOperation(value = "Obter corretoras")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso", response = PagedResponse.class),
        @ApiResponse(code = 400, message = "Erro de solicitação"),
        @ApiResponse(code = 404, message = "Página não encontrada")
    })
    @GetMapping(value = "/corretoras", produces = "application/json")
    public PagedResponse<Object> getCorretoras(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "30") int size) {

        if (size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("O número máximo de itens por página é " + MAX_PAGE_SIZE);
        }

        size = Math.min(size, MAX_PAGE_SIZE);

        PagedResponse<Object> pagedResponse = corretorasService.getAllCorretoras(page, size);

        if (page > pagedResponse.getTotalPages()) {
            throw new IllegalArgumentException("A página solicitada excede o número total de páginas.");
        }

        return pagedResponse;
    }
}
```

### Configuração do Swagger

Para gerar a documentação automática usando o Swagger, você pode configurar o Swagger com o Springfox:

```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.brasil.api"))
                .paths(PathSelectors.any())
                .build();
    }
}
```

Acesse a documentação Swagger em `http://localhost:8080/swagger-ui.html` para ver a API e interagir com ela.

---

Essa documentação serve como exemplo para que os desenvolvedores possam implementar e consumir a API com paginação. Você pode adaptar os detalhes conforme a necessidade do seu projeto.

