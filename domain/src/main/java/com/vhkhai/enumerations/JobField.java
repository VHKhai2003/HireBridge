package com.vhkhai.enumerations;

public enum JobField {
    SOFTWARE_ENGINEERING("Software Engineering", "Backend Developer, Mobile Developer"),
    MARKETING("Marketing", "Digital Marketing Specialist, Content Strategist"),
    SALES("Sales", "Sales Representative, Account Executive"),
    HUMAN_RESOURCES("Human Resources", "HR Manager, Recruiter"),
    DESIGN("Design", "UI/UX Designer, Graphic Designer"),
    FINANCE("Finance", "Accountant, Financial Analyst"),
    PRODUCT_MANAGEMENT("Product Management", "Product Manager, Product Owner"),
    CUSTOMER_SUPPORT("Customer Support", "Customer Service Agent, Technical Support Specialist"),
    OPERATIONS("Operations", "Operations Manager, Supply Chain Analyst"),
    DATA_SCIENCE("Data Science", "Data Scientist, Data Analyst"),
    LEGAL("Legal", "Legal Counsel, Compliance Officer"),
    EDUCATION("Education", "Teacher, Instructional Designer"),
    HEALTHCARE("Healthcare", "Nurse, Medical Assistant"),
    ENGINEERING("Engineering", "Mechanical Engineer, Civil Engineer");

    private final String label;
    private final String description;

    JobField(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

}
