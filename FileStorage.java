package com.novara.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.charset.StandardCharsets;

public class FileStorage {

    private final Path dataDirectory = Path.of("data");

    public void createFolders() throws IOException {
        Files.createDirectories(dataDirectory.resolve("customers"));
        Files.createDirectories(dataDirectory.resolve("accounts"));
        Files.createDirectories(dataDirectory.resolve("transactions"));
        Files.createDirectories(dataDirectory.resolve("statements"));
    }

    public void saveFile(Path filePath, String content) throws IOException {
        Files.createDirectories(filePath.getParent());

        if (Files.exists(filePath)) {
            throw new IOException("File already exists");
        }

        Files.writeString(filePath, content, StandardCharsets.UTF_8);
    }

    public String readFile(Path filePath) throws IOException {
        return Files.readString(filePath, StandardCharsets.UTF_8);
    }

    public void updateFile(Path filePath, String content) throws IOException {
        Files.createDirectories(filePath.getParent());

        Path tempFile = Files.createTempFile(
                filePath.getParent(), "update-", ".tmp");

        try {
            Files.writeString(tempFile, content, StandardCharsets.UTF_8);

            Files.move(tempFile, filePath,
                    StandardCopyOption.REPLACE_EXISTING);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
