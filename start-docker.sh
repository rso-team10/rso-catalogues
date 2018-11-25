#Stop all containers
#docker stop $(docker ps -a -q)

#Remove containers
#docker rm postgres-catalogues
#docker rm rso-catalogues

#Remove image
#docker rmi catalogues

#DB
docker run --name postgres-catalogues -d -e POSTGRES_USERNAME=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=rso-catalogues -p 5432:5432 postgres:latest

#MicroService
mvn clean package
docker build -t catalogues .
docker run --name rso-catalogues -p 8081:8081 catalogues
