# eLibrary Java Parser

**eLibrary Java Parser** is a Java application for automated collection and analysis of author data from the bibliographic database **eLibrary.ru**. It provides a graphical interface for input parameters and generates statistics such as total articles, h-index, zero-citation articles, and more.

---

## Features

* Extract author information from eLibrary.
* Collect statistics on author publications.
* Calculate h-index.
* Export results in JSON format.
* User-friendly GUI with JavaFX.

---

## Technologies

* **JavaFX** – GUI
* **Selenium WebDriver** – web automation
* **RestAssured** – HTTP requests
* **Jsoup** – HTML parsing
* **Stream API** – data processing
* **Gson** – JSON serialization/deserialization

---

## Requirements

* Java 17+
* Gradle
* Google Chrome and compatible ChromeDriver

---

## Installation & Run

```bash
git clone https://github.com/yourusername/eLibraryJavaParser.git
cd eLibraryJavaParser
gradle build
gradle run
```

> Ensure **ChromeDriver** is in PATH or project root.

---

## Usage

1. Launch the application.
2. Select an input `.txt` file containing author IDs (one per line).
3. Select output `.json` file for results.
4. (Optional) Configure delays for cookie retrieval or page processing.
5. Click **Run** to start processing.

JSON output example:

```json
[
  {
    "authorName": "Ivanov Ivan Ivanovich",
    "totalArticles": 10,
    "zeroCitationArticles": 3,
    "hIndex": 5,
    "zeroCitationDetails": [
      {
        "title": "Article 1",
        "author": "Ivanov I. I.",
        "place": "Journal 1"
      }
    ]
  }
]
```

---

## Project Components

* **MainGUI** – GUI management and input interface.
* **Main** – Entry point, coordinates data processing and saving.
* **ElibraryFetcher** – Handles cookie retrieval and author page access.
* **PageParser** – Parses HTML pages for article data.
* **AuthorStatistics** – Stores author statistics (total articles, h-index, etc.).
* **AuthorIdReader** – Reads author IDs from input file.
* **CookieFetcher** – Automates cookie retrieval via Selenium.
* **DataFetcher** – Performs HTTP requests to author pages.

---

## Extensibility

* Support additional platforms (Scopus, Web of Science).
* Add data analysis or visualization.
* Localize GUI to other languages.

