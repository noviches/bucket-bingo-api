docker compose down
docker rmi bucket-bingo-api_bucket-bingo

rm ./build/libs/api-0.0.1.jar

./gradlew build && docker compose up -d