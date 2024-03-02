docker compose down
docker rmi bucket-bingo-api-bucket-bingo

rm ./build/libs/api-0.0.1.jar

./gradlew build --no-build-cache && docker compose up -d