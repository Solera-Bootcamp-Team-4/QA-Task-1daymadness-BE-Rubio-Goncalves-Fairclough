# Week 5 final assessment BACKEND
Create a thread app!

## Done by
Adrià Rubio (adria.rubio@solera.com)
Joao Goncalves (joao.goncalves@solera.com)

## Technologies chosen
- Spring Boot

## What's done?
Uppercase letters are CRUD minus what's missing :D
- User CRD
- Forum CRD
- Threads CRD

### API done
- GET /api/users
- GET /api/user/{username}
- DELETE /api/user/{username}/delete
- POST /api/user/new
 
- GET /api/forums
- GET /api/forum/{name}
- DELETE /api/forum/{name}/delete
- POST /api/forum/new

- GET /api/threads
- GET /api/thread/{id}
- DELETE /api/thread/{id}/delete
- POST /api/thread/new

### API to do
The threads api is not as intended.
It should be accessible via its parent forum, like:
- GET /api/forum/{name}/thread/{id}
