package ca.cmpt213.as5.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Represent a reader to read course data CSV file
 */
public class CourseDataReader {
    private String filePath;
    private BufferedReader reader = null;
    private HashMap<String, Integer> header = new HashMap<>();

    public CourseDataReader(String filePath) {
        this.filePath = filePath;
    }

    public List<String> combineMultipleInstructor(String[] values) {
        String tem = "";
        List<String> list = new ArrayList<>();
        for (int i = 6; i < values.length - 1; i ++) {
            if (i == 6) {
                tem = tem + values[i].replace('"', ' ');
            } else {
                tem = tem + ", " + values[i].trim();
            }
        }
        for (int i = 0; i < 6; i ++) {
            list.add(values[i]);
        }
        list.add(tem);
        list.add(values[values.length - 1]);
        return list;
    }

    public HashMap<String, List<List<String>>> readCSV() {
        HashMap<String, List<List<String>>> hashMap = new HashMap<>();
        int count = 0;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                List<String> trimList;
                if (line.contains("\"")) {
                    List<String> temList = combineMultipleInstructor(values);
                    trimList = trimStringList(temList);
                } else {
                    List<String> list = Arrays.asList(values);
                    trimList = trimStringList(list);
                }
                if (count == 0) {
                    for (int i = 0; i < trimList.size(); i ++) {
                        header.put(values[i], i);
                    }
                } else {
                    int subjectIndex = header.get("SUBJECT");
                    int catalogIndex = header.get("CATALOGNUMBER");
                    String courseName = trimList.get(subjectIndex) + " " + trimList.get(catalogIndex);
                    if (hashMap.containsKey(courseName)) {
                        List<List<String>> dataRow = hashMap.get(courseName);
                        dataRow.add(trimList);
                    } else {
                        List<List<String>> courses = new ArrayList<>();
                        courses.add(trimList);
                        hashMap.put(courseName, courses);
                    }
                }
                count ++;
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    System.exit(-1);
                }
            }
        }
        return hashMap;
    }

    public HashMap<String, Integer> getHeader() {
        return header;
    }

    public List<String> trimStringList(List<String> list) {
        List<String> trimList = new ArrayList<>();
        for (String s : list) {
            trimList.add(s.trim());
        }
        return trimList;
    }
}
