# Back-end
Framework: Spark

Não esqueça de colocar os dados de conexão do banco em: `server/src/main/java/application/Connection.java`

# Front-end
Framework: Angular 2+

Por padrão, o endpoint da API está configurado para o endereço `http://localhost:4567` e WebSocket em `ws://localhost:4567/websocket`. Ambos endereços configurados em `client/src/environments/environment.ts`

# Database

```
CREATE DATABASE interview;

CREATE TABLE settings (
    id SERIAL primary key,
	current_normal_queue integer not null,
	current_preferential_queue integer not null
);  

CREATE TABLE queue (
	id SERIAL primary key,
	number integer not null,
	type integer not null
);

CREATE TABLE history (
	id SERIAL primary key,
	number integer not null,
	type integer not null
);

INSERT INTO settings VALUES (DEFAULT, 0, 0);
```
