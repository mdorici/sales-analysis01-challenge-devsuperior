package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path =  sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<Sale> saleList = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                String[] fields = line.split(",");
                saleList.add(new Sale(Integer.parseInt(fields[0]),
                        Integer.parseInt(fields[1]),
                        fields[2],
                        Integer.parseInt(fields[3]),
                        Double.parseDouble(fields[4])));
                line = br.readLine();
            }
           List<Sale> averagePrice = saleList.stream()
                   .filter(s -> s.getYear() == 2016)
                   .sorted(Comparator.comparing(Sale::averagePrice).reversed())
                   .limit(5)
                   .toList();

            System.out.println("Top five 2016 sales of highest average price ");
            averagePrice.forEach(System.out::println);
            System.out.println();

            double sumLogan = saleList.stream()
                    .filter(s -> (s.getSeller().equals("Logan") && s.getMonth() == 1) || (s.getSeller().equals("Logan") && s.getMonth() == 7))
                    .map(s -> s.getTotal())
                    .reduce(0.0, (x, y) -> x + y);

            System.out.println("Total amount sold by seller Logan in months 1 and 7 = " + String.format("%.2f", sumLogan));
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
