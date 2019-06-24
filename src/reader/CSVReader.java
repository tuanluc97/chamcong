package reader;

import bean.DateInfo;
import bean.Employee;
import common.Constant;
import common.Helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVReader implements ReaderFile {

    public void parse(String file, List<Employee> listEmployee, List<Date> dateOfMonths) {
        BufferedReader br = null;
        String line = Constant.BLANK_CHARACTER;
        boolean firstTime = true;
        try {
            File directory = new File("./");
            br = new BufferedReader(new FileReader(directory.getAbsolutePath() + file));
            while ((line = br.readLine()) != null) {
                String[] country = line.split(Constant.SEPARATOR);
                if (firstTime) {
                    this.handleDateOfMonth(dateOfMonths, country);
                    firstTime = false;
                    continue;
                }
                if (country[0].isEmpty()) {
                    continue;
                }
                Employee e = new Employee(country[0], country[1]);

                this.getListDateInfo(e, dateOfMonths, country);
                listEmployee.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null){
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleDateOfMonth(List<Date> dateOfMonths, String[] country) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FOMAT);
        for (int i = 0; i < country.length; i++) {
            Date date = null;
            if (!country[i].isEmpty() && Helper.checkInvalidFomat(dateFormat, country[i])) {
                try {
                    date = dateFormat.parse(country[i]);
                    date.setYear(119);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dateOfMonths.add(date);
        }
    }

    private void getListDateInfo(Employee employee, List<Date> listDateOfMonth, String[] country) {
        List listDateInfo = new ArrayList<DateInfo>();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FOMAT);
        for (int i = 0; i < listDateOfMonth.size() && i < country.length; i++) {
            if (listDateOfMonth.get(i) == null) {
                continue;
            }
            DateInfo dateInfo = new DateInfo();
            dateInfo.setIndex(i);
            if (country[i].isEmpty()) {
                listDateInfo.add(dateInfo);
                continue;
            }
            try {
                dateInfo.setTimeStart(dateFormat.parse(country[i]));

                dateInfo.setTimeEnd(dateFormat.parse(country[i + 1]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            listDateInfo.add(dateInfo);
        }
        employee.setListDateInfo(listDateInfo);
    }
}
