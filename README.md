# DataBaseProject
Developing a database for trading institutions with a nice, user friendly desktop application to interact with data in an easy, comfortable way.

Introduction:
Trading companies are businesses working with different kinds of products which are sold for consumer or business purposes. Trading companies buy a specialized range of products, maintain a stock or a shop, and deliver products to customers.
In these kind of business, they do not have the time or resources available to gather and process large quantities of information. This may lead to a lack of information about how their business is performing or how profitable their product lines are.
Here comes the need of our project. Developing a database for trading institutions, to help reduce the amount of time spent on managing data, to analyze data in a variety ways, turn disparate information into a valuable resource and of course to improve the quality and consistency of information.

Requirements:

Data requirements:
Our company database keeps track of employees and the departments they associate. The data base also keeps track of the products and the international exhibitions they attend in order to import products.
For each employee, the database maintains information on the person’s fname, sname, lname, unique ssn, phone number, birth date and address.
This employee may be a procurement engineer with additional attributes of certification and years of experiment.
 Employees are related to departments [works for] (an employee can be associated with one department, so the relationship is 1:N).
A department has the attributes of Location, unique Department number, Phone number, Department name, Opening time, Closing time.
Every department store particular product types, each of has a Name, unique Product code, Cost, Quantity, Number of warranty years, Manufacturer, Country of Origin and one manager. Managers can manage more than one department.
We also have an International Exhibition Entity which has attributes of Name, Country, Date and Travelling Cost.

Functional Requirement:
•	There are two relations between Employee and Department .Works for ,that every employee follow one department and every department has many employees, Manages, which has a relational attribute that is Start Date,
And every department has a manager, and a manager can run more than one department.

•	Between Department and Product there exist a Stored in relation, that every product follow one department and every department has many products.

•	Between Employee and International Exhibition there exist an Attends relation, that each exhibition can be attended by one employee, and this employee can elaborate more than one exhibition.
