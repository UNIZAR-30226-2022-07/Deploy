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
  
      - username de entre 4 y 255 caracteres
        
      - password de entre 6 y 255 caracteres
        
      - email que cumpla la siguiente ER: .+[@].+[\\.].+ (algo + @ + algo + . + algo). Máximo 255 caracteres
        
      - pais de máximo 255 caracteres

  - Al registrar un usuario, le manda un correo con un toke, que debe introducir para activar su cuenta

Logear un usuario:

  - Petición POST a https://onep1.herokuapp.com/api/auth/signin

  - JSON:
      
        {
          "username": <nombre_de_usuario>,
          "password": <contraseña>
        }
    
  - Devuelve

        {
          "username": <nombre_de_usuario>,
          "email": <correo>,
          "pais": <pais>,
          "puntos": <puntos>,
          "accessToken": <token_de_acceso>,
          "tokenType": "Bearer"
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
            "username": <nombre_del_usuario>
          }

  - Devuelve: 
    - Si va bien: codigo 200 
     
          
          {
            "partidas": <codigo_partida>
          }
          
      
    - Si va mal: codigo 4**, y por qué falla

Información de la partida

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
            ],
            "estado": <estado> (NEW, IN_PROGRESS, FINISHED)
          }
      
    - Si va mal: codigo 4**, y por qué falla

Enviar una invitación de partidas a un usuario

  - Peticion POST a : https://onep1.herokuapp.com/game/invite

  - JSON:

          {
            "username": <nombre_del_usuario>,
            "friendname": <nombre_del_amigo_al_que_se_invita>,
            "gameId": <gameId>
          }

  - Devuelve: 
    - Si va bien: codigo 200 
     
          {
            "message": "Amigo <nombre_del_amigo_al_que_se_invita> invitado"
          }
          
      
    - Si va mal: codigo 4**, y por qué falla

Activar cuenta de usuario

  - Peticion POST a : https://onep1.herokuapp.com/api/auth/activarCuenta

  - JSON:

          {
            "username": <nombre_de_usuario>,
            "token": <token_enviado>
          }

  - Devuelve: 
    - Si va bien: codigo 200 
     
          {
            "message": "Cuenta activa"
          }
          
      
    - Si va mal: codigo 4**, y por qué falla

Obtener las invitaciones de partida de un usuario

  - Peticion POST a : https://onep1.herokuapp.com/game/getInvitacionesPartida

  - JSON:

          {
            "username": <nombre_de_usuario>
          }

  - Devuelve: 
    - Si va bien: codigo 200 
     
          [
            {
              "invitador": <nombre_del_amigo>,
              "game": <gameId>
            },
            {
              "invitador": <nombre_del_amigo>,
              "game": <gameId>
            },
            ...
          ]
          
      
    - Si va mal: codigo 4**, y por qué falla

Eliminar una invitación a partida

  - Peticion POST a : https://onep1.herokuapp.com/game/cancelarInvitacionPartida

  - JSON:

          {
            "username": <nombre_de_usuario>,
	          "gameId": <id_partida>
          }

  - Devuelve: 
    - Si va bien: codigo 200 
		- Si está disponible la partida
     
				  {
					"message": "Se ha eliminado la invitación a partida"
				  }
      
      o

    	 - Si no está diponible esa partida
    
				  {
					"message": "No se ha podido eliminar la invitación a partida"
				  }
          
      
    - Si va mal: codigo 4**, y por qué falla

  - Para aceptar la invitación a la partida hay que eliminar la invitación para el usuario y conctarse a la partida

Buscar una partida pública
  
  - Peticion POST a : https://onep1.herokuapp.com/game/getPartidaPublica

  - JSON: vacio

  - Devuelve: 
    - Si va bien: codigo 200 
   
      	 "f214755c-217b-4803-ab6e-d08ad7ff6696" 
       <codigo_de_la_patida>
          
      
    - Si va mal: codigo 4**, y por qué falla

  - Para acceder a una partida pública hay que buscar un id de partida pública mediante este método y conectarse a él mediante connect
  - Es necesario suscribirse a todos los canales asociados a la partida
  - Cualquier jugador debería poder comenzar la partida cuando se haya llenado (haya 4 jugadores)

Crear un torneo
 
  - Peticion POST a : https://onep1.herokuapp.com/torneo/createTorneo

  - JSON: 
  
          {
            "username":<nombre_de_usuario>,
            "tiempoTurno":<tiempo_de_turno>,
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

  - Devuelve: 
    - Si va bien: codigo 200 
    
	       {
		      "idTorneo": <id_torneo>,
          "tiempoTurno": <tiempo de turno>,
          "jugadores": [
            <nombre_jugador1>,
            <nombre_jugador2>,
            ...

          ],
          "reglas": [
            <regla1>,
            <regla2>,
            ...
          ]
        }
          
      
    - Si va mal: codigo 4**, y por qué falla

Obtener torneos disponibles

  - Peticion POST a : https://onep1.herokuapp.com/torneo/getTorneos

  - JSON: vacio

  - Devuelve: 
    - Si va bien: codigo 200 

        [
          {
            "idTorneo": <id_torneo>,
            "tiempoTurno": <tiempo de turno>,
            "jugadores": [
              <nombre_jugador1>,
              <nombre_jugador2>,
              ...

            ],
            "reglas": [
              <regla1>,
              <regla2>,
              ...
            ]
          }
        ]
          
      
    - Si va mal: codigo 4**, y por qué falla

Jugar la final de un toneo

  - Petición POST a : https://onep1.herokuapp.com/torneo/jugarFinal

  - JSON:
   
          {
            "username" : <nombre_del_usuario>,
            "torneoId" : <id_Torneo>
          }

  - Devuelve: 
    - Si va bien: codigo 200 
        <id_partida_final>
          
      
    - Si va mal: codigo 4**, y por qué falla

Cambiar las manos con otro jugador

  - Petición POST a : https://onep1.herokuapp.com/game/cambiarManos

  - JSON:
   
          {
            "gameId" : <game_id>,
            "player1" : <username1>,
            "player2" : <username2>
          }

  - Devuelve: 
    - Si va bien: codigo 200 

        {
          "message" : "Se han cambiado las manos"
        }

      - Envia por el canal /user/{username}/msg la nueva mano asignada en formato:
		
	    mano: [NUEVE ROJO, BLOQUEO AZUL, MAS_CUATRO UNDEFINED]

          
      
    - Si va mal: codigo 4**, y por qué falla


Saber si una partida es una semifinal de un torneo o no
  - Petición POST a : https://onep1.herokuapp.com/torneo/game/isSemifinal

  - JSON:
   
          {
            "idPartida" : <game_id>,
            "idTorneo" : <username1>,
          }

  - Devuelve: 
    - Si va bien: codigo 200 

        {
          "true" o "false"
        }     
      
    - Si va mal: codigo 4**, y por qué falla

Obtener la mano de un jugador

  - Petición POST a : https://onep1.herokuapp.com/game/getManoJugador

  - JSON:
   
          {
            "username" : <nombre_del_usuario>,
            "idPartida" : <id_partida>
          }

  - Devuelve: 
    - Si va bien: codigo 200 

        {
          "message" : "Mano enviada"
        }

      - Envia por el canal /user/{username}/msg la mano del jugador
          
      
    - Si va mal: codigo 4**, y por qué falla

Obtener ultima jugada de una partida

  - Petición POST a : https://onep1.herokuapp.com/game/getManoJugador

  - JSON:
   
          {
            "username" : <nombre_del_usuario>,
            "idPartida" : <id_partida>
          }

  - Devuelve: 
    - Si va bien: codigo 200 

        {
          "message" : "Ultima jugada enviada"
        }

      - Envia por el canal /user/{username}/msg la ultima jugada con el formato:

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
            ],
            "turno":<username>
          }
      
    - Si va mal: codigo 4**, y por qué falla

Torneos de un usuario

  - Peticion POST a : https://onep1.herokuapp.com/torneo/getTorneosActivos

  - JSON:

          {
            "username": <nombre_del_usuario>
          }

  - Devuelve: 
    - Si va bien: codigo 200 
     
          
          {
            "partidas": <codigo_partida>
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
				  ],
				  "turno":<username>
				}
      
      
Enviar un mensaje para robar n cartas game/card/draw/{roomId}
  - Suscribirse a /topic/jugada/{roomId}
    - Header : nombre de usuario
    - Body :
 
			{
			  <numero_cartas_a_robar>
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
				  ],
				  "turno":<nombre_usuario>
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

Enviar un mensaje para pasar de turno (se usa en caso de robar cartas o que te hayan bloqueado etc, no al jugar una carta) game/pasarTurno/{roomId}
  - AL JUGAR UNA CARTA SE PASA EL TURNO SOLO
  - Suscribirse a /topic/jugada/{roomId}
    - Header : nombre de usuario
    - Body : vacio

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
			  ],
			  "turno":<nombre_usuario>
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
  - NUEVO ENDPOINT: Suscribirse a /topic/chat/{roomId}
  - Header : <nombre_de_usuario>
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


Enviar botón de uno
  - Suscribirse a /topic/buttonOne/{roomId}
  - Header : <usuario_que_pulsa_boton>
  - Body : vacio

  - Devuelve :
  
		{
		  <nombre_de_usuario_que_pulsa>
		}


Enviar un mensaje para conectarse a un torneo /game/connect/torneo/{torneoId}
  - Suscribirse a /topic/connect/torneo/{torneoId}
    - Header : nombre de usuario
    - Body : vacio
  - Devuelve la lista de jugadores del torneo


Enviar un mensaje para desconectarse de un torneo /game/disconnect/torneo/{torneoId}
  - Suscribirse a /topic/disconnect/torneo/{torneoId}
    - Header : nombre de usuario
    - Body : vacio


Enviar un mensaje para empezar un torneo /game/begin/torneo/{torneoId}
  - No hay que suscribirse a ningún canal general
    - Header : nombre de usuario
    - Body : vacio
  - Devuelve por /user/{username}/msg el código de partida de la semifinal a la que se tendrá que unir el usuario
  - Hay que suscribirse a todos los canales asociados al id de partida que se mandan como si fuera una partida privada
  - El jugador que esté primero de la lista hace begin de esa partida (?)
