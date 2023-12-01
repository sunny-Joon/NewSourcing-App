package com.paisalo.newinternalsourcingapp.Modelclasses;

public class ManagerModel {

        private String name;
        private String placeGroupCode;
        private String branchCreator;

        public ManagerModel(String name, String placeGroupCode, String branchCreator) {
            this.name = name;
            this.placeGroupCode = placeGroupCode;
            this.branchCreator = branchCreator;
        }


    public String getName() {
            return name;
        }

        public String getPlaceGroupCode() {
            return placeGroupCode;
        }

        public String getBranchCreator() {
            return branchCreator;
        }
}
