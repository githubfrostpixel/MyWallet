package entity;



import androidx.room.*;

@Entity
public class Wallet {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "balance")
    private int balance;

    public Wallet() {
    }
    @Ignore
    public Wallet(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }
    @Ignore
    public Wallet(int id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
