
package com.example.textviewfinder.dbbean;

import java.util.ArrayList;

import com.amida.easydb.DbTable;
import com.amida.easydb.annotaions.Column;
import com.amida.easydb.annotaions.OneToManyColumn;

public class Team extends DbTable {
    @Column(name = "teamName")
    private String teamName;
    @Column(name = "teamLeader")
    private String teamLeader;
    @OneToManyColumn(itemClass=Member.class)
    private ArrayList<Member> members;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public  ArrayList<Member> getMembers() {
        return members;
    }
    
    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }
}
