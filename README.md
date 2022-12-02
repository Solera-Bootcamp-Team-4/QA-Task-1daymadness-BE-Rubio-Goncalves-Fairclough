# Week 5 final assessment BACKEND
Create a forum app!

## Done by
- Adrià Rubio (adria.rubio@solera.com)
- Joao Goncalves (joao.goncalves@solera.com)

## Technologies chosen
### Front end
- Spring Boot
### Back end
- ReactJS

## What's done?
Uppercase letters are CRUD minus what's missing :D
- User CRD
- Forum CRD
- Threads CRD

Plus every controller has 100% coverage :D

### API done
#### Users
- GET /api/users
- GET /api/user/{username}
- DELETE /api/user/{username}/delete
- POST /api/user/new


#### Forums
- GET /api/forums
- GET /api/forum/{name}
- DELETE /api/forum/{name}/delete
- POST /api/forum/new


#### Threads
- GET /api/threads
- GET /api/thread/{id}
- DELETE /api/thread/{id}/delete
- POST /api/thread/new

### API todo
The threads api is not as intended.
It should be accessible via its parent forum, like so:
- GET /api/forum/{name}/thread/{id}
