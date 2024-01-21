package com.cchorafas;

import com.cchorafas.entities.Person;
import com.google.gson.Gson;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@ApplicationScoped
public class SQLDumpGenerator {

    @Incoming("add-requests")
    @Blocking
    public void addPersonToSqlDump(String personString) {
        Path resourcesPath = Paths.get("src/main/resources");
        Path sqlDumpPath = resourcesPath.resolve("dump.sql");

        Gson gson = new Gson();

        // Convert JSON string to Person object
        Person person = gson.fromJson(personString, Person.class);

        try (FileWriter writer = new FileWriter(sqlDumpPath.toFile(), true)) {
            writer.append("INSERT INTO person (id, firstname, lastname, birthday, age) " + "VALUES (")
                    .append(String.valueOf(person.id)).append(", '")
                    .append(person.firstName).append("', '")
                    .append(person.lastName).append("', '")
                    .append(person.birthday).append("', ")
                    .append(String.valueOf(person.age))
                    .append(");\n");
        } catch (IOException e) {
            throw new RuntimeException("Error writing SQL dump file", e);
        }
    }

    @Incoming("remove-requests")
    @Blocking
    public void removePersonFromSqlDump(String personId) {
        Path resourcesPath = Paths.get("src/main/resources");
        Path sqlDumpPath = resourcesPath.resolve("dump.sql");

        try {
            // Read the content of import.sql
            List<String> lines = Files.readAllLines(sqlDumpPath);

            // Identify and remove the relevant INSERT INTO statement for the person to be deleted
            lines.removeIf(line -> line.contains("INSERT INTO person") && line.contains("VALUES (" + personId));

            // Write the modified content back to import.sql
            Files.write(sqlDumpPath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
