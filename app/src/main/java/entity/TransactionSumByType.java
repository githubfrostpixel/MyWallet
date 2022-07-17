package entity;

import androidx.room.ColumnInfo;

public class TransactionSumByType {
    @ColumnInfo(name = "TypeID")
    private int TypeID;
    @ColumnInfo(name = "Total")
    private int Total;

    public int getTypeID() {
        return TypeID;
    }

    public void setTypeID(int typeID) {
        TypeID = typeID;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
