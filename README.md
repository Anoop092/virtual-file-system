# Virtual File System (VFS)

## Overview

Virtual File System (VFS) is a Java-based command-line application that simulates a real file system entirely in memory. It supports creating, navigating, modifying, copying, moving, and deleting files and directories through a 
Linux-like command interface. The project also includes persistence, allowing the file system state to be saved and restored.

## Features

* Create and delete directories
* Create, read, update, and delete files
* Navigate using relative and absolute paths
* Support for `.` and `..` path navigation
* Rename files and directories
* Move files and directories
* Copy files and directories recursively
* Display current working directory
* List directory contents
* Print the complete directory tree
* Save and load the virtual file system using JSON

## Technologies Used

* Java 17+
* Maven
* Gson
* JUnit 5 (basic tests)

## Project Structure

```text
src
├── main
│   ├── java
│   │   ├── cli
│   │   ├── domain
│   │   ├── persistence
│   │   ├── mapper
│   │   └── exceptions
│   └── resources
└── test
```

## Supported Commands

| Command                     | Description                        |
| --------------------------- | ---------------------------------- |
| `mkdir <name>`              | Create a directory                 |
| `touch <name>`              | Create a file                      |
| `ls`                        | List contents of current directory |
| `cd <path>`                 | Change directory                   |
| `pwd`                       | Print current directory            |
| `write <file> <content>`    | Write content to a file            |
| `cat <file>`                | Display file contents              |
| `rm <file>`                 | Delete a file                      |
| `rmdir <directory>`         | Delete an empty directory          |
| `rename <old> <new>`        | Rename a file or directory         |
| `mv <source> <destination>` | Move a file or directory           |
| `cp <source> <destination>` | Copy a file or directory           |
| `tree`                      | Display the directory tree         |
| `save`                      | Save the file system               |
| `load`                      | Load the saved file system         |
| `exit`                      | Exit the application               |

## Example Session

```text
/> mkdir docs
/> cd docs
/docs> touch notes.txt
/docs> write notes.txt Hello World
/docs> cat notes.txt
Hello World

/docs> mkdir images
/docs> ls
images
notes.txt

/docs> pwd
/docs

/docs> save
File system saved successfully.
```

## Design Highlights

* Tree-based file system representation
* Object-oriented design using inheritance and composition
* Recursive traversal for copy and tree operations
* JSON-based persistence using Gson
* Separation of domain, persistence, mapping, and CLI layers

## Future Improvements

* Comprehensive unit and integration testing
* File permissions and ownership
* Search functionality
* Command history
* Undo/Redo operations
* Symbolic links
* Compression support
* Improved error reporting

## Learning Outcomes

This project helped reinforce:

* Object-Oriented Programming
* Tree data structures
* Recursive algorithms
* File system design concepts
* JSON serialization and deserialization
* Layered architecture
* Exception handling
* Command-line application development

## Author

**Anoop Vantagodi**
