<h1 align="center"> Household Financial REST API </h1>

![Badge Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Badge Spring](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Badge Mysql](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Badge JUnit5](	https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Badge spring security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![Badge completed](https://img.shields.io/static/v1?label=Status&message=Completed&color=GREEN&style=for-the-badge)

In this challenge I created a REST API for household budget.

The application must allow a person to register their income and expenses for the month, as well as generate a monthly report.

The main features implemented were:

API with routes implemented following the best practices of the REST model;

Implementation of a database for information persistence;

Automated tests;

Authentication/authorization service to restrict access to information.


## ✔️ Technologies

- ``Java ``
- ``Eclipse IDE``
- ``Maven``
- ``Spring Boot``
- ``MySQL``

## :bulb: Project Features

- `Income`
  - `Register`: Income registration through a POST to "/income" with description, value, and date information in the request body.
  
  ![post](https://user-images.githubusercontent.com/105471213/198755869-272c463e-e9e2-4951-a121-0b6dd2c13c44.png)

   ```json
      {
        "id": 1,
        "description": "salary",
        "value": 3800.0,
        "date": "2022-08-01"
    }
    ```
     - `Income Listing`: listing of all incomes, and it must accept GET requests for the URI "/income".
     
     ![get](https://user-images.githubusercontent.com/105471213/198758662-5452308e-a110-48c4-afb3-8de173e53e4d.png)
     
     ```json
      {
        "id": 1,
        "description": "Salary",
        "value": 3800.0,
        "date": "2022-08-01"
    },
    {
        "id": 2,
        "description": "Bonus",
        "value": 1500.00,
        "date": "2022-08-10"
    }
    ```
     - `Searching by Id`: Search incomes by id through a type reques GET "/income/{ID}".
     
     ![get_by_id](https://user-images.githubusercontent.com/105471213/198760231-95968ea7-f3f2-476d-9ddf-072be13323d1.png)
    
    - `Searching by Year and Month`: Search incomes by Year and Month through a type reques GET "/income/{year}/{month}".
    
    ![get_year](https://user-images.githubusercontent.com/105471213/198763727-baf817bf-d1da-433f-8722-9d5b9061d51f.png)

    - `Update`: Uptade an income through a type request PUT "/income/{id}}".
    
    ![put](https://user-images.githubusercontent.com/105471213/198766671-afc0abb3-daad-4ee2-839a-2be2fb8f3ec4.png)
    
    - `Delete`: Delete an income through a type request DELETE "/income/{id}".
    
    ![delete](https://user-images.githubusercontent.com/105471213/198767762-906fbbef-24d1-4e04-ae82-10bee716b5ab.png)
 _______________________________________________________________________________________________________________________________________________________________________
- `Expense`
  - `Register`: Expense registration through a POST to "/expense" with description, value, date, and category information in the request body.
  
  ![post](https://user-images.githubusercontent.com/105471213/198772133-d6385449-9bf3-467b-ae36-bdf29242996c.png)

   ```json
      {
        "id": 1,
        "description": "Rent",
        "value": 1800.00,
        "date": "2022-08-01",
        "category": "HOUSING"
    }
    ```
    
     - `Expense Listing`: listing of all expenses, and it must accept GET requests for the URI "/expense".
     
     ![get](https://user-images.githubusercontent.com/105471213/198778739-9bd8e352-2ea5-4236-a3e1-536f0ebc0be0.png)
     
     ```json
      {
        "id": 1,
        "description": "Rent",
        "value": 1800.00,
        "date": "2022-08-01",
        "category": "HOUSING"
    },
    {
        "id": 2,
        "description": "Fuel",
        "value": "80.00",
        "date": "2022-08-10",
        "category": "TRANSPORT"
    }
    ```
    
    - `Searching by Id`: Search expenses by id through a type reques GET "/expense/{ID}".
     
    ![get_id](https://user-images.githubusercontent.com/105471213/198779038-4f167585-3f15-48ad-9c1b-0a53424d5df2.png)
    
    - `Searching by Year and Month`: Search expense by Year and Month through a type reques GET "/expense/{year}/{month}".
    
    ![get_year_month](https://user-images.githubusercontent.com/105471213/198780084-b5b20ec1-eb05-4237-8883-50e394d81b5b.png)

    - `Update`: Uptade an expense through a type request PUT "/expense/{id}}".
    
    ![put](https://user-images.githubusercontent.com/105471213/198780379-5cddc8bb-7a56-4c06-86c5-0ee46c0cfa72.png)
    
    - `Delete`: Delete an expense through a type request DELETE "/expense/{id}".
    
    ![delete](https://user-images.githubusercontent.com/105471213/198780806-a6470fc8-6157-40e3-ac92-77f48b66daeb.png)
    
_______________________________________________________________________________________________________________________________________________________________________
- `Summary`

  - `Monthly summary`: Should show total amount of income in the month, total amount of expenses in the month, ending balance in the month, and total amount spent in the month in each of the categories. Through a type reques GET "/summary/{year}/{month}".
  
  ![monthly_summary](https://user-images.githubusercontent.com/105471213/198788326-47dc7740-536a-4c7b-a4b5-d94b6df1d8c2.png)
  
   ```json
      {
        "year": 2022,
        "month": 8,
        "totalIncomes": 33800.0,
        "totalExpenses": 9495.5,
        "totalByCategory": [
        {
            "category": "TRANSPORT",
            "total": 80.0
        },
        {
            "category": "HOUSING",
            "total": 1800.0
        },
        {
            "category": "OTHER",
            "total": 7600.0
        },
        {
            "category": "FOOD",
            "total": 15.5
        }
    ],
    "balance": 24304.5
      }
    ```
  

