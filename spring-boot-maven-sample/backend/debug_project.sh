#!/bin/bash

#
# If you must use mvn spring-boot:run
#
# Start Maven with JDWP enabled:
#
# ```
#   mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
# ```
#
# Then in IntelliJ:
#   Run → Edit Configurations
#   Add a Remote JVM Debug configuration.
#   Host: localhost
#   Port: 5005
#
# Click Debug to attach.
#

ADMIN_PASSWORD=devpassword \
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005



