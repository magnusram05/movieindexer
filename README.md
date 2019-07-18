# movieindexer

This module uses Elastic search JAVA REST client to index documents in Elasticsearch

* An elasticsearch index called 'movie' is created with 5 primary and 1 replica shard to hold the movie data
* Movie data is downloaded as a file from [IMDb](https://datasets.imdbws.com/)
* File data is parsed and indexed into 'movie' index
 
