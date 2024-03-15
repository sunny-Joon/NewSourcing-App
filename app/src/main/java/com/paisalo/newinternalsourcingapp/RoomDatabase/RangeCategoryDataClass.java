package com.paisalo.newinternalsourcingapp.RoomDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "RangeCategory_Database")
public class RangeCategoryDataClass {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String cat_key;
    public String groupDescriptionEn;
    public String groupDescriptionHi;
    public String descriptionEn;
    public String descriptionHi;
    public int sortOrder;
    public String code;

    public RangeCategoryDataClass(String cat_key, String groupDescriptionEn, String groupDescriptionHi,
                                  String descriptionEn, String descriptionHi, int sortOrder, String code) {
        this.cat_key = cat_key;
        this.groupDescriptionEn = groupDescriptionEn;
        this.groupDescriptionHi = groupDescriptionHi;
        this.descriptionEn = descriptionEn;
        this.descriptionHi = descriptionHi;
        this.sortOrder = sortOrder;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCat_key() {
        return cat_key;
    }

    public void setCat_key(String cat_key) {
        this.cat_key = cat_key;
    }

    public String getGroupDescriptionEn() {
        return groupDescriptionEn;
    }

    public void setGroupDescriptionEn(String groupDescriptionEn) {
        this.groupDescriptionEn = groupDescriptionEn;
    }

    public String getGroupDescriptionHi() {
        return groupDescriptionHi;
    }

    public void setGroupDescriptionHi(String groupDescriptionHi) {
        this.groupDescriptionHi = groupDescriptionHi;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionHi() {
        return descriptionHi;
    }

    public void setDescriptionHi(String descriptionHi) {
        this.descriptionHi = descriptionHi;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
