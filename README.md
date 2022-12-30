

## ImageFinder Goal
The goal of this task is to perform a web crawl on a URL string provided by the user. From the crawl, you will have all of the parsed images on that web page and return a JSON array of strings that represent the URLs of all images on the page. [Jsoup](https://jsoup.org/) is a great library for crawling that was used in this project.

### Functionality
- Web crawler can find all images on the web page(s) that it crawls.
- Crawl sub-pages to find more images.
- Implement multi-threading so that the crawl can be performed on multiple sub-pages at a time.
- Keeps your crawl within the same domain as the input URL.
- Avoids re-crawling any pages that have already been visited.

## Structure
The ImageFinder servlet is found in `src/main/java/com/eulerity/hackathon/imagefinder/ImageFinder.java`. This is the only provided Java class. 

The main landing page for this project can be found in `src/main/webapp/index.html`. This page contains more instructions and serves as the starting page for the web application. 

## Running the Project
Here we will detail how to setup and run this project so you may get started, as well as the requirements needed to do so.

### Requirements
Before beginning, make sure you have the following installed and ready to use
- Maven 3.5 or higher
- Java 8
  - Exact version, **NOT** Java 9+ - the build will fail with a newer version of Java

### Setup
To start, open a terminal window and navigate to wherever you unzipped to the root directory `imagefinder`. To build the project, run the command:

>`mvn package`

If all goes well you should see some lines that end with "BUILD SUCCESS". When you build your project, maven should build it in the `target` directory. To clear this, you may run the command:

>`mvn clean`

To run the project, use the following command to start the server:

>`mvn clean test package jetty:run`

You should see a line at the bottom that says "Started Jetty Server". Now, if you enter `localhost:8080` into your browser, you should see the `index.html` welcome page! If all has gone well to this point, you're ready to begin!

<!-- ## Submission
When you are finished working on the project, before zipping up and emailing back your submission, **PLEASE RUN ONE LAST `mvn clean` COMMAND TO REMOVE ANY UNNECESSARY FILES FROM YOUR SUBMISSION**. Please also make sure to add the URLs you used to test your project to the `test-links.txt` file. After doing these things, you may zip up the root directory (`imagefinder`) and email it back to us. -->
