# WorkshopModelagem

Projeto Spring Boot usando Maven Wrapper.

## Requisitos

- Java 21
- Internet na primeira execucao para baixar as dependencias Maven

## Como executar

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

## Como testar

No Windows:

```powershell
.\mvnw.cmd test
```

No Linux ou macOS:

```bash
./mvnw test
```

## Rotas

- Pagina HTML: `http://localhost:8080/pedidos`
- API de pedidos: `http://localhost:8080/api/pedidos`
