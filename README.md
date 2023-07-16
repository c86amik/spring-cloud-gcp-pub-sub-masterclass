# spring-cloud-gcp-sql-storage-masterclass

## A good understanding of the usage of spring-data with REST Service and using Cloud SQL as Cloud Database and to store media files in Cloud Storage

### Software Required
* [Java 1.8](https://www.oracle.com/in/java/technologies/javase/javase8-archive-downloads.html)
* [Spring tool Suite](https://spring.io/tools) or [Eclipse](https://www.eclipse.org/downloads/packages/)
* [Apache Maven](https://maven.apache.org/download.cgi)
* [Git Bash](https://gramfile.com/git-bash-download/)
* [MySQL](https://dev.mysql.com/downloads/mysql/) - Download the Community Edition. If it is a problem then you can download another one as provided below
* [SQLYog](https://sqlyog.en.softonic.com/) <strong>/</strong> [DBeaver](https://dbeaver.io/download/) - You can use this as replacement of MySQL
* [Google Cloud SDK](https://cloud.google.com/sdk/docs/install) - Google Cloud SDK to register Google project locally
* [Google Cloud SQL Proxy](https://cloud.google.com/sql/docs/mysql/sql-proxy#install) - Cloud SQL Proxy as required to connect with Cloud SQL locally
* [Postman](https://www.postman.com/downloads/)

### Steps to execute Google Cloud SDK
* Install Google Cloud SDK
* After successful installation open the Cloud SDK
* Run the command as `gcloud auth application-default login`
* It will open your default browser and authorize the google account that you have used in Google Cloud Console to configure the services
* After that it will store the <strong>application_default_credentials.json</strong> file in your local drive.

### Steps to execute the DB Scripts
Run the below scripts in any of the software either in MySQL Workbench or in SQLYog or in DBeaver

* user-sql-storage-ddl.sql - It will create the table named as <strong>user</strong>
* hibernate_sequence.sql - It will create the table <strong>hibernate_sequence</strong> and will insert 1 record from where the next sequence will start

There is no sequencing that you have to run first the `hibernate_sequence.sql` and then `user-sql-storage-ddl.sql` or vice-versa. But you have to remember one point that before start up of the application these two tables should be present in DB.

* user-sql-storage-ddl.sql - It will create the table <strong>User</strong> under <strong>sql-storage</strong> schema

* hibernate_sequence.sql - It will create the table <strong>hibernate_sequence</strong> under <strong>sql-storage</strong> schema

### Steps to clone and run the application
* Follow the steps to install and configure [Google Cloud SDK](https://github.com/c86amik/spring-cloud-gcp-sql-storage-masterclass#steps-to-execute-google-cloud-sdk) with your Google account in your local machine. So that your local machine can connect with Google Cloud Components.
* Install MySQL. Complete installation steps of [MySQL Workbench](https://www.sqlshack.com/how-to-install-mysql-database-server-8-0-19-on-windows-10/) are provided
* If you face any problem while installing MySQL Workbench, then you can install SQLYog / DBeaver as mentioned in the Software Required steps
* Open Git Bash or even you can open Command Prompt (if you are using Windows) or Terminal (if you are using MAC) in your machine
* Clone the application from github.com as   
<code>git clone https://github.com/c86amik/spring-cloud-gcp-sql-storage-masterclass.git</code>
* Open either <strong>STS</strong> or <strong>Eclipse</strong> and import the application as <strong>Maven</strong> project
* After the application got successfully imported in either <strong>STS</strong> or <strong>Eclipse</strong>
* After this go to the folder where you had download the Cloud SQL Proxy from CMD
* Run the command as `cloud-sql-proxy --port 3307 spring-gcp-sql-storage:asia-south1:gcp-sql-storage`. Here, I have used the port no `3307` as `3306` is occupied by local MySQL. And `spring-gcp-sql-storage:asia-south1:gcp-sql-storage` is the connection string of Cloud SQL. Nomenclature of the Cloud SQL connection is `projectId:region:instanceId`
* Execute the SQL scripts as mentioned in the above [DB Script](https://github.com/c86amik/spring-cloud-gcp-sql-storage-masterclass#steps-to-execute-the-db-scripts) execution steps
* Right Click on the application, select the <strong>Run As</strong> option, and then select <strong>Spring Boot App</strong>
* The application will start in the port <strong>7110</strong>
* Open the Postman and test the REST Endpoints

### Testing using Postman
<ol>
<li><strong>Upload Files in GCP Cloud Storage</strong> - localhost:7120/uploadFile</li>
<li><strong>Save a User in GCP Cloud SQL</strong> - localhost:7120/saveUser</li>
<li><strong>Update a User in GCP Cloud SQL</strong> - localhost:7120/updateUser/{id}. Here <strong>{id}</strong> is the id of the record stored in Cloud SQL</li>
<li><strong>Delete a User from GCP Cloud SQL</strong> - localhost:7120/deleteUser/{id}</li>
<li><strong>Get user by Id from Cloud SQL</strong> - localhost:7120/getUser/{id}</li>
<li><strong>Get all users from Cloud SQL</strong> - localhost:7120/allUsers</li>
</ol>

#### Dummy JSON object
* Body for the <strong>POST</strong> method to upload file in GCP Cloud Storage. For this method the body type is `form-data`. Provide the value in key-value pair concept from Postman. It takes the parameters in request parameter concept.

<code>"file" : "<Uplaod a file from your local system>"
"name" : "Name of the file without extension"
"fileName" : "Name of the file with extension"</code>

* Body for the <strong>POST</strong> method to insert record in GCP Cloud SQL
 
<code>{
	"name" : "Test Name",
	"mobileNo" : "1234567890",
	"profilePhoto" : "sqlstorage/test.png",
	"profileUrl" : "https://storage.googleapis.com/download/storage/v1/b/gcp-sql-storage/o/sqlstorage%2Ftest.png?generation=1687665501945010&alt=media"
}</code>

* Body for the <strong>PUT</strong> method to upload record in GCP Cloud SQL

<code>{
	"userId" : 1,
	"name" : "Test Name",
	"mobileNo" : "1234567891",
	"profilePhoto" : "sqlstorage/test2.png",
	"profileUrl" : "https://storage.googleapis.com/download/storage/v1/b/gcp-sql-storage/o/sqlstorage%2Ftest2.png?generation=1687665501945010&alt=media"
}</code>
