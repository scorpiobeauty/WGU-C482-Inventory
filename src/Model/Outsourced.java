package Model;


/** is class inherits the Part class and contains Outsourced parts getters and setters.*/
public class Outsourced extends Part {

    /** This is setting the company name as a string.*/
    private String companyName;

    /** This is the Outsourced Constructor.*/
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This is Outsourced Company name setter.*/
    public void setCompanyName (String companyName) {

        this.companyName = companyName;
    }

    /** This is Outsourced Company name getter.*/
    public String getCompanyName() {
        return companyName;
    }
}
