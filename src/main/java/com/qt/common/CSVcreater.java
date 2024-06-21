/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class CSVcreater {

    private final List<String> columnNames;
    private final Map<Integer, String> lineValue;
    private File file;
    private File fileTemp;
    private FileWriter fileWriter;

    public CSVcreater() {
        this.columnNames = new ArrayList<>();
        this.lineValue = new HashMap<>();
    }

    public void start(String dir) throws IOException {
        this.file = new File(dir);
        this.columnNames.clear();
        this.lineValue.clear();
        this.fileTemp = new File(String.format("./temp/%s", System.currentTimeMillis()));
        if (!initFile(fileTemp)) {
            JOptionPane.showMessageDialog(null, "fail to create file temp!");
        }
        this.fileWriter = new FileWriter(this.fileTemp, true);
    }

    private boolean initFile(File file) {
        try {
            if (file == null) {
                return false;
            }
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            return file.exists() || file.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void newLine() throws IOException {
        if (this.fileWriter != null) {
            int size = this.columnNames.size();
            for (int i = 0; i < size; i++) {
                String value = this.lineValue.get(i);
                this.fileWriter.write(String.format("%s,",
                        value == null ? "" : value));
            }
            this.fileWriter.write("\r\n");
            this.fileWriter.flush();
            this.lineValue.clear();
        }
    }

    public void put(String name, String value) {
        if (!this.columnNames.contains(name)) {
            this.columnNames.add(name);
        }
        if (value == null) {
            value = "";
        } else if (value.contains(",")
                || value.contains("\r\n")
                || value.contains("\r")
                || value.contains("\n")) {
            value = String.format("\"%s\"", value);
        }
        this.lineValue.put(this.columnNames.indexOf(name), value);
    }

    public void save() throws IOException {
        if (!initFile(file)) {
            JOptionPane.showMessageDialog(null, "Create file csv failure!");
        }
        this.fileWriter.close();
        try ( FileWriter fw = new FileWriter(file)) {
            for (String columnName : columnNames) {
                fw.write(String.valueOf(columnName));
                fw.write(",");
                fw.flush();
            }
            fw.write("\r\n");
            fw.write(Files.readString(this.fileTemp.toPath()));
            fw.flush();
        }
        JOptionPane.showMessageDialog(null, this.file.getPath());
    }

//    public static void main(String[] args) throws IOException {
//        File dir = new File("C:\\Users\\Administrator\\Desktop\\jupiter-log");
//        CSVcreater csv = new CSVcreater();
//        csv.start("csv_file.csv");
//        FileService fileService = new FileService();
//        for (File file : dir.listFiles()) {
//            String lines = fileService.readFile(file);
//            String itemName = null;
//            String value = null;
//            csv.put("path", file.getName());
//            boolean thisOne = false;
//            for (String line : lines.split("\r\n")) {
//                if (line == null || line.isBlank()) {
//                    continue;
//                }
//                if (line.contains("]NAME[") && line.contains("	ITEM=ID[") && line.contains("];")) {
//                    itemName = line.substring(line.indexOf("]NAME[") + 6, line.lastIndexOf("];"));
//                    value = null;
//                }else if(line.contains("read ") && line.contains(" Mbps")){
//                    value = line.split(" ")[2].replaceAll("\\.", ",");
//                }else if(line.contains("]RESULT[") && line.contains("	ITEM=ID[") && itemName != null){
//                    csv.put(itemName, value);
//                    itemName = null;
//                }else{
//                    String[] values;
//                    if(line.contains("V;") && (values = line.split("\\s")).length == 9){
//                        csv.put(values[2], values[4]);
//                    }
//                }
//            }
//            csv.newLine();
//        }
//        csv.save();
//    }
}
