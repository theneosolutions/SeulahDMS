package com.example.User.utils;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

/**
 * Utility class for utility methods
 *
 * @author Muhammad Mansoor
 */
public class CommonUtils {

    private static final SecureRandom secureRandom = new SecureRandom();

    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private static final String FILE_EXTENSION = "csv.gz";

    private CommonUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String dateFormatter(final long timestamp) {
        final Instant instant = Instant.ofEpochMilli(timestamp);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm:ssZ")
                .withZone(ZoneId.from(ZoneOffset.UTC));
        return formatter.format(instant);
    }

    public static String dateFormatterMST(final long timestamp) {
        final Instant instant = Instant.ofEpochMilli(timestamp);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm.ss")
                .withZone(ZoneId.from(ZoneOffset.ofHours(-7)));
        return formatter.format(instant);
    }

    public static String itemIdModifier(final String itemIDs) {
        final String[] values = itemIDs.split(",");
        final StringBuilder updatedItemIds = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (values[i].contains("|")) {
                values[i] = values[i].substring(values[i].indexOf("|") + 1, values[i].length() - 2);
            }
            updatedItemIds.append(values[i]).append(",");
        }
        return updatedItemIds.substring(0, updatedItemIds.length() - 1);
    }

    public static String generateNewSessionToken() {
        final byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String generateLiveUsername(final String firstName, final String lastName) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (firstName != null && !firstName.isEmpty() && !firstName.isBlank()) {
            stringBuilder.append(firstName.toLowerCase(Locale.ENGLISH).charAt(0));
        }
        if (lastName != null && !lastName.isEmpty() && !lastName.isBlank()) {
            stringBuilder.append(lastName.toLowerCase(Locale.ENGLISH).charAt(0));
            stringBuilder.append("-");
        }
        final SecureRandom secureRandom = new SecureRandom();
        final int upperBound = 10000;
        stringBuilder.append(String.format("%04d", secureRandom.nextInt(upperBound)));
        return stringBuilder.toString();
    }

    public static String createFileNameStringForReport(final String filename) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
                .withZone(ZoneId.systemDefault());
        final Instant instant = Instant.now();
        final String currentDate = formatter.format(instant);
        final StringBuilder fileNameString = new StringBuilder();
        fileNameString.append(filename);
        fileNameString.append(currentDate);
        fileNameString.append(".");
        fileNameString.append(FILE_EXTENSION);
        return fileNameString.toString();
    }    
}
