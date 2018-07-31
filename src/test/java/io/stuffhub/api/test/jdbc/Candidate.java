package io.stuffhub.api.test.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Candidate {

    private int candidate_id;
    private String first_name;
    private String last_name;
    private ArrayList<String> skills = new ArrayList<>();

    @Override
    public String toString() {
        return "Candidate{" +
                "candidate_id=" + candidate_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }

    public int getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(int candidate_id) {
        this.candidate_id = candidate_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void addSkills(String skill) {
        this.skills.add(skill);
    }

    public void getCandidateSkills() {
        for (String skill : skills) {
            System.out.print(skill);
            System.out.println(" ");
        }
    }
}
