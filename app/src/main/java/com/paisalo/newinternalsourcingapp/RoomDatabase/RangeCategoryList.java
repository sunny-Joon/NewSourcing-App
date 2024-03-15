package com.paisalo.newinternalsourcingapp.RoomDatabase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "RangeCategory_List", foreignKeys = @ForeignKey(entity = RangeCategoryDataClass.class,
        parentColumns = "id",
        childColumns = "parentId",
        onDelete = ForeignKey.CASCADE))
public class RangeCategoryList {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long parentId;
    public String value;

    public RangeCategoryList(long parentId, String value) {
        this.parentId = parentId;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
