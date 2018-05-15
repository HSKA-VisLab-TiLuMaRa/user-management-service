FROM java:8
ADD /target/*.jar user-management-service.jar
ENTRYPOINT ["java","-jar","user-management-service.jar"]
