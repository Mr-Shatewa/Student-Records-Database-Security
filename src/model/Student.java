package model;
    public class Student {
        private int studentId;
        private String firstName;
        private String lastName;
        private String gender;
        private String programme;
        private String email;

        @Override
        public  String toString() {
            return "Student ID: " + studentId + ", Name: " + firstName + " " + lastName;
        }

        public Student (int studentId, String firstName, String lastName, String gender, String programme, String email) {
            this.studentId = studentId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.programme = programme;
            this.email = email;
            
        }

        public int getStudentId() {
            return studentId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getGender() {
            return gender;
        }

        public String getProgramme() {
            return programme;
        }

        public String getEmail() {
            return email;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setProgramme(String programme) {
            this.programme = programme;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }