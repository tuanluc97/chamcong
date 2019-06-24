import reader.CSVReader;
import bean.DateInfo;
import bean.Employee;
import common.Constant;
import common.Helper;
import reader.ReaderFile;

import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChamCongController {

    private List<Employee> listEmployee = new ArrayList<Employee>();
    private List<Date> listDateOfMonth = new ArrayList<Date>();
    SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_INPUT_FOMAT);

    public void execute(String dateInput) {
        try {
            if (!Helper.checkInvalidFomat(dateFormat, dateInput)) {
                System.out.println(Constant.MESAGE_INVALID_INPUT);
                return;
            }
            Date date = dateFormat.parse(dateInput);
            int index = this.getDateIndex(date);
            this.printListTotalTimeWord(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFile(String file, ReaderFile readerFile) {
        readerFile.parse(file, listEmployee, listDateOfMonth);
    }

    private int getDateIndex(Date date) {
        List<Date> list = this.listDateOfMonth;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) {
                continue;
            }
            if (list.get(i).getMonth() == date.getMonth() && list.get(i).getDate() == date.getDate()) {
                return i;
            }
        }
        return -1;
    }

    private void printListTotalTimeWord(int index) {
        if (index < 0) {
            System.out.println(Constant.MESAGE_ERROR_INPUT);
            return;
        }
        for (int i = 0; i < this.listEmployee.size(); i++) {
            if (this.listEmployee.get(i) == null) {
                System.out.println();
                continue;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("id: ").append(this.listEmployee.get(i).getId());
            sb.append(" - name: ");
            sb.append(this.listEmployee.get(i).getName() + " thoi gian lam viec: ");
            List<DateInfo> listDateInfo = this.listEmployee.get(i).getListDateInfo();
            boolean isHasDateInfo = false;
            for (int j = 0; j < listDateInfo.size(); j++) {
                if (listDateInfo.get(j).getTimeStart() == null) {
                    continue;
                }
                if (listDateInfo.get(j).getIndex() == index) {
                    sb.append(listDateInfo.get(j).getTimeStart().getHours() + ":");
                    sb.append(listDateInfo.get(j).getTimeStart().getMinutes() + " - ");
                    sb.append(listDateInfo.get(j).getTimeEnd().getHours() + ":");
                    sb.append(listDateInfo.get(j).getTimeEnd().getMinutes() + " - ");
                    sb.append(" --> ");
                    sb.append(this.getTotalMinitesWordInDay(listDateInfo.get(j).getTimeStart(), listDateInfo.get(j).getTimeEnd()));
                    isHasDateInfo = true;
                    break;
                }
            }
            if (!isHasDateInfo) {
                sb.append(Constant.MESAGE_ERROR_INPUT);
            }
            System.out.println(sb);
        }
    }

    private String getTotalMinitesWordInDay(Date timeStart, Date timeEnd) {
        if (timeStart == null || timeEnd == null ||
                (timeEnd.getHours() == timeStart.getHours() && timeStart.getMinutes() == timeEnd.getMinutes())) {
            return Constant.MESAGE_ERROR_INPUT;
        }
        int totalTime = 0;
        // di lam den truoc 12:00 va ve sau 13:30
        if (timeStart.getHours() < 12 &&
                (timeEnd.getHours() >= 14 || (timeEnd.getHours() == 13 && timeEnd.getMinutes() >= 30))) {
            //tinh thoi gian lam buoi sang
            int timeM = (12 - timeStart.getHours()) * 60 - timeStart.getMinutes();
            if (timeM > 210) {
                timeM = 210;
            }
            //tinh thoi gian lam ca ngay = sang + chieu
            int timeA = (timeEnd.getHours() - 13) * 60 + timeEnd.getMinutes() - 30;
            if (timeA > 270) {
                timeA = 270;
            }
            totalTime = timeA + timeM;
        }
        // di truoc 12:00 va ve truoc 13:30
        if (timeStart.getHours() < 12 &&
                ((timeEnd.getHours() < 13) || (timeEnd.getHours() == 13 && timeEnd.getMinutes() < 30))) {
            // ve sau 12h
            if (timeEnd.getHours() >= 12) {
                totalTime = (12 - timeStart.getHours()) * 60 - timeStart.getMinutes();
                if (totalTime > 210) {
                    totalTime = 210;
                }
            }
            // ve truoc 12h
            else {
                // den truoc 8:30
                if (timeStart.getHours() < 8 || (timeStart.getHours() == 8 && timeStart.getMinutes() <= 30)) {
                    totalTime = (timeEnd.getHours() - 8) * 60 + (timeEnd.getMinutes() - 30);
                }
                //den sau 8:30
                else {
                    totalTime = (timeEnd.getHours() - timeStart.getHours()) * 60 + (timeEnd.getMinutes() - timeStart.getMinutes());
                }
            }
        }
        // di sau 12:00 va ve sau 13:30
        if (timeStart.getHours() >= 12 &&
                ((timeEnd.getHours() > 14) || (timeEnd.getHours() == 13 && timeEnd.getMinutes() > 30))) {
            // di truoc 13:30
            if (timeStart.getHours() < 13 || (timeStart.getHours() == 13 && timeStart.getMinutes() <= 30)) {
                totalTime = (timeEnd.getHours() - 13) * 60 + timeEnd.getMinutes() - 30;
                if (totalTime > 270) {
                    totalTime = 270;
                }
            }
            // di sau 13:30
            else {
                // ve sau 18:30
                if (timeEnd.getHours() > 18 || (timeEnd.getHours() == 18 && timeEnd.getMinutes() >= 30)) {
                    totalTime = (18 - timeStart.getHours()) * 60 - (timeStart.getMinutes() - 30);
                }
                // ve truoc 18:30
                else {
                    totalTime = (timeEnd.getHours() - timeStart.getHours()) * 60 + (timeEnd.getMinutes() - timeStart.getMinutes());
                }
            }
        }
        return (totalTime / 60) + " gio " + (totalTime % 60) + " phut";
    }
}