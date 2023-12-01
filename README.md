# Match Application API Documentation

The Match Application is a project that provides a platform for scheduling appointments between clients and service
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
    - `GET /clients`: Get a list of all clients.
    - `GET /client/{id}/allProviders`: Get all providers in a given client ID
    - `POST /clients`: Add a new client.
    - `DELETE /client/{id}`: Delete a client by ID.
    - `POST /client/{id}/consumer`: Add a consumer to a client.
    - `POST /client/{id}/serviceProvider`: Add a service provider to a client.
    - `POST /client/{id}/consumerRequest`: Add a consumer request for appointment scheduling.
    - `DELETE /client/{id}/consumer/{consumerId}`: Delete a consumer from a client.
    - `DELETE /client/{id}/service_providers/{serviceProviderId}`: Delete a service provider from a client.
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
	    @param id The ID of the client to retrieve.  
        @return The client with the specified ID.  
                HttpStatus is 400 in case no client exists for given ID  
 - `GET /clients`: Get a list of all clients.   
	    @return An iterable list of all clients. 
 - `GET /client/{id}/allProviders`: Get all providers in a given client ID  
     @param consumerRequest The consumer request with date and service type.  
     @return A list of service providers matching the request, sorted by proximity and rating.  
                HttpStatus is 400 in case no client exists for given ID  
 - `POST /clients`: Add a new client.  
     @param client The new client to add.  
     @return The newly created client.  
 - `DELETE /client/{id}`: Delete a client by ID.  
     @param id The ID of the client to delete.
 - `POST /client/{id}/consumer`: Add a consumer to a client.  
     @param id       The ID of the client.  
     @param consumer The new consumer to add.  
     @return The newly created consumer.  
                HttpStatus is 400 in case no client exists for given ID  
 - `POST /client/{id}/serviceProvider`: Add a service provider to a client.  
     @param id               The ID of the client.  
     @param serviceProvider   The new service provider to add.  
     @return The newly created service provider.  
                HttpStatus is 400 in case no client exists for given ID, OR if service provider with name already EXISTS
 - `POST /client/{id}/consumerRequest`: Add a consumer request for appointment scheduling.  
     @param id                The ID of the client.  
     @param consumerRequest   The new consumer request to add.   
     @return The newly created consumer request.  
 - `DELETE /client/{id}/consumer/{consumerId}`: Delete a consumer from a client.  
     @param id         The ID of the client.  
     @param consumerId The ID of the consumer to delete.  
 - `DELETE /client/{id}/service_providers/{serviceProviderId}`: Delete a service provider from a client.   
     @param id                  The ID of the client.  
     @param serviceProviderId    The ID of the service provider to delete.  
 - `DELETE /client/{id}/consumerRequest/{consumerRequestId}`: Delete a consumer request.  
     @param id                   The ID of the client.  
     @param consumerRequestId    The ID of the consumer request to delete.  

2. **Matching and Scheduling API**

    - `POST /client/{id}/consumerRequest`: Sort and retrieve available service providers based on consumer request.  
    @param id                The ID of the client.  
    @param consumerRequest   The new consumer request to add.  
    @return The newly created consumer request.  

3. **Appointment API**

    - `POST /client/{id}/bookAppointment`: Book an appointment with a service provider.  
    @param id          The ID of the client.  
    @param appointment The new appointment to book.  
    @return The newly created appointment.  
    - `DELETE /client/{id}/appointment/{appointmentId}`: Cancel an appointment.  
    @param id              The ID of the client.  
    @param appointmentId   The ID of the appointment to delete.  

4. **Review API**
    - `GET /client/{id}/review`: Get all reviews existing for the given client ID
    @param id                The ID of the client.  
    @return The list of all reviews of given client ID
    - `POST /client/{id}/addReview`: Post review for the given clint ID
    @param id                The ID of the client.  
    @param review The new Review object to add.  
    @return The newly created review
    - `DELETE /client/{id}/review/{reviewId}`:  Delete review with given ID,for  the given client ID  
    @param id                The ID of the client.  
    @param reviewId The Review id to delete.  


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

## Testing Resource

The link to the Postman Collection with some start API's can be
found [here](https://app.getpostman.com/join-team?invite_code=409679a1dec844deadebd4a22f3c5acd&target_code=a92949bc4a1ae5d66291956ecbb26cf5).
A video of API testing via Postman can be seen [here](https://www.youtube.com/watch?v=fzW7lfTNeZs).
