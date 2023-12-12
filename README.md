# Match Service API Documentation

The Match Service is a project that provides a platform for scheduling appointments between clients and service
providers. This README provides an overview of the available APIs and their usage.

## Table of Contents

- [API Endpoints](#api-endpoints)
- [API Usage](#api-usage)
- [Data Models](#data-models)
- [Getting Started](#getting-started)

## API Endpoints

The Match Application provides the following API endpoints:

1. **Client API**

    - `GET /client/{id}`: Get a specific client by ID.
    - `GET /clientByName/{id}:`  Get a client by its name  
    - `GET /clients`: Get a list of all clients.  
    - `GET /client/{id}/consumer/{name}`: Get a consumer by name (case ignored)
    - `GET /client/{id}/allProviders`: Get all providers in a given client ID
    - `GET /client/{id}/getAvailability`: Get availability for a service provider
    - `POST /clients`: Add a new client.
    - `DELETE /client/{id}`: Delete a client by ID.
    - `POST /client/{id}/consumer`: Add a consumer to a client.
    - `POST /client/{id}/serviceProvider`: Add a service provider to a client.
    - `POST /client/{id}/service`: Add a new service to the client, that service providers use.
    - `POST /client/{id}/addAvailability`: Post availability for a service provider
    - `POST /client/{id}/consumerRequest`: Add a consumer request for appointment scheduling.
    - `DELETE /client/{id}/consumer/{consumerId}`: Delete a consumer from a client.
    - `DELETE /client/{id}/service_providers/{serviceProviderId}`: Delete a service provider from a client.
    - `DELETE /client/{id}/deleteAvailability`: Delete availability for a service provider.
    - `DELETE /client/{id}/consumerRequest/{consumerRequestId}`: Delete a consumer request.

2. **Matching and Scheduling API**

    - `POST /client/{id}/consumerRequest`: Sort and retrieve available service providers based on consumer request.

3. **Appointment API**

    - `POST /client/{id}/bookAppointment`: Book an appointment with a service provider.
    - `DELETE /client/{id}/appointment/{appointmentId}`: Cancel an appointment.

4. **Review API**
    - `GET /client/{id}/review`: Get all reviews existing for the given client ID
    - `POST /client/{id}/addReview`: Post review for the given client ID
    - `DELETE /client/{id}/review/{reviewId}`: Delete review with given ID,for the given client ID

## API Usage Notes

1. **Client API**

 - `GET /client/{id}`: Get a specific client by ID.  
	    @pathVariable id   The ID of the client to retrieve.  
        @return             The client with the specified ID.  
                HttpStatus is 400 in case no client exists for given ID  
 - `GET /clientByName/{id}:`  Get a client by its name  
        @pathVariable name      The name of the client to retrieve.  
        @return                 The client with the specified name.  
                HttpStatus is 400 in case no client exists for given name    
 - `GET /clients`: Get a list of all clients.   
	    @return    An iterable list of all clients.  
 - `GET /client/{id}/consumer/{name}`: Get a consumer by name (case ignored)
     @pathVariable id               The ID of the client.  
     @pathVariable name             The name of the consumer to retrieve.  
     @return                        The consumer with the specified name.  
                HttpStatus is 400 in case no consumer exists for given name, or if client ID is invalid  
 - `GET /client/{id}/allProviders`: Get all providers in a given client ID  
     @pathVariable consumerRequest  The consumer request with date and service type.  
     @return                        A list of service providers matching the request, sorted by proximity and rating.  
                HttpStatus is 400 in case no client exists for given ID  
 - `GET /client/{id}/getAvailability`: Get availability for a service provider               
     @pathVariable id               The ID of the client.  
     @param serviceProvider         The new service provider to add.  
     @return                        The availability of the service provider.  
                HttpStatus is 400 in case no client/service-provider exist for given ID  
 - `POST /clients`: Add a new client.  
     @pathVariable client   The new client to add.  
     @return                The newly created client.  
 - `DELETE /client/{id}`: Delete a client by ID.  
     @param id The ID of the client to delete.
 - `POST /client/{id}/consumer`: Add a consumer to a client.  
     @pathVariable id       The ID of the client.  
     @param consumer        The new consumer to add.  
     @return                The newly created consumer.  
                HttpStatus is 400 in case no client exists for given ID  
 - `POST /client/{id}/serviceProvider`: Add a service provider to a client.  
     @pathVariable id               The ID of the client.  
     @param serviceProvider         The new service provider to add.  
     @return                        The newly created service provider.  
                HttpStatus is 400 in case no client exists for given ID, OR if service provider with name already EXISTS
 - `POST /client/{id}/service`: Add a new service to the client, that service providers use  
     @param service The new service to add.  
     @return The newly created service provider.  
                HttpStatus is 400 in case one of the given the given client/service-provider id is invalid, OR if service with name already EXISTS  
 - `POST /client/{id}/addAvailability`: Post availability for a service provider  
     @pathVariable id               The ID of the client.   
     @param newAvailability         The new Availability to add.  
     @return                        The newly created Availability.  
                    HttpStatus is 400 in case one of the given the given client/service-provider id is invalid  
 - `POST /client/{id}/consumerRequest`: Add a consumer request for appointment scheduling.  
     @pathVariable id                The ID of the client.  
     @param consumerRequest         The new consumer request to add.   
     @return                        The newly created consumer request.  
 - `DELETE /client/{id}/consumer/{consumerId}`: Delete a consumer from a client.  
     @pathVariable id         The ID of the client.  
     @pathVariable consumerId The ID of the consumer to delete.  
 - `DELETE /client/{id}/service_providers/{serviceProviderId}`: Delete a service provider from a client.   
     @pathVariable id                  The ID of the client.  
     @param serviceProviderId    The ID of the service provider to delete.  
 - `DELETE /client/{id}/deleteAvailability`: Delete availability for a service provider.  
     @pathVariable id                  The ID of the client.  
     @param providerId          The ID of the service provider to delete.  
     @RequestBody               TupleDateTime expiredAvailability  
 - `DELETE /client/{id}/consumerRequest/{consumerRequestId}`: Delete a consumer request.  
     @pathVariable id                   The ID of the client.  
     @pathVariable consumerRequestId    The ID of the consumer request to delete.  

2. **Matching and Scheduling API**

    - `POST /client/{id}/consumerRequest`: Sort and retrieve available service providers based on consumer request.  
    @pathVariable id                The ID of the client.  
    @param consumerRequest          The new consumer request to add.  
    @return                         The newly created consumer request.  

3. **Appointment API**

    - `POST /client/{id}/bookAppointment`: Book an appointment with a service provider.  
    @pathVariable id            The ID of the client.  
    @param appointment          The new appointment to book.  
    @return                     The newly created appointment.  
    - `DELETE /client/{id}/appointment/{appointmentId}`: Cancel an appointment.  
    @pathVariable id              The ID of the client.  
    @pathVariable appointmentId   The ID of the appointment to delete.  

4. **Review API**
    - `GET /client/{id}/review`: Get all reviews existing for the given client ID  
    @pathVariable id                The ID of the client.  
    @return The list of all reviews of given client ID  
    - `POST /client/{id}/addReview`: Post review for the given client ID  
    @pathVariable id                The ID of the client.    
    @param review The new Review object to add.  
    @return The newly created review  
    - `DELETE /client/{id}/review/{reviewId}`:  Delete review with given ID,for  the given client ID  
    @pathVariable id                The ID of the client.  
    @pathVariable reviewId          The Review id to delete.  


## Data Models

The Match Application uses the following data models:

- `Client`: Represents a client using the application.
- `Consumer`: Represents a consumer associated with a client.
- `ServiceProvider`: Represents a service provider associated with a client.
- `ConsumerRequest`: Represents a request from a consumer for appointment scheduling.
- `Appointment`: Represents a scheduled appointment between a consumer and a service provider.
- `TupleDateTime`: An embeddable data model for specifying start and end times for events.

## Getting Started

Prerequisites:
Before you run the Match Application, since we are using a Cloud SQL server to handle persistent data, you will need the
Google Cloud CLI and MySQL installed on your laptop.

To run the Match Application, follow these steps:

1. Clone the project repository.
2. Configure your database settings in the application.
3. Build and run the application.
4. Access the APIs using the provided endpoints.

## CI Reports

Continuous integration is done using GitHub Actions, and is run on all pull requests or pushes to the master branch. 
The workflows contain coverage reports, static code analysis, and automated testing.

(As the code has multiple instances of endpoints checking different cases, achieving complete converage was laborious. Also, 
many functions are helper functions such as sorted service providers and we did not have any reliance on them in the main 
methods. So, test units for these were not critical to the service endpoint methods.)

## Style Checking

We used the Checkstyle style checker with the "Sun Checks" coding style specifications for Java.

## Testing Resource

The entire Postman [workspace][https://www.postman.com/red-trinity-392290/workspace/red-trinity-392290-workspace/overview] is now public, and the collection "New Service Tests" under this has the new API tests that passed for the updated API in this iteration.

The link to the Postman Collection with some start API's can be
found [here](https://app.getpostman.com/join-team?invite_code=409679a1dec844deadebd4a22f3c5acd&target_code=a92949bc4a1ae5d66291956ecbb26cf5).
A video of API testing via Postman can be seen [here](https://www.youtube.com/watch?v=fzW7lfTNeZs).
A link to database persistence testing can be seen [here](https://docs.google.com/document/d/1eoRkn8gq1HRkxllWjNaj0VbxDW0YpKKzI4bNtDAS-2c/edit?usp=sharing)

## Sample Client
A sample client app can be found [here](https://github.com/sm4410/coms4156-frontend). To run the app, Node.js and
Angular are required to be installed. After, you can start up the app by the command "ng serve" in the main directory
of the app. The client is configured to connect to the service running on the local machine, but you can change the
url it connects to in the environment.ts file. The app will run on port 4200 by default, and is also able to
simulate multiple clients. 


# Tests for multiple client instances

Instructions for tests with multiple instances of the client is in this [document][https://docs.google.com/document/d/1L5SUP2ngKrWnFArTArXg9g7Pf5iDaPZfepBEqO5tul8/edit?usp=sharing]  

Please contact the testers for setting up the service on the Google App Engine before running such tests.   
