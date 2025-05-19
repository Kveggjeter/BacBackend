
Backend for Simply Tidings
===============================

# Overview

[Simply Tidings](https://www.simplytidings.com/) is a GIS for news, where the sources are scraped from various sources. 

This is the backend of said GIS. It contains webcrawler logic and webscraping.

There are two third-party dependencies you need for running this.
* A Redis database, for saving articles (i also use postgres for updating scraping properties, advisible)
* A webdriver, for accsessing HTML.

Ideally you would interact with a LLM for georeferencing. There is already logic and implementation for OpenAI,
but if you want to use your own algorithms or another LLM you can simply make an implementation for that.
