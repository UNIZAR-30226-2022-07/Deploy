# Deploy
Aplicación en el dominio https://onep1.herokuapp.com/

Para la autentificación de usuarios (tanto registro como login) es necesario usar el prefijo (/api/auth)
Para acceder al login (/signin). Es necesario usar LoginRequest (en el paquete com.cerea_p1.spring.jpa.postgresql.payload.request). Devuelve ResponseEntity (en formato JSON) con la información de nombre de usuario, email, pais y puntos.

Para registrar un usuario se usa (/signup). Es necesario usar SignupRequest (en el paquete com.cerea_p1.spring.jpa.postgresql.payload.request) y devuelve un mensaje de ok del formato que se muestra en el ficero com.cerea_p1.spring.jpa.postgresql.payload.response.MessageResponse.

Registro de usuario:

  -Petición GET a https://onep1.herokuapp.com/api/auth/signup
  
  -JSON:
  
      {
        "username": <nombre_de_usuario>,
        "email": <email>,
        "pais": <pais>,
        "password": <contraseña>
      }

Logear un usuario:

  -Petición POST a https://onep1.herokuapp.com/api/auth/signin

  -JSON:
      
      {
        "username": <nombre_de_usuario>,
        "password": <contraseña>
      }
      
