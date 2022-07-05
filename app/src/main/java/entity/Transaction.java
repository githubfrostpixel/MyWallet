package entity;

import java.util.Date;


class Transaction {
    private int id;
    private String name;
    private int typeId;
    private int walletId;
    private Date date;
    private String description;

    public Transaction() {
    }

    public Transaction(int id, String name, int typeId, int walletId, Date date, String description) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.walletId = walletId;
        this.date = date;
        this.description = description;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
