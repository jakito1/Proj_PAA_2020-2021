package com.pa.proj2020.adts.graph;

public class FileObject {

    private String userNamesFile, relationshipsFile, interestNamesFile, interestsFile;

    public FileObject(String userNamesFile, String relationshipsFile, String interestNamesFile, String interestsFile) {
        this.userNamesFile = userNamesFile;
        this.relationshipsFile = relationshipsFile;
        this.interestNamesFile = interestNamesFile;
        this.interestsFile = interestsFile;
    }

    public FileObject() {
        this.userNamesFile = "user_names.csv";
        this.relationshipsFile = "relationships.csv";
        this.interestNamesFile = "interest_names.csv";
        this.interestsFile = "interests.csv";
    }

    public String getUserNamesFile() {
        return userNamesFile;
    }

    public void setUserNamesFile(String userNamesFile) {
        this.userNamesFile = userNamesFile;
    }

    public String getRelationshipsFile() {
        return relationshipsFile;
    }

    public void setRelationshipsFile(String relationshipsFile) {
        this.relationshipsFile = relationshipsFile;
    }

    public String getInterestNamesFile() {
        return interestNamesFile;
    }

    public void setInterestNamesFile(String interestNamesFile) {
        this.interestNamesFile = interestNamesFile;
    }

    public String getInterestsFile() {
        return interestsFile;
    }

    public void setInterestsFile(String interestsFile) {
        this.interestsFile = interestsFile;
    }


    /**
     * Método que verifica se os nomes dos ficheiros foram inicializados
     */
    public void checkFilenames() {
        if (this.userNamesFile == null || this.relationshipsFile == null ||
                this.interestNamesFile == null || this.interestsFile == null) {
            this.userNamesFile = "user_names.csv";
            this.relationshipsFile = "relationships.csv";
            this.interestNamesFile = "interest_names.csv";
            this.interestsFile = "interests.csv";
        }

    }

    /**
     * Método que inicializa os nomes dos ficheiros
     *
     * @param userNamesFile     representa o ficheiro com os nomes dos utilizadores
     * @param relationshipsFile representa o ficheiro com relationships
     * @param interestNamesFile representa o ficheiro com o nome dos interesses
     * @param interestsFile     representa o ficheiro com os interesses
     */
    public void setFileNames(String userNamesFile, String relationshipsFile,
                             String interestNamesFile, String interestsFile) {
        this.userNamesFile = userNamesFile;
        this.relationshipsFile = relationshipsFile;
        this.interestNamesFile = interestNamesFile;
        this.interestsFile = interestsFile;
        this.checkFilenames();
    }


}
