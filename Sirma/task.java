// src\employeesData.csv

package Sirma;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class task {

    public static void main(String[] args) throws IOException {
        String file = "src/Sirma/employeesData.csv";
        Path path = Paths.get(file);
        BufferedReader bufferedReader = Files.newBufferedReader(path);
        List data = new LinkedList();

        String curLine;
        while ((curLine = bufferedReader.readLine()) != null) {
            data.add(curLine);

        }

        // This Associative Array holds <ProjectID<EmpID, working days on the project>>
        Map<String, Map<String, Integer>> projectEmployeeDays = new HashMap<String, Map<String, Integer>>();


        for (int i = 0; i < data.size(); i++) {
            String curEmployee = data.get(i).toString().split(",")[0];
            String curProject = data.get(i).toString().split(",")[1];

            String startDate = data.get(i).toString().split(",")[2];

            String endDate = data.get(i).toString().split(",")[3];
            if (endDate.equals("NULL")) {
                endDate = LocalDate.now().toString();
            }

            LocalDate startTime = LocalDate.parse(startDate);
            LocalDate endTime = LocalDate.parse(endDate);

            //calculating number of days in between startTime and endTime
            Integer workedDays = Math.toIntExact(ChronoUnit.DAYS.between(startTime, endTime));

            if (projectEmployeeDays.containsKey(curProject)) {
                Map<String, Integer> enteredInformation = projectEmployeeDays.get(curProject);
                // chek if in one project some person is worked more times
                if (enteredInformation.containsKey(curEmployee)) {
                    Integer oldWorkedDays = enteredInformation.get(curEmployee);
                    Integer newSumDays = oldWorkedDays + workedDays;
                    enteredInformation.replace(curEmployee, newSumDays);
                    projectEmployeeDays.put(curProject, enteredInformation);

                } else {
                    enteredInformation.put(curEmployee, workedDays);
                    projectEmployeeDays.put(curProject, enteredInformation);
                }
            } else {
                Map<String, Integer> innermap = new HashMap<String, Integer>();
                innermap.put(curEmployee, workedDays);
                projectEmployeeDays.put(curProject, innermap);

            }
        }


        for (Map.Entry<String, Map<String, Integer>> entry : projectEmployeeDays.entrySet()) {
            String projectName = entry.getKey();

            Map<String, Integer> currentInfo = entry.getValue();
            // case 1: a maximum of two people work on the projects
            if (currentInfo.size() == 2) {

                int days = 0;
                List personalID = new ArrayList();
                for (Map.Entry<String, Integer> integerEntry : currentInfo.entrySet()) {
                    days += integerEntry.getValue();
                    personalID.add(integerEntry.getKey());
                }
                int years = days / 365;


                // printing the result to the console

                String toPrint = personalID.get(1) + "," + personalID.get(0) + "," + years;

                System.out.println(toPrint);
            }

        }


    }
}