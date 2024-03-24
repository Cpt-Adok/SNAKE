FROM ubuntu:latest

WORKDIR /TRON

RUN apt update && \
    apt install -y default-jdk maven

COPY  * .
