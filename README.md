# Deploy
Aplicación en el dominio https://onep1.herokuapp.com/

Para la autentificación de usuarios (tanto registro como login) es necesario usar el prefijo (/api/auth)
Para acceder al login (/signin). Es necesario usar LoginRequest (en el paquete com.cerea_p1.spring.jpa.postgresql.payload.request). Devuelve ResponseEntity (en formato JSON) con la información de nombre de usuario, email, pais y puntos.

Para registrar un usuario se usa (/signup). Es necesario usar SignupRequest (en el paquete com.cerea_p1.spring.jpa.postgresql.payload.request) y devuelve un mensaje de ok del formato que se muestra en el ficero com.cerea_p1.spring.jpa.postgresql.payload.response.MessageResponse.

Registro de usuario:

  - Petición POST a https://onep1.herokuapp.com/api/auth/signup
  
  - JSON:
  
        {
          "username": <nombre_de_usuario>,
          "email": <email>,
          "pais": <pais>,
          "password": <contraseña>
        }
      
  - Información adicional:
  
      - username de entre 3 y 255 caracteres
        
      - password de entre 6 y 255 caracteres
        
      - email que cumpla la siguiente ER: .+[@].+[\\.].+ (algo + @ + algo + . + algo). Máximo 255 caracteres
        
      - pais de máximo 255 caracteres

Logear un usuario:

  - Petición POST a https://onep1.herokuapp.com/api/auth/signin

  - JSON:
      
        {
          "username": <nombre_de_usuario>,
          "password": <contraseña>
        }

Crear una partida:
  
  - Petición POST a https://onep1.herokuapp.com/game/create

  - JSON:

        {
            "playername": <nombre_del_usuario>,
            "nplayers": <numero_de_jugadores>,
            "tturn": <tiempo_de_turno>,
            "rules": [
              {
                [CERO_SWITCH, CRAZY_7, PROGRESSIVE_DRAW, CHAOS_DRAW, BLOCK_DRAW, REPEAT_DRAW]
              },
              {
                [CERO_SWITCH, CRAZY_7, PROGRESSIVE_DRAW, CHAOS_DRAW, BLOCK_DRAW, REPEAT_DRAW]
              }
              ...
            ]
        }

  - Devuelve: 

        {
          "jugadores": [
            {
              "nombre": <nombre_de_usuario>,
              "cartas": []
            },
          ],
          "id": <id_partida>,
          "tipo": true
        }

    - Si va mal: codigo 4**, y por qué falla

Enviar una petición de amistad
  - Petición POST a https://onep1.herokuapp.com/friends/send/friend-request

  - JSON:

        {
          "username": <nombre_de_usuario>,
          "friendname": <nombre_del_amigo>
        }

  - Devuelve: 
    - Si va bien: codigo 200, Petición de amistad enviada a " + <nombre_del_amigo>
    - Si va mal: codigo 4**, y por qué falla

Ver invitaciones de amistad
  - Petición POST a https://onep1.herokuapp.com/friends/receive/friend-request

  - JSON:

          {
            "username": <nombre_de_usuario>
          }

  - Devuelve: 
    - Si va bien: codigo 200, 

          {
            "message": "[ <nombre_del_amigo1>, <nombre_del_amigo2>, ...]"
          }

    - Si va mal: codigo 4**, y por qué falla

Aceptar invitación de amistad
  - Petición POST a https://onep1.herokuapp.com/friends/accept/friend-request

  - JSON:

          {
            "username": <nombre_de_usuario>,
            "friendname": <nombre_del_amigo>
          }
          
  - Devuelve: 
    - Si va bien: codigo 200
     
          {
            "message": "Amigo añadido: <nombre_del_amigo>"
          }
          
    - Si va mal: codigo 4**, y por qué falla

Ver lista de amigos
  - Petición POST a https://onep1.herokuapp.com/friends/friendsList

  - JSON:

          {
            "username": <nombre_de_usuario>
          }
          
  - Devuelve: 
    - Si va bien: codigo 200
    
          {
              "message": "[ <nombre_del_amigo1>, <nombre_del_amigo2>, ...]"
          }

    - Si va mal: codigo 4**, y por qué falla

Cancelar petición de amistad
  - Petición POST a https://onep1.herokuapp.com/friends/cancel/fiend-request

  - JSON:

          {
            "username": <nombre_de_usuario>,
            "friendname": <nombre_del_amigo>
          }
          
  - Devuelve: 
    - Si va bien: codigo 200
     
          {
            "message": "Petición de amistad cancelada: <nombre_del_amigo>"
          }
          
    - Si va mal: codigo 4**, y por qué falla

Eliminar amigo
  - Petición POST a https://onep1.herokuapp.com/friends/deleteFriend

  - JSON:

          {
            "username": <nombre_de_usuario>,
            "friendname": <nombre_del_amigo>
          }
          
  - Devuelve: 
    - Si va bien: codigo 200
     
          {
            "message": "Amigo eliminado: <nombre_del_amigo>"
          }
          
    - Si va mal: codigo 4**, y por qué falla

Ranking de un pais
  - Petición POST a https://onep1.herokuapp.com/ranking/rankingPais

  - JSON:

          {
            "pais": <pais_del_usuario>
          }
          
  - Devuelve: 
    - Si va bien: codigo 200
     
          {
            "message": "[\"3nsalada2,10\",\"3nsalada,3\",\"3nsalada3,0\"]"
          }
          
    - Si va mal: codigo 4**, y por qué falla

Ranking de un mundial
  - Petición POST a https://onep1.herokuapp.com/ranking/rankingMundial

  - JSON: No tiene mensaje 


  - Devuelve: 
    - Si va bien: codigo 200
     
          {
              "message": "["3nsalada2,10","3nsalada,3","usuario123,0","paulapruebas,0","Helios,0","victor,0","3nsalada3,0","nere.g,0","nereapruebas,0","victorg,0"]"
          }
          
    - Si va mal: codigo 4**, y por qué falla

Ranking de un Amigos
  - Petición POST a https://onep1.herokuapp.com/ranking/rankingAmigos

  - JSON: 
          {
            "username": <nombre_del_usuario>
          } 


  - Devuelve: 
    - Si va bien: codigo 200
     
          {
            "message": "[\"3nsalada3,0,espagna\",\"nereapruebas,0,España\"]"
          }
          
    - Si va mal: codigo 4**, y por qué falla

Borrar un usuario
  - Petición POST a https://onep1.herokuapp.com/user/deleteUser

  - JSON: 
  
          {
            "username": <nombre_del_usuario>
          } 


  - Devuelve: 
    - Si va bien: codigo 200
    
          {
            "message": "Se ha eliminado el usuario correctamente"
          }
          
    - Si va mal: codigo 4**, y por qué falla

Cambiar el pais de un usuario
  - Petición POST a https://onep1.herokuapp.com/user/changePais

  - JSON: 
  
          {
            "username": <nombre_del_usuario>,
            "pais": <nuevo_pais>
          } 
          
    - Es vuestra responsabilidad que el país se encuentre dentro de la lista de paises que se han establecido

  - Devuelve: 
    - Si va bien: codigo 200
     
          {
            "message": "Se ha actualizado el pais del usuario correctamente."
          }
          
    - Si va mal: codigo 4**, y por qué falla

Cambiar el nombre de un usuario
  - Petición POST a https://onep1.herokuapp.com/user/changeUsername

  - JSON: 
  
          {
            "username": <nombre_del_usuario>,
            "newUsername": <nuevo_nombre_de_usuario>
          } 

  - Devuelve: 
    - Si va bien: codigo 200
     
          {
            "message": "Se ha actualizado el nombre del usuario correctamente."
          }
          
    - Si va mal: codigo 4**, y por qué falla

Restablecer la contraseña

  - Petición POST a https://onep1.herokuapp.com/api/auth/forgot_password

  - JSON:

          {
            "email": <email_con_el_que_se_registro>
          }

  - Devuelve: 
    - Si va bien: codigo 200 y envía un correo al usuario con un token para recuperar la contraseña
     
          {
            "message": "Se ha enviado el correo correctamente."
          }
          
    - Si va mal: codigo 4**, y poca info

Establecer nueva contraseña

  - Petición POST a https://onep1.herokuapp.com/api/auth/reset_password

  - JSON:

          {
            "email": <email_con_el_que_se_registro>
            "token":<token_que_se_ha_enviado_al_correo>,
            "password":<nueva_contraseña>
          }

  - Devuelve: 
    - Si va bien: codigo 200 
     
          {
            "message": "La contraseña se ha restablecido correctamente"
          }
          
      - Hay que volver a iniciar sesión
      
    - Si va mal: codigo 4**, y poca info

Devolver las cartas de un usuario en una partida

  - Peticion POST a : https://onep1.herokuapp.com/game/getCartas

  - JSON:

          {
            "playerName": <nombre_del_usuario>,
            "gameId":<id_de_la_partida>
          }

  - Devuelve: 
    - Si va bien: codigo 200 
     
          {
            [ "num" : [CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, BLOQUEO, MAS_DOS, CAMBIO_SENTIDO, CAMBIO_COLOR, MAS_CUATRO],
            "col" : [ROJO, AMARILLO, AZUL, VERDE, UNDEFINED], 
            "num" : [CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, BLOQUEO, MAS_DOS, CAMBIO_SENTIDO, CAMBIO_COLOR, MAS_CUATRO],
            "col" : [ROJO, AMARILLO, AZUL, VERDE, UNDEFINED],
            ... ]
          }
      
    - Si va mal: codigo 4**, y por qué falla

Partidas de un usuario

  - Peticion POST a : https://onep1.herokuapp.com/game/getPartidasActivas

  - JSON:

          {
            "playerName": <nombre_del_usuario>
          }

  - Devuelve: 
    - Si va bien: codigo 200 
     
          {
            codigo de partida
          }
      
    - Si va mal: codigo 4**, y por qué falla

Información de la partida

Partidas de un usuario

  - Peticion POST a : https://onep1.herokuapp.com/game/getInfoPartida

  - JSON:

          {
            "idPartida": <id_de_la_partida>
          }

  - Devuelve: 
    - Si va bien: codigo 200 
     
          {
            "numeroJugadores": <numero_de_jugadores_dentro_de_la_sala>,
            "tiempoTurno": <tiempo_de_turno>,
            "jugadores": [
              <nombre_jugador1>,
              <nombre_jugador2>,
              ...
            ],
            "reglas": [
              {
                [CERO_SWITCH, CRAZY_7, PROGRESSIVE_DRAW, CHAOS_DRAW, BLOCK_DRAW, REPEAT_DRAW]
              },
              {
                [CERO_SWITCH, CRAZY_7, PROGRESSIVE_DRAW, CHAOS_DRAW, BLOCK_DRAW, REPEAT_DRAW]
              }
              ...
            ]
          }
      
    - Si va mal: codigo 4**, y por qué falla

## Websockets

Endpoint al que se debe conectar el websocket: https://onep1.herokuapp.com/onep1-game



Cada usuario debería suscribirse a /user/{username}/msg (es donde llegarán los mensajes específicos para el usuario)


Para enviar un mensaje SIEMPRE tiene que tener el header { username: <nombre_de_usuario> (y authorization)} en el mensaje

Enviar un mensaje para conectarse a la partida /game/connect/{roomId}
  - Suscribirse a /topic/connect/{roomId}
    - Header : nombre de usuario
    - Body : vacio
  - Devuelve la lista de jugadores

Enviar un mensaje para empezar una partida /game/begin/{roomId}
  - Suscribirse a /topic/begin/{roomId}
    - Header : nombre de usuario
    - Body : vacio
  - Devuelve por este canal la carta del medio
  - Devuelve por /user/{username}/msg el array de cartas de cada jugador

Enviar un mensaje para desconectarse de una partida /game/disconnect/{roomId}
  - Suscribirse a /topic/disconnect/{roomId}
    - Header : nombre de usuario
    - Body : vacio

Enviar un mensaje para jugar una carta /game/card/play/{roomId}
  - Suscribirse a /topic/jugada/{roomId}
    - Header : nombre de usuario
    - Body :
 
        {
          "num" : [CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, BLOQUEO, MAS_DOS, CAMBIO_SENTIDO, CAMBIO_COLOR, MAS_CUATRO],
          "col" : [ROJO, AMARILLO, AZUL, VERDE, UNDEFINED]
        }
        
  - Devuelve:
    - Si va bien: 
      - Devuelve por /topic/jugada/{roomId} un JSON con formato:

        {
          "carta": {
            "num" : [CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, BLOQUEO, MAS_DOS, CAMBIO_SENTIDO, CAMBIO_COLOR, MAS_CUATRO],
            "col" : [ROJO, AMARILLO, AZUL, VERDE, UNDEFINED]
          },
          "jugadores": [
            {
              "username": "<nombre>",
              "numeroCartas": <numero_cartas>
            }, 
            {
              "username": "<nombre>",
              "numeroCartas": <numero_cartas>
            },
            ...
          ]
        }
      
                  
      
Enviar un mensaje para robar n cartas game/card/draw/{roomId}
  - Suscribirse a /topic/jugada/{roomId}
    - Header : nombre de usuario
    - Body :
 
        {
          "nCards" : <numero_cartas_a_robar>
        }

  - Devuelve:
    - Si va bien: 
      - Devuelve por /topic/jugada/{roomId} un JSON con formato:

        {
          "carta": {
            "num" : [CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, BLOQUEO, MAS_DOS, CAMBIO_SENTIDO, CAMBIO_COLOR, MAS_CUATRO],
            "col" : [ROJO, AMARILLO, AZUL, VERDE, UNDEFINED]
          },
          "jugadores": [
            {
              "username": "<nombre>",
              "numeroCartas": <numero_cartas>
            }, 
            {
              "username": "<nombre>",
              "numeroCartas": <numero_cartas>
            },
            ...
          ]
        }

      - Devuelve por /user/{username}/msg una lista de cartas:

        [
          {
            "num" : [CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, BLOQUEO, MAS_DOS, CAMBIO_SENTIDO, CAMBIO_COLOR, MAS_CUATRO],
            "col" : [ROJO, AMARILLO, AZUL, VERDE, UNDEFINED]
          },
          {
            "num" : [CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, BLOQUEO, MAS_DOS, CAMBIO_SENTIDO, CAMBIO_COLOR, MAS_CUATRO],
            "col" : [ROJO, AMARILLO, AZUL, VERDE, UNDEFINED]
          },
          ...
        ]
        
  
Enviar un mensaje al chat de la partida /game/message/{roomId}
  -NUEVO ENDPOINT: Suscribirse a /topic/chat/{roomId}
  -Header : nombre de usuario
  - Body :
  
        {
          "message" : "<mensaje>"
        }
  
  - Devuelve :
  
        {
          "username" : "<nombre_emisor>",
          "message" : "<mensaje>"
        }
  
  - El mensaje que se envíe se recibirá por el endpoint. 2 opciones para Frontend:
      - Mostrar nuevo mensaje, enviar mensaje a Backend, ignorar respuesta si lo he enviado yo.
      - Enviar mensaje a Backend, mostrar respuesta de Backend.
