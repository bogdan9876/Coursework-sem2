package ua.lviv.iot.algo.part1.coursova.datastorage;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public final class DataRepositoryAssistant {


    public static String getDateNow() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);
    }

    public static void writeContentToFile(
            final File file,
            final String content)
            throws IOException {
        Writer writer = new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8);
        writer.write(content);
        writer.close();
    }

    public static File validateFile(
            final String directoryPath,
            final String objectPrefix) throws ParseException {
        File file = generateFile(directoryPath, objectPrefix);

        File directory = new File(directoryPath);
        if (FileUtils.sizeOfDirectory(directory) != 0) {
            file = findFile(directoryPath, objectPrefix);
        }

        return file;
    }

    public static File findFile(final String directoryPath,
                                final String objectPrefix)
                                throws ParseException {
        File file = new File("");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        for (LocalDate date = LocalDate.now();
             date.isAfter(formatter.parse("1970-01-01").toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
             date = date.minusDays(1)) {

            DateTimeFormatter pathFormatter = DateTimeFormatter.
                    ofPattern("yyyy-MM-dd");
            String datePath = date.format(pathFormatter);
            if (Files.exists(Paths.get(directoryPath
                    + objectPrefix + "-" + datePath + ".csv"))) {
                file = new File(directoryPath
                        + objectPrefix + "-" + datePath + ".csv");
                break;
            }
        }
        return file;
    }

    public static File generateFile(final String directoryPath,
                                    final String objectPrefix) {
        String date = DataRepositoryAssistant.getDateNow();

        return new File(directoryPath + objectPrefix + "-" + date + ".csv");
    }

    @SuppressFBWarnings
    public static void generateDirectory(final String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static List<String> processRawValues(final List<String> rawValues) {
        List<String> values = new LinkedList<>();

        for (String value : rawValues) {
            if (value.equals("null")) {
                values.add("");
            } else if (value.startsWith("[") && value.endsWith("]")) {
                String listValue = value.substring(1, value.length() - 1);
                if (!listValue.isEmpty()) {
                    values.add(listValue);
                } else {
                    values.add("");
                }
            } else {
                values.add(value);
            }
        }

        return values;
    }


}
