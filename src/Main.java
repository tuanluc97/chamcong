import common.Constant;
import reader.CSVReader;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String arg[]){
        Scanner sc = new Scanner(System.in);
        ChamCongController controller = new ChamCongController();
        controller.readFile(Constant.DIR_CSV, new CSVReader());
        while(true){
            System.out.println(Constant.MESSAGE_INPUT);
            String dateInput = sc.nextLine();
            controller.execute(dateInput);
        }
    }
}
