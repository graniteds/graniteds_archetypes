cd graniteds-flex-spring-jpa-hibernate
mvn deploy -Dgpg.passphrase=$1
cd ..

cd graniteds-tide-flex-cdi-jpa
mvn deploy -Dgpg.passphrase=$1
cd ..

cd graniteds-tide-flex-seam-jpa-hibernate
mvn deploy -Dgpg.passphrase=$1
cd ..

cd graniteds-tide-flex-spring-jpa-hibernate
mvn deploy -Dgpg.passphrase=$1
cd ..

cd graniteds-tide-javafx-spring-jpa-hibernate
mvn deploy -Dgpg.passphrase=$1
cd ..

