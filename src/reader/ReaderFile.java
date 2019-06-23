package reader;

import bean.Employee;

import java.io.FileReader;
import java.util.Date;
import java.util.List;

public interface ReaderFile{
    void parse(String file, List<Employee> listEmployee, List<Date> dateOfMonths);
}