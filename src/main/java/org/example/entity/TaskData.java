package org.example.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskData {

    private Set<Task> annsTasks;
    private Set<Task> bobsTasks;
    private Set<Task> carolsTasks;
    private Set<Task> unassignedTasks;

    public TaskData(Set<Task> annsTasks, Set<Task> bobsTasks, Set<Task> carolsTasks, Set<Task> unassignedTasks) {
        this.annsTasks = annsTasks;
        this.bobsTasks = bobsTasks;
        this.carolsTasks = carolsTasks;
        this.unassignedTasks = unassignedTasks;
    }

    public Set<Task> getTasks(String name) {
        switch (name.toLowerCase()) {
            case "ann": return this.annsTasks;
            case "bob": return this.bobsTasks;
            case "carol": return this.carolsTasks;
            case "all":
                return getUnion(List.of(
                        annsTasks != null ? annsTasks : new HashSet<>(),
                        bobsTasks != null ? bobsTasks : new HashSet<>(),
                        carolsTasks != null ? carolsTasks : new HashSet<>(),
                        unassignedTasks != null ? unassignedTasks : new HashSet<>()
                ));
            default:
                return new HashSet<>();
        }
    }

    public Set<Task> getUnion(List<Set<Task>> sets) {
        Set<Task> unionSet = new HashSet<>();
        for (Set<Task> set : sets) {
            if (set != null) {
                unionSet.addAll(set);
            }
        }
        return unionSet;
    }

    public Set<Task> getIntersect(Set<Task> first, Set<Task> second) {
        if (first == null || second == null) return new HashSet<>();
        Set<Task> intersectSet = new HashSet<>(first);
        intersectSet.retainAll(second);
        return intersectSet;
    }

    public Set<Task> getDifference(Set<Task> first, Set<Task> second) {
        if (first == null) return new HashSet<>();
        if (second == null) return new HashSet<>(first);
        Set<Task> differenceSet = new HashSet<>(first);
        differenceSet.removeAll(second);
        return differenceSet;
    }

    public Set<Task> getAllEmployeesTasks() {
        return getUnion(List.of(
                this.annsTasks != null ? this.annsTasks : new HashSet<>(),
                this.bobsTasks != null ? this.bobsTasks : new HashSet<>(),
                this.carolsTasks != null ? this.carolsTasks : new HashSet<>()
        ));
    }

    public Set<Task> getIntersectTasks() {
        Set<Task> ann = this.annsTasks != null ? this.annsTasks : new HashSet<>();
        Set<Task> bob = this.bobsTasks != null ? this.bobsTasks : new HashSet<>();
        Set<Task> carol = this.carolsTasks != null ? this.carolsTasks : new HashSet<>();

        Set<Task> annAndBob = getIntersect(ann, bob);
        Set<Task> annAndCarol = getIntersect(ann, carol);
        Set<Task> bobAndCarol = getIntersect(bob, carol);

        return getUnion(List.of(annAndBob, annAndCarol, bobAndCarol));
    }
}
