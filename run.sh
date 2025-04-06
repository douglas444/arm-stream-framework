docker build -t arm-stream-runner .
docker run --rm -v "$(pwd)"/results:/app/output arm-stream-runner