package ru.spb.herzen.ivt3.third;

import java.io.BufferedReader;
import java.io.FileReader;

public class BookPageCounter {
    private String name;
    private int pageAmount;

    public BookPageCounter(String name) {
        this.name = name;
        try {
            FileReader file = new FileReader("./books/" + name);
            BufferedReader bufferedReader = new BufferedReader(file);

            String line;
            while((line = bufferedReader.readLine()) != null) {
                this.pageAmount += line.split("\t").length;
            }

            bufferedReader.close();
        } catch (Exception exception) {
            System.out.print("Unable to open book: " + this.getName() + "\n\n");
            this.pageAmount = 0;
        }
    }

    public Integer getPageAmount() {
        return pageAmount;
    }

    public String getName() {
        return "\"" + name + "\"";
    }
}
