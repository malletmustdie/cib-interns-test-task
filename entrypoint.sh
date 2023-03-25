echo "Installing certs to JDK" && \
keytool -importcert -file mycert.cer -alias mycert -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit