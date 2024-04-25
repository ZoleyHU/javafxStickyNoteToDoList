# Sticky Note To Do List javafx
To do list app, with the intention of looking like sticky notes, made with javafx. You can add new notes with given deadlines, delete or just postpone them.
## Requirements
To run the app, you will need
* JDK
* Maven
* Sqlite3
* JDBC
## First steps
After you cloned the project please go into the db folder, and run the following commands with sqlite3:
```
sqlite3 notes.db
.read createScript.sql
```
Note that **notes.db** is the name of the db file that is created, so it can be replaced with anything you want.
After the db file is created, copy it's absolute path and replace the placeholder text in the application.properties file to it.
The file path might need some formatting, but it should look like this:
```
db.url=jdbc:sqlite:G:/StickyNotes/src/main/resources/hu/sticky/db/notes.db
```
Do not forget to run
```
nvm install
```
before running the project with
```
javafx:run
```