FROM openjdk:11.0.7-jre
ADD build/libs/cib-interns-test-task.jar cib-interns-test-task.jar
ENTRYPOINT ["java", "-jar", "cib-interns-test-task.jar"]