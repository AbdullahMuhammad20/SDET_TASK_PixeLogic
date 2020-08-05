## Register and login features in PHPTravels

#### *Prerequisites*
- JDK 1.8
- IDE (IntelliJ OR Eclipse)

#### *Running*
- You can run tests throughout "TestRegister.java" class from (src>main>java>test>TestRegister.java)

#### *Classes architecture*
- **pages**

1 - Account Class
    
    - Definie account page locators
    - Function to return welcome message as a string
    - Function to logout from account page
    
2 - Login Class

    - Definie login page locators
    - Functions to find locators and interact with them
    - Function to login with (Email - Password)

3 - Register Class

    - Definie register page locators
    - String text to use in validation alert message
    - Functions to find locators and interact with them
    - Function to check if the element is display or not!
    - Function to check alert message if alert dialog is display
    - Group of test functions to check validation of register page items
    - Function to get the Response after send the request with Json content and save this content in txt File 
    - Register function to set values to items and click register 
    
- **test**

1 - TestRegister

    - Define object of classes used in TestRegister
    - Define Data pass to use in register function, also we can use "DATA DRIVEN" feature
    - Use anotations of testNG to control and manage before and after tests
    - Tests use to run and check **Register** feature and **Login** feature and validation on elements 
 
- **utility**

1 - Utility Class

    - Function to return current date as a string in order to use it when the report is saved
    - Function to capture screenshot and save it in the project and return screen URL

#### *This Simple project build with*
- Java programming language
- Selenium framework
- TestNG framework
- Maven
- apache.io "to save report files"
- Extent Reports
- Json "to get response after request and save it"
  
## *Important Notes*
- It is preferable that the test data be modified before run the tests
- Open this project as a maven project