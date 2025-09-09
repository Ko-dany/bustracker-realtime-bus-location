```
# Generate GrfsRealtime.java class
protoc --java_out=src/main/java src/main/proto/gtfs-realtime.proto
```

```
# Build project, downloading all dependencies
mvn clean compile  

# Execute the Main.java
mvn exec:java
```