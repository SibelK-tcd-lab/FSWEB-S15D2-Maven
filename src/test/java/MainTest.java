package org.example;

import org.example.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    Task task1;
    Task task2;
    Task task3;
    TaskData taskData;
    Set<Task> taskSet1;
    Set<Task> taskSet2;
    Set<Task> taskSet3;

    @BeforeEach
    void setUp() {
        // Sıralama Hatası Düzeltildi: project, description, assignee, priority, status
        task1 = new Task("Java Collections", "Write List Interface", "Bob", Priority.HIGH, Status.IN_QUEUE);
        task2 = new Task("Java Collections", "Write Set Interface", "Ann", Priority.MED, Status.ASSIGNED);
        task3 = new Task("Java Collections", "Write Map Interface", "Bob", Priority.HIGH, Status.IN_QUEUE);

        // Parantez uyuşmazlığı ve yanlış setlere ekleme hataları düzeltildi
        taskSet1 = new HashSet<>();
        taskSet1.add(task1);

        taskSet2 = new HashSet<>();
        taskSet2.add(task2);

        taskSet3 = new HashSet<>();
        taskSet3.add(task3);

        taskData = new TaskData(taskSet1, taskSet2, taskSet3, new HashSet<>());
    }

    @DisplayName("Task sınıfı doğru access modifiers sahip mi")
    @Test
    public void testTaskAccessModifiers() throws NoSuchFieldException {
        Field assigneeFields = task1.getClass().getDeclaredField("assignee");
        Field descriptionsFields = task1.getClass().getDeclaredField("description");
        Field projectFields = task1.getClass().getDeclaredField("project");
        Field priorityFields = task1.getClass().getDeclaredField("priority");
        Field statusFields = task1.getClass().getDeclaredField("status");

        assertEquals(assigneeFields.getModifiers(), 2);
        assertEquals(descriptionsFields.getModifiers(), 2);
        assertEquals(projectFields.getModifiers(), 2);
        assertEquals(priorityFields.getModifiers(), 2);
        assertEquals(statusFields.getModifiers(), 2);
    }

    @DisplayName("Task sınıfı doğru typelara sahip mi")
    @Test
    public void testTaskTypes() throws NoSuchFieldException {
        assertThat(task1.getAssignee(), instanceOf(String.class));
        assertThat(task1.getDescription(), instanceOf(String.class));
        assertThat(task1.getPriority(), instanceOf(Priority.class));
        assertThat(task1.getProject(), instanceOf(String.class));
        assertThat(task1.getStatus(), instanceOf(Status.class));
    }

    @DisplayName("TaskData sınıfı doğru access modifiers sahip mi")
    @Test
    public void testTaskDataAccessModifiers() throws NoSuchFieldException {
        Field annTasksField = taskData.getClass().getDeclaredField("annsTasks");
        Field bobTasksField = taskData.getClass().getDeclaredField("bobsTasks");
        Field carolTasksField = taskData.getClass().getDeclaredField("carolsTasks");
        Field unassignedTasksField = taskData.getClass().getDeclaredField("unassignedTasks");

        assertEquals(annTasksField.getModifiers(), 2);
        assertEquals(bobTasksField.getModifiers(), 2);
        assertEquals(carolTasksField.getModifiers(), 2);
        assertEquals(unassignedTasksField.getModifiers(), 2);
    }

    @DisplayName("TaskData getTasks method doğru çalışıyor mu ?")
    @Test
    public void testGetTasksMethod() {
        assertEquals(taskData.getTasks("ann"), taskSet1);
        assertEquals(taskData.getTasks("bob"), taskSet2);
        assertEquals(taskData.getTasks("carol"), taskSet3);
    }

    @DisplayName("TaskData getUnion method doğru çalışıyor mu ?")
    @Test
    public void testGetUnionMethod() {
        Set<Task> taskSet = new HashSet<>();
        taskSet.add(task1);
        Set<Task> taskSet2 = new HashSet<>();
        taskSet.add(task2);

        // TaskData içindeki getUnion metodunun List.of() parametre yapısıyla eşitlendi
        Set<Task> totals = taskData.getUnion(List.of(taskSet, taskSet2));
        assertEquals(totals.size(), 2);
    }

    @DisplayName("TaskData getIntersect() method doğru çalışıyor mu ?")
    @Test
    public void testGetIntersectMethod() throws NoSuchFieldException {
        Set<Task> taskSet = new HashSet<>();
        taskSet.add(task1);
        taskSet.add(task2);
        Set<Task> taskSet2 = new HashSet<>();
        taskSet2.add(task2);

        // Metod ismi getIntersect olarak düzeltildi
        Set<Task> intersections = taskData.getIntersect(taskSet, taskSet2);

        for(Task task: intersections){
            assertEquals(task, task2);
        }

        assertEquals(intersections.size(), 1);
    }

    @DisplayName("TaskData getDifference() method doğru çalışıyor mu ?")
    @Test
    public void testGetDifferenceMethod() throws NoSuchFieldException {
        Set<Task> taskSet = new HashSet<>();
        taskSet.add(task1);
        taskSet.add(task2);
        Set<Task> taskSet2 = new HashSet<>();
        taskSet2.add(task2);

        // Metod ismi getDifference olarak düzeltildi
        Set<Task> differences = taskData.getDifference(taskSet, taskSet2);

        for(Task task: differences){
            assertEquals(task, task1);
        }

        assertEquals(differences.size(), 1);
    }

    @DisplayName("findUniqueWords doğru çalışıyor mu ?")
    @Test
    public void testFindUniqueWordsMethod() {

        assertEquals(org.example.entity.StringSet.findUniqueWords().size(), 70);

        List<String> results = org.example.entity.StringSet.findUniqueWords().stream().collect(Collectors.toList());
        assertEquals(results.get(0), "a");
    }
}