package com.paisalo.newinternalsourcingapp.RoomDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ManagerListDataBase")
public class ManagerListDataClass {

    @PrimaryKey(autoGenerate = true)
    public long id;
        private long imeino;
        private String foCode;
        private String foName;
        private String creator;
        private String isActive;
        private String dataBase;
        private String tag;
        private String areaCd;
        private String areaName;
        private String creatorDesc;
        private String fiExecCode;
        private String fiExecName;

        // Constructors
        public ManagerListDataClass() {
        }

        public ManagerListDataClass(long imeino, String foCode, String foName, String creator, String isActive, String dataBase, String tag, String areaCd, String areaName, String creatorDesc, String fiExecCode, String fiExecName) {
            this.imeino = imeino;
            this.foCode = foCode;
            this.foName = foName;
            this.creator = creator;
            this.isActive = isActive;
            this.dataBase = dataBase;
            this.tag = tag;
            this.areaCd = areaCd;
            this.areaName = areaName;
            this.creatorDesc = creatorDesc;
            this.fiExecCode = fiExecCode;
            this.fiExecName = fiExecName;
        }

        public long getImeino() {
            return imeino;
        }

        public void setImeino(long imeino) {
            this.imeino = imeino;
        }

        public String getFoCode() {
            return foCode;
        }

        public void setFoCode(String foCode) {
            this.foCode = foCode;
        }

        public String getFoName() {
            return foName;
        }

        public void setFoName(String foName) {
            this.foName = foName;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getDataBase() {
            return dataBase;
        }

        public void setDataBase(String dataBase) {
            this.dataBase = dataBase;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getAreaCd() {
            return areaCd;
        }

        public void setAreaCd(String areaCd) {
            this.areaCd = areaCd;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getCreatorDesc() {
            return creatorDesc;
        }

        public void setCreatorDesc(String creatorDesc) {
            this.creatorDesc = creatorDesc;
        }

        public String getFiExecCode() {
            return fiExecCode;
        }

        public void setFiExecCode(String fiExecCode) {
            this.fiExecCode = fiExecCode;
        }

        public String getFiExecName() {
            return fiExecName;
        }

        public void setFiExecName(String fiExecName) {
            this.fiExecName = fiExecName;
        }
}
