package it.polito.ezshop.data.classes;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.*;

public class EZFileReader {
    public Map<String, Double> readCreditCards() {
        Map<String, Double> ccMap = new HashMap<>();

        try {
            //Instantiating the CSVReader class
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

            CSVReader reader =
                    new CSVReaderBuilder(new FileReader("testFiles/creditCardFile_test.csv"))
                            .withCSVParser(parser)
                            .build();
            //Reading the contents of the csv file
            StringBuffer buffer = new StringBuffer();
            String line[];
            try {
                while ((line = reader.readNext()) != null) {
                    // skip comments, i.e. lines starting with '#'
                    if (line[0].startsWith("#")) {
                        continue;
                    }
                    // add the credit card to the map
                    ccMap.put(line[0], Double.parseDouble(line[1]));
                }

                reader.close();
            }
            catch (IOException e) {
                System.out.println("Error:" + e.getMessage());
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: file not found. Returning empty map.");
            e.printStackTrace();
        }

        return ccMap;
    }

    public void setCreditCards(Map<String, Double> ccMap) {
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(new File("testFiles/creditCardFile_test.csv"));

            CSVWriter writer = new CSVWriter(outputfile, ';',
                    ICSVWriter.NO_QUOTE_CHARACTER,
                    ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    ICSVWriter.DEFAULT_LINE_END);

            for(Map.Entry<String, Double> e : ccMap.entrySet()) {
                String line[] = {e.getKey(), e.getValue().toString()};

                writer.writeNext(line);
            }

            writer.close();
        }
        catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
