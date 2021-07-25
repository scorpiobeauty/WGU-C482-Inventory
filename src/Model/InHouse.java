package Model;

/** This class inherits the Part class and contains Inhouse parts getters and setters.*/
public class InHouse extends Part {

    /** This is declaring machineID is an Integer.*/
    private int machineID;

    /** This is the Inhouse constructor.*/
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** This is Inhouse machineID setter.*/
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /** This is Inhouse machineID getter.*/
    public int getMachineID() {
        return machineID;
    }


}
