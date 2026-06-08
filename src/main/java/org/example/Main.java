package org.example;

import org.example.entity.*;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // ========================================================
        // 1-) SET CHALLENGE TEST (GÖREV YÖNETİMİ & RAPORLAMA)
        // ========================================================

        // Örnek Tasklar oluşturalım
        Task t1 = new Task("ProjectA", "Fix Login Bug", "Ann", Priority.HIGH, Status.IN_PROGRESS);
        Task t2 = new Task("ProjectA", "Create Dashboard", "Bob", Priority.MED, Status.ASSIGNED);
        Task t3 = new Task("ProjectB", "Database Migration", "Carol", Priority.HIGH, Status.IN_QUEUE);

        // Çakışma senaryosu: Aynı taskı (ProjectA - Fix Login Bug) Bob'a da atayalım.
        // Task sınıfındaki equals/hashCode sadece project ve description'a baktığı için bu iki task "Eşit" sayılacak.
        Task t4 = new Task("ProjectA", "Fix Login Bug", "Bob", Priority.HIGH, Status.IN_QUEUE);

        // Atanmamış task
        Task t5 = new Task("ProjectC", "Write Unit Tests", null, Priority.LOW, Status.IN_QUEUE);

        // Çalışanların setlerini hazırlayalım
        Set<Task> annsTasks = new HashSet<>();
        annsTasks.add(t1);

        Set<Task> bobsTasks = new HashSet<>();
        bobsTasks.add(t2);
        bobsTasks.add(t4); // Çakışan görev Bob'a da eklendi

        Set<Task> carolsTasks = new HashSet<>();
        carolsTasks.add(t3);

        Set<Task> unassignedTasks = new HashSet<>();
        unassignedTasks.add(t5);

        // TaskData nesnemizi ayağa kaldıralım
        TaskData taskData = new TaskData(annsTasks, bobsTasks, carolsTasks, unassignedTasks);

        System.out.println("=================== MÜDÜRE RAPOR SONUÇLARI ===================");

        // Soru 1: Tüm çalışanların üzerindeki tasklar nelerdir?
        System.out.println("1. Tüm Çalışanların Toplam Benzersiz Görevleri:");
        for (Task task : taskData.getAllEmployeesTasks()) {
            System.out.println("   -> [" + task.getProject() + "] " + task.getDescription() + " (Atanan: " + task.getAssignee() + ")");
        }

        // Soru 2: Her bir çalışanın üzerindeki tasklar nelerdir?
        System.out.println("\n2. Çalışan Bazlı Görev Dağılımı:");
        System.out.println("   Ann'in Görevleri  : " + taskData.getTasks("ann"));
        System.out.println("   Bob'un Görevleri  : " + taskData.getTasks("bob"));
        System.out.println("   Carol'un Görevleri: " + taskData.getTasks("carol"));

        // Soru 3: Herhangi bir çalışana atanması yapılmamış olan tasklar nelerdir?
        // getTasks("all") çağrısı içeride unassignedTasks dahil hepsini birleştirir,
        // çalışanlarınkini çıkarırsak geriye sadece atanmamışlar kalır.
        Set<Task> totalWithUnassigned = taskData.getTasks("all");
        Set<Task> employeesOnly = taskData.getAllEmployeesTasks();
        Set<Task> purelyUnassigned = taskData.getDifference(totalWithUnassigned, employeesOnly);
        System.out.println("\n3. Ataması Yapılmamış Görevler: " + purelyUnassigned);

        // Soru 4: Birden fazla çalışana atanmış task var mı? Varsa hangileri?
        System.out.println("\n4. Birden Fazla Çalışana Atanmış (Çakışan) Görevler: " + taskData.getIntersectTasks());


        // ========================================================
        // 2-) CHALLENGE TEST (METİN ANALİZİ & TREESET)
        // ========================================================
        System.out.println("\n=================== STRING SET CHALLENGE SONUÇLARI ===================");

        Set<String> sortedUniqueWords = StringSet.findUniqueWords();
        System.out.println("Alfabetik Sıralı Benzersiz Kelimeler Listesi:\n" + sortedUniqueWords);
    }
}