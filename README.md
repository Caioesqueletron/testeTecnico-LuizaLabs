# API de Conversão de Pedidos

## Visão Geral
A API de Conversão de Pedidos é uma aplicação desenvolvida em Spring Boot, projetada para normalizar e processar dados de pedidos desnormalizados provenientes de sistemas legados. Permite que os usuários enviem um arquivo de largura fixa contendo dados de pedidos, processe-o em um formato JSON normalizado e recupere os resultados por meio de endpoints REST. A API também oferece suporte a filtros para pedidos com base em critérios como ID do pedido e intervalo de datas de compra.

## Funcionalidades
- Aceita arquivos de texto de largura fixa via um endpoint da API.
- Normaliza e converte os dados em JSON estruturado.
- Fornece endpoints REST para recuperar os dados processados.
- Suporte a filtros por ID do pedido e intervalo de datas.
- Segue princípios de código limpo e arquitetura SOLID.

## Tecnologias utilizadas
- Java 17 ou superior
- Gradle 7+
- Spring Boot 3.4.+
- Github Actions
- Fly.io
- Solid
- DDD
- Opcional: Docker (para implantação em container)

## Como Começar

### 1. Clone o repositório
```bash
git clone https://github.com/Caioesqueletron/testeTecnico-LuizaLabs.git
cd testeTecnico-LuizaLabs
```

### 2. Build do projeto
Use o Gradle para compilar o projeto:
```bash
./gradlew clean build
```

### 3. Execute a aplicação
```bash
./gradlew bootRun
```
A aplicação estará disponível em `http://localhost:8080`.

## Endpoints

### 1. Enviar e Processar Arquivo
**POST** `/api/orders/process`
- **Descrição:** Aceita um arquivo e processa os dados em JSON normalizado.
- **Parâmetro de Requisição:**
    - `file`: Arquivo multipart contendo os dados dos pedidos.
- **Resposta:** Representação JSON dos dados normalizados.

### 2. Filtrar Pedidos
**GET** `/api/orders/filter`
- **Descrição:** Filtra pedidos processados com base nos critérios fornecidos.
- **Body da Requisição:**
```json
{
  "orderId": 123,
  "startDate": "2021-01-01",
  "endDate": "2021-12-31"
}
```
- **Resposta:** Representação JSON dos pedidos filtrados.

## Exemplo de Arquivo de Entrada
```
0000000002 Medeiros00000123450000000111 256.2420201201
0000000001 Zarelli00000001230000000111 512.2420211201
0000000001 Zarelli00000001230000000122 512.2420211201
0000000002 Medeiros00000123450000000122 256.2420201201
```

## Exemplo de Saída
```json
[
  {
    "user_id": 1,
    "name": "Zarelli",
    "orders": [
      {
        "order_id": 123,
        "total": "1024.48",
        "date": "2021-12-01",
        "products": [
          {
            "product_id": 111,
            "value": "512.24"
          },
          {
            "product_id": 122,
            "value": "512.24"
          }
        ]
      }
    ]
  },
  {
    "user_id": 2,
    "name": "Medeiros",
    "orders": [
      {
        "order_id": 12345,
        "total": "512.48",
        "date": "2020-12-01",
        "products": [
          {
            "product_id": 111,
            "value": "256.24"
          },
          {
            "product_id": 122,
            "value": "256.24"
          }
        ]
      }
    ]
  }
]
```

## Testes
Execute os testes unitários com Gradle:
```bash
./gradlew test
```

## Implantação
Para implantar usando Docker:
1. Construa a imagem Docker:
   ```bash
   docker build -t api-conversao-pedidos .
   ```
2. Execute o container:
   ```bash
   docker run -p 8080:8080 api-conversao-pedidos
   ```

## Fluxo CI/ CD:
![img.png](src/main/java/br/com/luizalabs/teste/tecnico/docs/img.png)


## URL de produção 
- https://damp-darkness-8381.fly.dev/swagger-ui.html

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para fazer um fork do repositório e enviar um pull request.

## Licença
Este projeto está licenciado sob a Licença MIT.

