package com.example.ToontownLauncher.utils.game;

import com.example.ToontownLauncher.utils.game.LauncherHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Patcher {
    private static final String MIRROR_URL = "https://www.toontownrewritten.com/api/mirrors";
    private List<String> mirrors;

    public Patcher() {
        this.mirrors = fetchMirrors();
    }

    // Fetch mirrors from TTR API
    private List<String> fetchMirrors() {
        List<String> mirrors = new ArrayList<>();
        try {
            URL url = new URL(MIRROR_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = new String(in.readAllBytes());
            ObjectMapper mapper = new ObjectMapper();
            mirrors = Arrays.asList(mapper.readValue(result, String[].class));
            Collections.shuffle(mirrors);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not fetch mirrors.");
        }
        return mirrors;
    }

    // Check for game updates using LauncherHelper
    boolean checkUpdate(String ttrDir, String patchManifestUrl) {
        try {
            // Use LauncherHelper to handle user directory creation
            Path installPath = LauncherHelper.getLauncherFilePath("ToontownGame");

            if (!Files.exists(installPath)) {
                Files.createDirectories(installPath);
            }

            // Fetch and compare patch manifest with local files
            JsonNode patchManifest = fetchPatchManifest(patchManifestUrl);
            return compareLocalFilesWithManifest(installPath, patchManifest);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch patch manifest
    public JsonNode fetchPatchManifest(String patchManifestUrl) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            URL url = new URL(patchManifestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Check if the response code is 200 (HTTP OK)
            if (conn.getResponseCode() != 200) {
                System.out.println("Failed to fetch patch manifest: " + patchManifestUrl);
                return null;
            }

            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = new String(in.readAllBytes());
            return mapper.readTree(result);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error fetching manifest from URL: " + patchManifestUrl);
            return null;
        }
    }

    // Compare local files with the manifest and download/update if necessary
    private boolean compareLocalFilesWithManifest(Path installPath, JsonNode patchManifest) {
        for (Iterator<String> it = patchManifest.fieldNames(); it.hasNext(); ) {
            String fileName = it.next();
            JsonNode fileInfo = patchManifest.get(fileName);

            Path filePath = installPath.resolve(fileName);
            if (!Files.exists(filePath) || !verifyFileHash(filePath, fileInfo.get("hash").asText())) {
                System.out.println("File " + fileName + " needs to be updated.");
                LauncherHelper.downloadFileFromMirrors(fileInfo.get("dl").asText(), filePath.toString(), mirrors);
            } else {
                System.out.println("File " + fileName + " is up-to-date.");
            }
        }
        return true;
    }

    // Verify file hash
    private boolean verifyFileHash(Path filePath, String expectedHash) {
        try (InputStream fis = Files.newInputStream(filePath)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            return LauncherHelper.verifyFileHash(digest, fis, expectedHash);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
}