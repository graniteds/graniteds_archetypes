mvn archetype:generate -DarchetypeGroupId=org.graniteds.archetypes -DarchetypeArtifactId=graniteds-flex-spring-jpa-hibernate -DarchetypeVersion=$1 -DgroupId=org.example -Dversion=1.0-SNAPSHOT -Dpackage=org.example -DartifactId=flexspring -DinteractiveMode=false

mvn archetype:generate -DarchetypeGroupId=org.graniteds.archetypes -DarchetypeArtifactId=graniteds-tide-flex-cdi-jpa -DarchetypeVersion=$1 -DgroupId=org.example -Dversion=1.0-SNAPSHOT -Dpackage=org.example -DartifactId=tideflexcdi -DinteractiveMode=false

mvn archetype:generate -DarchetypeGroupId=org.graniteds.archetypes -DarchetypeArtifactId=graniteds-tide-flex-seam-jpa-hibernate -DarchetypeVersion=$1 -DgroupId=org.example -Dversion=1.0-SNAPSHOT -Dpackage=org.example -DartifactId=tideflexseam -DinteractiveMode=false

mvn archetype:generate -DarchetypeGroupId=org.graniteds.archetypes -DarchetypeArtifactId=graniteds-tide-flex-spring-jpa-hibernate -DarchetypeVersion=$1 -DgroupId=org.example -Dversion=1.0-SNAPSHOT -Dpackage=org.example -DartifactId=tideflexspring -DinteractiveMode=false

mvn archetype:generate -DarchetypeGroupId=org.graniteds.archetypes -DarchetypeArtifactId=graniteds-tide-javafx-spring-jpa-hibernate -DarchetypeVersion=$1 -DgroupId=org.example -Dversion=1.0-SNAPSHOT -Dpackage=org.example -DartifactId=tidejavafxspring -DinteractiveMode=false

cd flexspring
mvn install
cd ..

cd tideflexcdi
mvn install
cd ..

cd tideflexseam
mvn install
cd ..

cd tideflexspring
mvn install
cd ..

cd tidejavafxspring
mvn install
cd ..

