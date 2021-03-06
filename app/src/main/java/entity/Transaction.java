package entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import utility.DateConverter;

@Entity(foreignKeys = {
        @ForeignKey(entity = Wallet.class,
                    parentColumns = "id",
                    childColumns = "walletId",
                    onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = TransactionType.class,
                    parentColumns = "id",
                    childColumns = "typeId",
                    onDelete = ForeignKey.CASCADE)
})
@TypeConverters(DateConverter.class)
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "typeId",index = true)
    private int typeId;
    @ColumnInfo(name = "walletId",index = true)
    private int walletId;
    @ColumnInfo(name = "value")
    private int value;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "inorout")
    private int inorout;
    public Transaction() {
    }
    @Ignore
    public Transaction(int typeId, int walletId,int value, Date date, String description,int inorout) {
        this.typeId = typeId;
        this.walletId = walletId;
        this.value = value;
        this.date = date;
        this.description = description;
        this.inorout = inorout;
    }

    public int getInorout() {
        return inorout;
    }

    public void setInorout(int inorout) {
        this.inorout = inorout;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
