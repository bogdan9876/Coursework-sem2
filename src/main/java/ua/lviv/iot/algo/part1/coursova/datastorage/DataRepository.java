package ua.lviv.iot.algo.part1.coursova.datastorage;

import ua.lviv.iot.algo.part1.coursova.model.Entity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public abstract class DataRepository<T extends Entity> {

    public void save(final HashMap<Integer, T> items,
                     final String directoryPath) throws IOException {
        DataRepositoryAssistant.generateDirectory(directoryPath);
        File file = DataRepositoryAssistant.generateFile(
                directoryPath, getPrefix());

        StringBuilder content = new StringBuilder(items.values().stream().
                toList().get(0).getHeaders() + "\n");
        for (T item: items.values()) {
            content.append(item.toCSV()).append("\n");
        }

        DataRepositoryAssistant.writeContentToFile(file, content.toString());
    }

    public HashMap<Integer, T> load(final String directoryPath)
            throws IOException, ParseException {
        DataRepositoryAssistant.generateDirectory(directoryPath);

        return new HashMap<>(scan(DataRepositoryAssistant.validateFile(
                directoryPath, getPrefix())));
    }

    private HashMap<Integer, T> scan(final File file)
            throws IOException, ParseException {
        HashMap<Integer, T> resultCouriers = new HashMap<>();
        if (file.exists()) {
            Scanner scanner = new Scanner(file, StandardCharsets.UTF_8);
            boolean isFirst = true;
            while (scanner.hasNextLine()) {
                if (isFirst) {
                    scanner.nextLine();
                    isFirst = false;
                } else {
                    List<String> rawValues = Arrays.stream(
                            scanner.nextLine().split(", ")).toList();
                    List<String> values = DataRepositoryAssistant.
                            processRawValues(rawValues);
                    T item = fill(values);
                    resultCouriers.put(getId(item), item);
                }
            }
            scanner.close();
        }
        return resultCouriers;
    }

    protected abstract Integer getId(T item);

    protected abstract T fill(List<String> values) throws ParseException;

    protected abstract String getPrefix();

}
