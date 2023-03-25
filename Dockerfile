FROM openjdk:17-alpine
ADD build/libs/cib-interns-test-task.jar cib-interns-test-task.jar
ENTRYPOINT ["java", "-jar", "cib-interns-test-task.jar"]