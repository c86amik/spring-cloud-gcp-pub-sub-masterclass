# spring-cloud-gcp-pub-sub-masterclass

## A good understanding of the usage of Google Cloud Pub-Sub, Topics and Subscription concept using Spring Boot

### Software Required
* [Java 1.8](https://www.oracle.com/in/java/technologies/javase/javase8-archive-downloads.html)
* [Spring tool Suite](https://spring.io/tools) or [Eclipse](https://www.eclipse.org/downloads/packages/)
* [Apache Maven](https://maven.apache.org/download.cgi)
* [Git Bash](https://gramfile.com/git-bash-download/)
* [Google Cloud SDK](https://cloud.google.com/sdk/docs/install) - Google Cloud SDK to register Google project locally
* [Postman](https://www.postman.com/downloads/)

### Steps to execute Google Cloud SDK
* Install Google Cloud SDK
* After successful installation open the Cloud SDK
* Run the command as `gcloud auth application-default login`
* It will open your default browser and authorize the google account that you have used in Google Cloud Console to configure the services
* After that it will store the <strong>application_default_credentials.json</strong> file in your local drive.

### Steps to clone and run the application
* Follow the steps to install and configure [Google Cloud SDK](https://github.com/c86amik/spring-cloud-gcp-pub-sub-masterclass#steps-to-execute-google-cloud-sdk) with your Google account in your local machine. So that your local machine can connect with Google Cloud Components.
* Open Git Bash or even you can open Command Prompt (if you are using Windows) or Terminal (if you are using MAC) in your machine
* Clone the application from github.com as   
<code>git clone https://github.com/c86amik/spring-cloud-gcp-pub-sub-masterclass.git</code>
* Open either <strong>STS</strong> or <strong>Eclipse</strong> and import the application as <strong>Maven</strong> project
* After the application got successfully imported in either <strong>STS</strong> or <strong>Eclipse</strong>
* Right Click on the application, select the <strong>Run As</strong> option, and then select <strong>Spring Boot App</strong>
* The application will start in the port <strong>7121</strong>
* Open the Postman and test the REST Endpoints

### Testing using Postman
<ol>
<li><strong>Create Topic and Subscription in Google Cloud Console</strong> - localhost:7121/createTopic/{topicName}/{subscriptionName}. Here the {topicName} and {subscriptionName} are the Topic Names and Subscription Names you are providing as user input to create in Google Cloud rather than going in GCP Console to create</li>
<li><strong>Publish a message in Google Cloud Pub-Sub Topic</strong> - localhost:7121/publishMessage</li>
<li><strong>Pull Message from Google Cloud Pub-Sub Topic</strong> - localhost:7121/pullMessage</li>
<li><strong>Delete Subscription from Google Cloud Pub-Sub</strong> - localhost:7121/deleteSubscription</li>
<li><strong>Delete Topic from Google Cloud Pub-Sub</strong> - localhost:7121/deleteTopic</li>
</ol>

#### Dummy Post Method RequestBody
* Body for the <strong>POST</strong> method to publish a message in Google Cloud Pub-sub Topic. For this method the body type is `raw` and then select `Text` from Postman. Provide the message in text format. Like, `"Hi! Welcome to Google Cloud Pub-Sub"`.

