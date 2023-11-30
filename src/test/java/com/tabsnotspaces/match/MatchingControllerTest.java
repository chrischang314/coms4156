package com.tabsnotspaces.match;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.http.HttpStatus;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class MatchingControllerTest {

    @InjectMocks
    private MatchingController matchingController;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ConsumerRepository consumerRepository;

    @Mock
    private ServiceProviderRepository serviceProviderRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ConsumerRequestRepository consumerRequestRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(clientRepository, consumerRepository, serviceProviderRepository, serviceRepository, reviewRepository, appointmentRepository, consumerRequestRepository);
    }

    @Test
    void client() {
    }

    @Test
    void clientsListTest() {
        Client client1 = new Client();
        client1.setClientId(1);
        client1.setClientName("Client1");
        clientRepository.save(client1);

        Client client2 = new Client();
        client2.setClientId(2);
        client2.setClientName("Client2");
        clientRepository.save(client2);

        List<Client> clients = Arrays.asList(client1, client2);
        when(clientRepository.findAll()).thenReturn(clients);

        Iterable<Client> result = matchingController.clientsList();
        assertEquals(2, ((List<Client>) result).size());
    }

    @Test
    void clientsAddTest() {
        Client client = new Client();
        client.setClientId(1);
        client.setClientName("Client1");
        String name = "Client1";
        when(clientRepository.save(client)).thenReturn(client);
        ResponseEntity<Object> responseEntity = matchingController.clientsAdd(client);
        Client result = (Client) responseEntity.getBody();
        assertEquals(result.getClientName(), name);
    }

    @Test
    void deleteClientTest() {
    }

    @Test
    void consumerAddTest() {
        Client client = new Client();
        client.setClientName("ClientB");
        client.setServiceProviders(new ArrayList<>());
        client.setConsumers(new ArrayList<>());
        client.setClientId(1L);
        ResponseEntity<Object> addClientResponse = matchingController.clientsAdd(client);
        System.out.println("Client add entity: " + addClientResponse.getBody());

        Consumer consumer = new Consumer();
        consumer.setConsumerName("ConsumerA");
        consumer.setAddress("New York");
        consumer.setParentClientId(client.getClientId());
        consumer.setAppointments(new ArrayList<Appointment>());
        ArrayList<Double> consumerLocation = new ArrayList<>();
        consumerLocation.add(4.0);
        consumerLocation.add(4.0);
        consumer.setLocation(consumerLocation);

        ResponseEntity<Object> responseEntity = matchingController.consumerAdd(1L, consumer);
        Object responseBody = responseEntity.getBody();
        System.out.println("Response entity: " + responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseBody instanceof Consumer);
        Consumer result = (Consumer) responseBody;
        assertEquals(result, consumer);
    }

    @Test
    void serviceProviderAddTest() {
        Client client = new Client();
        client.setClientId(1);
        client.setClientName("Client1");
        String name = "Client1";
        when(clientRepository.save(client)).thenReturn(client);

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setAddress("New York");
        serviceProvider.setId(1);
        serviceProvider.setParentClientId(1);
        when(serviceProviderRepository.save(serviceProvider)).thenReturn(serviceProvider);
        ResponseEntity<Object> responseEntity = matchingController.serviceProviderAdd(1L, serviceProvider);
        ServiceProvider result = (ServiceProvider) responseEntity.getBody();
        assertEquals(result, serviceProvider);
    }

    @Test
    void sortedProvidersResponseTest() {
        Client client = new Client();
        client.setClientId(2);
        client.setClientName("Client2");
        String name = "Client2";
        matchingController.clientsAdd(client);

        Consumer consumer = new Consumer();
        consumer.setConsumerId(1);
        consumer.setAddress("New York");
        ArrayList<Double> consumerLocation = new ArrayList<Double>();
        consumerLocation.add(40.7128);
        consumerLocation.add(-74.0060);
        consumer.setLocation(consumerLocation);
        consumer.setParentClientId(2);
        consumer.setAppointments(new ArrayList<Appointment>());
        matchingController.consumerAdd(1L, consumer);

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setAddress("New York");

        ArrayList<Double> providerLocation = new ArrayList<Double>();
        providerLocation.add(40.7128);
        providerLocation.add(-74.0060);

        ArrayList<Service> providerServices = new ArrayList<Service>();
//        providerServices.add("Healthcare");
        Service service = new Service();
        service.setId(1L);
        service.setServiceName("Healthcare");
        service.setProviderId(serviceProvider.getId());
        providerServices.add(service);
        serviceProvider.setServices(providerServices);
        serviceProvider.setLocation(providerLocation);

        ArrayList<TupleDateTime> providerAvailabilities = new ArrayList<TupleDateTime>();
        TupleDateTime time = new TupleDateTime(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        providerAvailabilities.add(time);
        serviceProvider.setId(1);
        serviceProvider.setParentClientId(1);
        matchingController.serviceProviderAdd(1L, serviceProvider);

        ConsumerRequest consumerRequest = new ConsumerRequest();
        consumerRequest.setRequestDate(providerAvailabilities.get(0));
        consumerRequest.setConsumerId(1);
        consumerRequest.setPreferredProviderID(1L);
        consumerRequest.setRequestId(3L);
//        consumerRequest.setServiceType("Healthcare");
        consumerRequest.setServiceType(service);
        when(consumerRequestRepository.save(consumerRequest)).thenReturn(consumerRequest);

        List<ServiceProvider> expectedList = new ArrayList<>();
        expectedList.add(serviceProvider);
        List<ServiceProvider> result = matchingController.sortedProvidersResponse(consumerRequest).getBody();
        assertEquals(result, expectedList);
    }

    @Test
    void appointmentAddTest() {
        Client client = new Client();
        client.setClientId(1);
        client.setClientName("Client1");
        String name = "Client1";
        when(clientRepository.save(client)).thenReturn(client);

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1);
        appointment.setConsumerId(1);
        appointment.setProviderID(1);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        Appointment result = matchingController.appointmentAdd(1L, appointment).getBody();
        assertEquals(result, appointment);
    }

    @Test
    void deleteCustomerTest() {
        Client client = new Client();
        client.setClientId(1);
        client.setClientName("Client1");
        String name = "Client1";
        when(clientRepository.save(client)).thenReturn(client);

        Consumer consumer = new Consumer();
        consumer.setConsumerId(1);
        consumer.setAddress("New York");
        consumer.setParentClientId(1);
        consumer.setAppointments(new ArrayList<Appointment>());
        when(consumerRepository.save(consumer)).thenReturn(consumer);
        matchingController.deleteCustomer(1L, 1L);
        verify(consumerRepository, times(1)).delete(consumer);
    }

    @Test
    void deleteServiceProvidersTest() {
        Client client = new Client();
        client.setClientId(1);
        client.setClientName("Client1");
        String name = "Client1";
        when(clientRepository.save(client)).thenReturn(client);

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setAddress("New York");
        serviceProvider.setId(1);
        serviceProvider.setParentClientId(1);
        when(serviceProviderRepository.save(serviceProvider)).thenReturn(serviceProvider);
        ResponseEntity<Object> responseEntity = matchingController.serviceProviderAdd(1L, serviceProvider);
        ServiceProvider result = (ServiceProvider) responseEntity.getBody();
        matchingController.deleteServiceProviders(1L, 1L);
        verify(serviceProviderRepository, times(1)).delete(serviceProvider);
    }

    @Test
    void deleteAppointmentTest() {
        Client client = new Client();
        client.setClientId(1);
        client.setClientName("Client1");
        String name = "Client1";
        when(clientRepository.save(client)).thenReturn(client);

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1);
        appointment.setConsumerId(1);
        appointment.setProviderID(1);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        Appointment result = matchingController.appointmentAdd(1L, appointment).getBody();
        matchingController.deleteAppointment(1L, 1L);
        verify(appointmentRepository, times(1)).delete(appointment);
    }

    @Test
    void deleteConsumerRequestTest() {
        Client client = new Client();
        client.setClientId(2);
        client.setClientName("Client2");
        String name = "Client2";
        matchingController.clientsAdd(client);

        Consumer consumer = new Consumer();
        consumer.setConsumerId(1);
        consumer.setAddress("New York");
        ArrayList<Double> consumerLocation = new ArrayList<Double>();
        consumerLocation.add(40.7128);
        consumerLocation.add(-74.0060);
        consumer.setLocation(consumerLocation);
        consumer.setParentClientId(2);
        consumer.setAppointments(new ArrayList<Appointment>());
        matchingController.consumerAdd(1L, consumer);

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setAddress("New York");

        ArrayList<Double> providerLocation = new ArrayList<Double>();
        providerLocation.add(40.7128);
        providerLocation.add(-74.0060);

        ArrayList<Service> providerServices = new ArrayList<Service>();
//        providerServices.add("Healthcare");
        Service service = new Service();
        service.setId(1L);
        service.setServiceName("Healthcare");
        service.setProviderId(serviceProvider.getId());
        providerServices.add(service);
        serviceProvider.setServices(providerServices);
        serviceProvider.setLocation(providerLocation);

        ArrayList<TupleDateTime> providerAvailabilities = new ArrayList<TupleDateTime>();
        TupleDateTime time = new TupleDateTime(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        providerAvailabilities.add(time);
        serviceProvider.setId(1);
        serviceProvider.setParentClientId(1);
        matchingController.serviceProviderAdd(1L, serviceProvider);

        ConsumerRequest consumerRequest = new ConsumerRequest();
        consumerRequest.setRequestDate(providerAvailabilities.get(0));
        consumerRequest.setConsumerId(1);
        consumerRequest.setPreferredProviderID(1L);
        consumerRequest.setRequestId(3L);
//        consumerRequest.setServiceType("Healthcare");
        consumerRequest.setServiceType(service);
        matchingController.deleteConsumerRequest(1L, 1L);
        verify(consumerRequestRepository, times(1)).delete(consumerRequest);
    }
}