# Solstice Backend Coding Challenge - Java

## Deployment
This project is currently deplyed [here](url) on Heroku. It uses [Travis CI](https://travis-ci.org/) to automatically test and redeploy the application.

It should be noted that the first requests made to the Application may be slow as Heroku puts free tier dynos to sleep after some time. The contact `id`s will also increment by odd amounts due to how [ClearDB](http://w2.cleardb.net/) does their incrementing.

## Assumptions
I have made the following assumptions based on the document provided:

* Profile images will be stored in a separate location. Storing them locally could cause issues with migrating the server, because of this the `profileImage` should be a URL pointing to a image.

* The `email` data field should be unique. Errors this may cause in data creation are properly handled and responses contain proper error messages.

## Running
To run this project:

 1. Download the Zip and extract it.
 2. `cd <wherever-you-unzipped-it>`
 3. `mvn test `
 	- To test the project. More info on tests provided below
 4. `mvn spring-boot:run` 
	- Will compile and run the project.
 5. `mvn package`
	- Will build a .jar file

You can then start hitting endpoints using Postman or whatever HTTP client you like.

### Resources

There is a single resource in this API, the `contact`. Who's model is as follows:

|     Field     |  Type  | Required | Unique |      Notes     |
|:-------------:|:------:|:--------:|:------:|:--------------:|
|       id      |  Long  |   true   |  true  | auto-increment |
|      name     | String |   true   |  false |                |
|    company    | String |   false  |  false |                |
|  profileImage | String |   false  |  false | is a URL       |
|     email     | String |   true   |  true  |                |
|   birthDate   | String |   true   |  false |                |
|   workPhone   | String |   false  |  false |                |
| personalPhone | String |   false  |  false |                |
|    address    | String |   true   |  false |                |
|      city     | String |   true   |  false |                |
|     state     | String |   true   |  false |                |

JSON for a `contact` resource would appear as follows:
```
{
    "id": 1,
    "name": "Daffy Duck",
    "company": "Acme Inc.",
    "profileImage": "https://i.imgur.com/16ta98z.gif",
    "email": "daffy@acme.com",
    "workPhone": "555-123-7890",
    "personalPhone": "098-732-1555",
    "address": "123 Looney Lane",
    "city": "Los Angeles",
    "state": "CA",
    "birthdate": "1937-04-17"
}
```

### Endpoints
The API has the following endpoints exposed, all of which are behind /api. All requests and responses are done using JSON format.

#### `GET /contacts`
##### Request Parameters:
* None
##### Response:
###### Codes:
* `200 OK`
###### Data:
An Array of all contacts.
***

### `GET /contacts/{id}`
##### Request Parameters:
* `id` of a contact resource being requested.
##### Response:
###### Codes:
* `200 OK`

* `404 Not Found`
###### Data:
The single contact resource being requested.
***
### `POST /contacts`
##### Request Parameters:
* Contact data in a request body to be saved
##### Response:
###### Codes:
* `201 Created`

* `409 Conflict` if the `email` field is not unique
###### Data:
Returns the newly created contact resource
***
### `DELETE /contacts/{id}`
##### Request Parameters:
* `id` of a contact resource being deleted.
##### Response:
###### Codes:
* `204 No Content`

* `404 Not Found`
###### Data:
Empty
***
### `PUT /contacts/{id}`
##### Request Parameters:
* `id` of a contact resource being updated.
* Contact data in the Request Body to be updated
##### Response:
###### Codes:
* `200 OK`

* `409 Conflict` if the `email` field is not unique
###### Data:
The updated contact resource.
###### Notes:
* A `PUT` request to an `id` not in use will result in the creation of a new resource.
***
### `GET /contacts/search/{query}?value={param}`
##### Request Parameters:
* `query` - the type of query being run available queries include `[phone, email, state, city]`.
* `param` - the value to be searched against.
##### Response:
###### Codes:
* `200 OK`

* `400 Bad Request` if the value of `query` is not supported.
###### Data:
Any resources matching the search.
###### Notes: 
* `GET /api/contacts/search/phone?value=5550123` will return contacts whos work **OR** personal phone number matches the value.