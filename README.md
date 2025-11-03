# Sistema de Gestão de Ordens de Serviço (OS)

## Informações do Projeto

**Disciplina:** Arquitetura Java [25E4_2]
**Aluno:** Paulo Almeida  

## Resumo do Projeto

Sistema completo de gestão de ordens de serviço desenvolvido com **Java 17**, **Spring Boot 3.5.6** e seguindo os princípios de **Domain-Driven Design (DDD)**. O projeto implementa todas as 4 features solicitadas, foram utilizados os seguintes conceitos:

- Herança e Polimorfismo
- Estruturas de Dados
- Ambiente de Desenvolvimento com Spring
- Arquitetura em Camadas (DDD)
- Spring Boot com Jakarta EE
- API REST Spring Boot
- Persistência com Spring Boot (JPA/Hibernate)

---

## Arquitetura

O projeto segue uma **arquitetura em camadas baseada em DDD**:

```
src/main/java/br/edu/infnet/pauloapi/
│
├── domain/                         # Camada de Domínio
│   ├── model/                      # Entidades de domínio
│   │   ├── Pessoa.java             # Classe abstrata (herança)
│   │   ├── Cliente.java            # Herda de Pessoa
│   │   ├── Tecnico.java            # Herda de Pessoa
│   │   ├── OrdemServico.java       # Classe abstrata (polimorfismo)
│   │   ├── OSManutencao.java       # Especialização de OS
│   │   ├── OSInstalacao.java       # Especialização de OS
│   │   ├── OSConsultoria.java      # Especialização de OS
│   │   └── StatusOS.java           # Enum
│   └── exception/                  # Exceções de domínio
│       ├── ResourceNotFoundException.java
│       └── BusinessException.java
│
├── application/                    # Camada de Aplicação
│   ├── dto/                        # Data Transfer Objects
│   │   ├── ClienteDTO.java
│   │   ├── TecnicoDTO.java
│   │   ├── OrdemServicoDTO.java
│   │   ├── OSManutencaoDTO.java
│   │   ├── OSInstalacaoDTO.java
│   │   └── OSConsultoriaDTO.java
│   └── service/                    # Services (regras de negócio)
│       ├── ClienteService.java
│       ├── TecnicoService.java
│       └── OrdemServicoService.java
│
├── infrastructure/                 # Camada de Infraestrutura
│   ├── repository/                 # Repositories (Spring Data JPA)
│   │   ├── ClienteRepository.java
│   │   ├── TecnicoRepository.java
│   │   └── OrdemServicoRepository.java
│   ├── config/                     # Configurações
│   │   └── ModelMapperConfig.java
│   ├── exception/                  # Tratamento global de exceções
│   │   ├── GlobalExceptionHandler.java
│   │   ├── ErrorResponse.java
│   │   └── ValidationErrorResponse.java
│   └── loader/                     # Carregadores de dados
│       └── DataLoader.java
│
└── presentation/                   # Camada de Apresentação
    └── controllers/                # Controllers REST
        ├── ClienteController.java
        ├── TecnicoController.java
        ├── OrdemServicoController.java
        ├── DashboardController.java
        └── RelatorioController.java
```

---

## Features Implementadas

### Feature 1
- Classes de domínio criadas (Cliente, Tecnico, OrdemServico)
- Classes Loader para carregamento de informação (DataLoader)
- Classes Service para manipular informações (ClienteService, TecnicoService, OrdemServicoService)
- Injeção de dependência para relacionar Loader e Service

### Feature 2
- Configuração de criação do banco de dados (H2 com JPA)
- Relacionamentos entre entidades (OneToMany, ManyToOne)
- Interfaces Repository com funcionalidades de banco (Spring Data JPA)
- Injeção de dependência para relacionar Service e Repository

### Feature 3
- Camada frontend (Controllers REST) para visualizar dados
- Camada controladora para comunicação (REST Controllers com validação)
- Validação e tratamento de exceções (@Valid, GlobalExceptionHandler)
- Cliente para consumir API (Swagger UI integrado)

### Feature 4
- Processo de exclusão de registros implementado
- Processo de Sort e busca por dados (Query Methods)
- API para gestão das informações (CRUD completo)
- Endpoints necessários para CRUD (GET, POST, PUT, PATCH, DELETE)

---

## 🔧 Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.6** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Hibernate** - ORM
- **H2 Database** - Banco de dados em memória
- **Lombok** - Redução de boilerplate
- **ModelMapper** - Mapeamento DTO <-> Entity
- **SpringDoc OpenAPI** - Documentação da API (Swagger)
- **Jakarta Validation** - Validação de dados
- **Maven** - Gerenciamento de dependências
- **Docker** - Containerização

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior
- Docker (opcional)

### Opção 1: Executar localmente com Maven

```bash
# Clone o repositório
git clone https://github.com/yihongz/infnet-java-pauloapi.git
cd pauloapi

# Compile e execute
./mvnw clean install
./mvnw spring-boot:run
```

### Opção 2: Executar com Docker

```bash
# Build e execução com Docker Compose
docker-compose up --build

# Ou build manual
docker build -t gestao-os-api .
docker run -p 8080:8080 gestao-os-api
```

---

## Acessando a Aplicação

Após iniciar a aplicação, você pode acessar:

### Swagger UI (Documentação Interativa da API)
```
http://localhost:8080/swagger-ui.html
```

### H2 Console (Banco de Dados)
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:gestao_os
Username: sa
Password: (deixar em branco)
```

### Endpoints da API

#### Clientes
- `GET /api/clientes` - Listar todos os clientes
- `GET /api/clientes/{id}` - Buscar cliente por ID
- `GET /api/clientes/cpf/{cpf}` - Buscar cliente por CPF
- `GET /api/clientes/buscar?nome={nome}` - Buscar clientes por nome
- `GET /api/clientes/com-os-aberta` - Listar clientes com OS em aberto
- `POST /api/clientes` - Criar novo cliente
- `PUT /api/clientes/{id}` - Atualizar cliente
- `DELETE /api/clientes/{id}` - Deletar cliente

#### Técnicos
- `GET /api/tecnicos` - Listar todos os técnicos
- `GET /api/tecnicos/{id}` - Buscar técnico por ID
- `GET /api/tecnicos/matricula/{matricula}` - Buscar técnico por matrícula
- `GET /api/tecnicos/especialidade/{especialidade}` - Buscar por especialidade
- `GET /api/tecnicos/ativos` - Listar técnicos ativos
- `GET /api/tecnicos/disponiveis` - Listar técnicos disponíveis
- `POST /api/tecnicos` - Criar novo técnico
- `PUT /api/tecnicos/{id}` - Atualizar técnico
- `PATCH /api/tecnicos/{id}/inativar` - Inativar técnico
- `DELETE /api/tecnicos/{id}` - Deletar técnico

#### Ordens de Serviço
- `GET /api/ordens-servico` - Listar todas as OS
- `GET /api/ordens-servico/{id}` - Buscar OS por ID
- `GET /api/ordens-servico/status/{status}` - Buscar por status
- `GET /api/ordens-servico/cliente/{clienteId}` - Buscar por cliente
- `GET /api/ordens-servico/tecnico/{tecnicoId}` - Buscar por técnico
- `GET /api/ordens-servico/antigas?dias={dias}` - Buscar OS antigas
- `POST /api/ordens-servico/manutencao` - Criar OS de Manutenção
- `POST /api/ordens-servico/instalacao` - Criar OS de Instalação
- `POST /api/ordens-servico/consultoria` - Criar OS de Consultoria
- `PATCH /api/ordens-servico/{id}/concluir` - Concluir OS
- `PATCH /api/ordens-servico/{id}/cancelar` - Cancelar OS
- `PATCH /api/ordens-servico/{id}/status?status={status}` - Atualizar status
- `DELETE /api/ordens-servico/{id}` - Deletar OS

---

## Conceitos Demonstrados

### 1. Herança e Polimorfismo

**Herança de Pessoa:**
```java
// Classe abstrata base
public abstract class Pessoa {
    private Long id;
    private String nome;
    private String telefone;
}

// Especializações
public class Cliente extends Pessoa {
    private String cpf;
    private String endereco;
    // ...
}

public class Tecnico extends Pessoa {
    private String matricula;
    private String especialidade;
    // ...
}
```

**Polimorfismo em OrdemServico:**
```java
// Classe abstrata com método polimórfico
public abstract class OrdemServico {
    // ...
    public abstract BigDecimal calcularValorTotal();
}

// Implementações específicas
public class OSManutencao extends OrdemServico {
    @Override
    public BigDecimal calcularValorTotal() {
        return BigDecimal.valueOf(horasTrabalhadas)
                .multiply(valorHora).add(getValor());
    }
}

public class OSInstalacao extends OrdemServico {
    @Override
    public BigDecimal calcularValorTotal() {
        return BigDecimal.valueOf(quantidade)
                .multiply(valorUnitario).add(getValor());
    }
}
```

### 2. Estruturas de Dados

- **Listas:** `List<OrdemServico>` para relacionamentos OneToMany
- **Maps:** Utilizados internamente pelo JPA para cache
- **Enums:** `StatusOS` para estados da OS
- **DTOs:** Estruturas para transferência de dados

### 3. Relacionamentos JPA

```java
// OneToMany no Cliente
@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
private List<OrdemServico> ordensServico = new ArrayList<>();

// ManyToOne na OrdemServico
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "cliente_id", nullable = false)
private Cliente cliente;
```

### 4. Query Methods

```java
// Exemplos de Query Methods no Repository
Optional<Cliente> findByCpf(String cpf);
List<Cliente> findByNomeContainingIgnoreCase(String nome);
List<Tecnico> findByAtivoTrue();
List<OrdemServico> findByStatus(StatusOS status);

// JPQL personalizado
@Query("SELECT c FROM Cliente c WHERE c.endereco LIKE %:cidade%")
List<Cliente> findByEnderecoCidade(@Param("cidade") String cidade);
```

### 5. Validação de Dados

```java
@NotBlank(message = "Nome é obrigatório")
private String nome;

@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", 
         message = "CPF deve estar no formato XXX.XXX.XXX-XX")
private String cpf;

@Email(message = "Email deve ser válido")
private String email;
```

### 6. Tratamento Global de Exceções

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(...) {
        // Retorna 404 NOT FOUND
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(...) {
        // Retorna 400 BAD REQUEST
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(...) {
        // Retorna 400 com detalhes dos erros de validação
    }
}
```

---

## Testando a API

### Exemplo: Criar um Cliente

```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "cpf": "123.456.789-00",
    "telefone": "(11) 98765-4321",
    "endereco": "Rua das Flores, 123",
    "email": "joao@email.com"
  }'
```

### Exemplo: Criar uma OS de Manutenção

```bash
curl -X POST http://localhost:8080/api/ordens-servico/manutencao \
  -H "Content-Type: application/json" \
  -d '{
    "descricao": "Manutenção preventiva",
    "status": "ABERTA",
    "valor": 150.00,
    "clienteId": 1,
    "tecnicoId": 1,
    "horasTrabalhadas": 8,
    "valorHora": 80.00,
    "tipoManutencao": "Preventiva"
  }'
```

---

## Dados de Teste

A aplicação já vem com dados pré-carregados através do `DataLoader`:

- **4 Clientes** cadastrados
- **4 Técnicos** cadastrados
- **5 Ordens de Serviço** (de diferentes tipos e status)

---

## Padrões e Boas Práticas

### Padrões Implementados

1. **Repository Pattern** - Abstração da camada de dados
2. **Service Layer Pattern** - Lógica de negócio centralizada
3. **DTO Pattern** - Separação entre domínio e apresentação
4. **Dependency Injection** - Inversão de controle com Spring
5. **Strategy Pattern** - Polimorfismo em OrdemServico
6. **Builder Pattern** - Lombok @Builder (implícito)

### Boas Práticas

- Código limpo e bem documentado
- Separação de responsabilidades (SRP)
- Injeção de dependência via construtor
- Uso de interfaces para contratos
- Validação em múltiplas camadas
- Tratamento adequado de exceções
- Logging estruturado
- Transações gerenciadas pelo Spring
- API RESTful com códigos HTTP semânticos
- Documentação automática com Swagger

---

**Desenvolvido por Paulo Almeida**
