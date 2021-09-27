Contents:
1.About The Project
2.Build and compile
3.Deployment and Run the application

About The Project:

This project aims at indexing the text files present within a directory into an in memory data structure and return the results
like file name and match percentage when a query line is searched for. This is a command line application. The project is
developed with some assumptions like
    - the directory contains the text files only
    - the match is case insensitive,
    - word to word match is irrespective of the presence of special characters
    - the match computing logic is a simple percentage of word count found within the file
    - no positioning or ordering of words in the search phrase is considered

Build and Compile:
    This is a maven project with java as programming language. Simple maven command can be used to compile and
    generate the executable jar file. Use the below command.
        mvn clean install
Deploy Run the application:
    - Once the project is built using the maven command, go to the target directory present in the root level of the project.
    - copy the filesearch-1.0-SNAPSHOT.jar file onto the location where you need to run the application
    - Execute the below java command to run the application.
        java -cp <path to the jar file which is copied in above step> com.filesearch.app.WordSearchApplication <valid path to the directory of text files>
    - To come out of the application type :quit against the search command.
