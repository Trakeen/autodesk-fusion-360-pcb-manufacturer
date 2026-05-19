#!/bin/sh
git pull
./mvnw clean package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
docker save -o pcb.tar otomotive/pcb
scp pcb.tar debian@api.otomotive.org:pcb.tar