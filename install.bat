cd graniteds-flex-spring-jpa-hibernate
mvn install -Dgpg.passphrase=$1
cd ..

cd graniteds-tide-flex-cdi-jpa
mvn install -Dgpg.passphrase=$1
cd ..

cd graniteds-tide-flex-seam-jpa-hibernate
mvn install -Dgpg.passphrase=$1
cd ..

cd graniteds-tide-flex-spring-jpa-hibernate
mvn install -Dgpg.passphrase=$1
cd ..

cd graniteds-tide-javafx-spring-jpa-hibernate
mvn install -Dgpg.passphrase=$1
cd ..

