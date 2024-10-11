package com.example.ToontownLauncher.utils.game;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.nio.file.attribute.PosixFilePermission;

public class LauncherHelper {

    // Helper method to load a JSON configuration file
    public static JsonNode loadJsonFile(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonData = null;
        try {
            Path filePath = getLauncherFilePath(fileName);
            jsonData = mapper.readTree(Files.newInputStream(filePath));
        } catch (IOException e) {
            System.out.println("Failed to load " + fileName + ": " + e.getMessage());
        }
        return jsonData;
    }

    // Helper method to update a JSON configuration file
    public static void updateJsonFile(String fileName, JsonNode jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Path filePath = getLauncherFilePath(fileName);
            mapper.writerWithDefaultPrettyPrinter().writeValue(Files.newOutputStream(filePath), jsonData);
        } catch (IOException e) {
            System.out.println("Failed to update " + fileName + ": " + e.getMessage());
        }
    }

    // Helper method to retry an operation multiple times with delay
    public static boolean retryOperation(int retries, long delayMillis, RetryableOperation operation) {
        for (int i = 0; i < retries; i++) {
            try {
                if (operation.execute()) {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Retry failed: " + e.getMessage());
            }
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException ignored) {
            }
        }
        return false;
    }

    // Method to check for updates using patch manifest
    public static boolean checkForUpdates(String gameDirectory, String patchManifestUrl) {
        Patcher patcher = new Patcher();
        return patcher.checkUpdate(gameDirectory, patchManifestUrl);
    }

    // Convert JSON string to Map<String, String>
    public static Map<String, String> jsonStringToMap(String body) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonObj = mapper.readTree(body);
            jsonObj.fieldNames().forEachRemaining(key -> resultMap.put(key, jsonObj.get(key).asText()));
        } catch (IOException e) {
            System.out.println("Failed to parse JSON: " + e.getMessage());
        }
        return resultMap;
    }

    // Fetch patch manifest from a URL
    public static JsonNode fetchPatchManifest(String patchManifestUrl) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            URL url = new URL(patchManifestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the manifest data
            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                String jsonData = scanner.useDelimiter("\\A").next();
                return mapper.readTree(jsonData);
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch patch manifest: " + e.getMessage());
        }
        return null;
    }

    public boolean fetchPatchManifestWithRetry(String patchManifestUrl) {
        return LauncherHelper.retryOperation(3, 2000, () -> fetchPatchManifest(patchManifestUrl) != null);
    }

    public static void downloadFileFromMirrors(String dl, String localFilePath, List<String> mirrors) {
        for (String mirror : mirrors) {
            String fullUrl = mirror + dl;
            try {
                System.out.println("Attempting to download: " + fullUrl);
                URL url = new URL(fullUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                try (InputStream in = conn.getInputStream();
                     OutputStream out = new FileOutputStream(localFilePath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    System.out.println("Download completed: " + localFilePath);
                    return;  // Exit on first successful download
                }
            } catch (IOException e) {
                System.out.println("Failed to download from: " + fullUrl);
            }
        }
        System.out.println("All mirrors failed for: " + dl);
    }

    public static boolean verifyFileHash(MessageDigest digest, InputStream fis, String expectedHash) {
        try {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            byte[] hashBytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            String computedHash = sb.toString();
            return computedHash.equals(expectedHash);
        } catch (IOException e) {
            System.out.println("Failed to compute hash: " + e.getMessage());
            return false;
        }
    }

    public static void quitLauncher(int i) {
    }

    // Interface for retryable operations
    @FunctionalInterface
    public interface RetryableOperation {
        boolean execute() throws Exception;
    }

    // Helper method to get the launcher directory path
    public static Path getLauncherFilePath(String fileName) {
        Path launcherDir = Paths.get(System.getProperty("user.home"), "ToontownGame");
        if (!Files.exists(launcherDir)) {
            try {
                Files.createDirectories(launcherDir);
            } catch (IOException e) {
                System.out.println("Failed to create launcher directory: " + e.getMessage());
            }
        }
        return launcherDir.resolve(fileName);
    }

    // Helper method to set executable permissions on a file (for Unix-like systems)
    public static void setExecutablePermission(File file) {
        try {
            Set<PosixFilePermission> permissions = EnumSet.of(
                    PosixFilePermission.OWNER_EXECUTE,
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.GROUP_EXECUTE,
                    PosixFilePermission.GROUP_READ,
                    PosixFilePermission.OTHERS_EXECUTE,
                    PosixFilePermission.OTHERS_READ
            );
            Files.setPosixFilePermissions(file.toPath(), permissions);
        } catch (IOException e) {
            System.out.println("Failed to set file permissions: " + e.getMessage());
        }
    }

}