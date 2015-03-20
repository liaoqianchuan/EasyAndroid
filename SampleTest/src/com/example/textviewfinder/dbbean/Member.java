
package com.example.textviewfinder.dbbean;

import com.amida.easydb.DbTable;
import com.amida.easydb.annotaions.Column;

public class Member extends DbTable {

    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "team")
    private Team team;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
