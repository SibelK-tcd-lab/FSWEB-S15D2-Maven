package org.example;

import org.example.entity.*;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {



        Task t1 = new Task("ProjectA", "Fix Login Bug", "Ann", Priority.HIGH, Status.IN_PROGRESS);
        Task t2 = new Task("ProjectA", "Create Dashboard", "Bob", Priority.MED, Status.ASSIGNED);
        Task t3 = new Task("ProjectB", "Database Migration", "Carol", Priority.HIGH, Status.IN_QUEUE);


        Task t4 = new Task("ProjectA", "Fix Login Bug", "Bob", Priority.HIGH, Status.IN_QUEUE);


        Task t5 = new Task("ProjectC", "Write Unit Tests", null, Priority.LOW, Status.IN_QUEUE);


        Set<Task> annsTasks = new HashSet<>();
        annsTasks.add(t1);

        Set<Task> bobsTasks = new HashSet<>();
        bobsTasks.add(t2);
        bobsTasks.add(t4);

        Set<Task> carolsTasks = new HashSet<>();
        carolsTasks.add(t3);

        Set<Task> unassignedTasks = new HashSet<>();
        unassignedTasks.add(t5);


        TaskData taskData = new TaskData(annsTasks, bobsTasks, carolsTasks, unassignedTasks);

        System.out.println("=================== MÜDÜRE RAPOR SONUÇLARI ===================");


        System.out.println("1. Tüm Çalışanların Toplam Benzersiz Görevleri:");
        for (Task task : taskData.getAllEmployeesTasks()) {
            System.out.println("   -> [" + task.getProject() + "] " + task.getDescription() + " (Atanan: " + task.getAssignee() + ")");
        }


        System.out.println("\n2. Çalışan Bazlı Görev Dağılımı:");
        System.out.println("   Ann'in Görevleri  : " + taskData.getTasks("ann"));
        System.out.println("   Bob'un Görevleri  : " + taskData.getTasks("bob"));
        System.out.println("   Carol'un Görevleri: " + taskData.getTasks("carol"));


        Set<Task> totalWithUnassigned = taskData.getTasks("all");
        Set<Task> employeesOnly = taskData.getAllEmployeesTasks();
        Set<Task> purelyUnassigned = taskData.getDifference(totalWithUnassigned, employeesOnly);
        System.out.println("\n3. Ataması Yapılmamış Görevler: " + purelyUnassigned);


        System.out.println("\n4. Birden Fazla Çalışana Atanmış (Çakışan) Görevler: " + taskData.getIntersectTasks());



        System.out.println("\n=================== STRING SET CHALLENGE SONUÇLARI ===================");

        Set<String> sortedUniqueWords = StringSet.findUniqueWords();
        System.out.println("Alfabetik Sıralı Benzersiz Kelimeler Listesi:\n" + sortedUniqueWords);
    }
}