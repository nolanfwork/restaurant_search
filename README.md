1. I have set up my localhost to localhost:8081.
2. If any steps involving creation of server has arisen, I used "/usr/local/Cellar/tomcat@9/9.0.58/libexec" when telling Eclipse where is the tomcat resource is (notice the @9 after tomcat).
Deployment Steps:
1. Load  directly from Eclispe by File->Open the root Projects folder from File System;
2. Please find "Servers" folder and use the configurations there;
3. Load the sql file "RestaurantDB_Initialize.sql" from "setups" folder first in MySQLWorkbench before running the website;
4. Configure Build Path to include external jars "gson-2.8.9.jar" and "mysql-connector-java-8.0.21.jar" for project (both of them are in "setups" folder);
5. Choose "index.jsp" file to run (which is the homepage).