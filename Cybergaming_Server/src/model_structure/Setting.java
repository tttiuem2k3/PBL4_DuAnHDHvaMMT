/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model_structure;
    

public class Setting {
    private int id;
    private int numofpc;
    private long cost;
    private long costuser;
    public Setting()
    {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumofpc() {
        return numofpc;
    }

    public void setNumofpc(int numofpc) {
        this.numofpc = numofpc;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getCostuser() {
        return costuser;
    }

    public void setCostuser(long costuser) {
        this.costuser = costuser;
    }
    
}
